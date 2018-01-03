/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.api;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.Comment;
import com.restfb.types.Conversation;
import com.restfb.types.FacebookType;
import com.restfb.types.GraphResponse;
import com.restfb.types.Message;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.SendResponse;
import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author HP
 */
public class Activities {

    static FacebookClient fbclient = new DefaultFacebookClient(Constants.MY_ACCESS_TOKEN);

    //client with page access token 
    static FacebookClient fbPageClient = new DefaultFacebookClient(Constants.PAGE_ACCESS_TOKEN);

    public static String makePost(String fbmessage) {
        String postStatus = new String();
        //publish post with page ID and get post ID from response
        FacebookType postResponse = fbPageClient.publish(Constants.PAGE_ID + "/feed", FacebookType.class, Parameter.with("message", fbmessage));

        if (postResponse.getId() != null || postResponse.getId().equals("")) {

           // Constants.post_id = postResponse.getId();
            postStatus = "Status posted successfully in your page";
        }
        return postStatus;
    }

    public static JSONArray getAllPostComments() {
        //to hold the comments of all posts
        JSONArray receivedCmnts = new JSONArray();
        //fetch all the posts
        Connection<Post> pageFeed = fbPageClient.fetchConnection(Constants.PAGE_ID + "/feed", Post.class);
        for (List<Post> feed : pageFeed) {
            for (Post post : feed) {
                //for ech post
                JSONObject cmnts = new JSONObject();
                //fetch comments of ech post

                cmnts = getComments(post.getId(), post.getMessage());

                receivedCmnts.put(cmnts);
            }
        }
        return receivedCmnts;
    }

    public static JSONObject getComments(String postId, String postContent) {
        JSONObject cmnts = new JSONObject();
        Connection<Comment> cmntDetails = fbPageClient.fetchConnection(postId + "/comments", Comment.class, Parameter.with("fields", "message,from{id,name}"));

        if (cmntDetails != null) {
            List<Comment> cmntList = cmntDetails.getData();

            for (Comment comment : cmntList) {
                //fetch sender name and message
                try {
                    cmnts.put("postContent", postContent);
                    cmnts.put("sender", comment.getFrom().getName());
                    cmnts.put("content", comment.getMessage());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return cmnts;

    }

    public static JSONArray getConversations() {
        String id = new String();
        JSONArray receivedMsgs = new JSONArray();
        //fetch users sent message to your page
        Connection<Conversation> conversations = fbPageClient.fetchConnection("me/conversations", Conversation.class);
        for (List<Conversation> conversatioPage : conversations) {
            for (Conversation convo : conversatioPage) {
                id = id + convo.getId();
                Connection<Message> messages = fbPageClient.fetchConnection(id + "/messages", Message.class, Parameter.with("fields", "message, created_time, from, id"));
                try {
                    Message m = null;
                    List<Message> data = messages.getData();
                    for (int i = 0; i < data.size(); i++) {
                        m = data.get(i);
                        JSONObject msg = new JSONObject();
                        msg.put("convId", id);
                        msg.put("sender", m.getFrom().getName());
                        msg.put("content", m.getMessage());
                        receivedMsgs.put(msg);
                    }
                    String recipientId = m.getFrom().getId();
                    Constants.recipient_id = m.getFrom().getId();
                    System.out.println(recipientId);
//                    Message msg = new Message();
//                    msg.setMessage("Hello");
                    String msg = "Just for test";
                    //System.out.println(recipientId);
                    IdMessageRecipient recipient = new IdMessageRecipient(id);

                    SendResponse resp = fbPageClient.publish(id+"/messages", SendResponse.class, Parameter.with("recipient", recipient), Parameter.with("message", msg));
                    
                    if(!resp.isSuccessful()){
                        throw new RuntimeException("Error : Message not sent");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
        return receivedMsgs;
    }

    public static String postToPage(String message) {
        String status = new String();

        FacebookType response = fbclient.publish(Constants.PAGE_ID + "/feed", FacebookType.class, Parameter.with("message", message));
        if (response.getId() != null) {
            status = "Successfully posted";
        }

        return status;
    }

}
