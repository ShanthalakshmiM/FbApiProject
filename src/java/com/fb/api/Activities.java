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

    static String recipient_id;
    static FacebookClient fbClient = new DefaultFacebookClient(Constants.PAGE_ACCESS_TOKEN);

    public static String makePost(String fbmessage) {
        String postStatus = "not yet posted";
        FacebookType postResponse = fbClient.publish(Constants.PAGE_ID + "/feed", FacebookType.class, Parameter.with("message", fbmessage));

        if (postResponse.getId() != null || postResponse.getId().equals("")) {
            Constants.post_id = postResponse.getId();
            postStatus = "Status posted successfully in your page";
        }
        return postStatus;
    }

    public static JSONArray getAllPostComments() {
        JSONArray receivedCmnts = new JSONArray();
        Connection<Post> pageFeed = fbClient.fetchConnection(Constants.PAGE_ID + "/feed", Post.class);
        for (List<Post> feed : pageFeed) {
            for (Post post : feed) {
                JSONObject cmnts = new JSONObject();
                
                cmnts = getComments(post.getId());
               // if(cmnts.isNull("cmnts.sender"))
                receivedCmnts.put(cmnts);
            }
        }
        return receivedCmnts;
    }
   
    public static JSONObject getComments(String postId) {
        JSONObject cmnts = new JSONObject();
        Connection<Comment> cmntDetails = fbClient.fetchConnection(postId + "/comments", Comment.class, Parameter.with("fields", "message,from{id,name}"));
        if (cmntDetails != null) {
            List<Comment> cmntList = cmntDetails.getData();
            
            for (Comment comment : cmntList) {
                
                try {
                    
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
        Connection<Conversation> conversations = fbClient.fetchConnection("me/conversations", Conversation.class);
        for (List<Conversation> conversatioPage : conversations) {
            for (Conversation convo : conversatioPage) {
                id = id + convo.getId();
                Connection<Message> messages = fbClient.fetchConnection(id + "/messages", Message.class, Parameter.with("fields", "message, created_time, from, id"));
                try {
                    List<Message> data = messages.getData();
                    for (int i = 0; i < data.size(); i++) {
                        Message m = data.get(i);
                        JSONObject msg = new JSONObject();
                        msg.put("sender", m.getFrom().getName());
                        msg.put("content", m.getMessage());
                        receivedMsgs.put(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
        return receivedMsgs;
    }

}
