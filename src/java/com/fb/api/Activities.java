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
import java.util.List;

/**
 *
 * @author HP
 */
public class Activities {
    static String recipient_id = "1093481367411780";
   static  FacebookClient fbClient = new DefaultFacebookClient(Constants.PAGE_ACCESS_TOKEN);
    public static void makePost(String fbmessage){
        
        FacebookType postResponse = fbClient.publish(Constants.PAGE_ID+"/feed", FacebookType.class, Parameter.with("message", fbmessage));
        Constants.post_id = postResponse.getId();
    }
    public static void getComments(){
       Connection<Post> pageFeed = fbClient.fetchConnection(Constants.PAGE_ID +"/feed",Post.class);
       for(List<Post> feed :pageFeed){
           for(Post post : feed){
               getAllPostComments(post.getId());
               
           }
       }
        
    }
    public static void getAllPostComments(String postId){
        Connection<Comment> cmntDetails = fbClient.fetchConnection(postId+"/comments", Comment.class, Parameter.with("fields", "message,from{id,name}"));
        if(cmntDetails != null){
            List<Comment> cmntList = cmntDetails.getData();
            for(Comment comment : cmntList){
                String temp =comment.getId()+ ": "+comment.getMessage();
                
            }
        }
        
    }
    public static String getConversations(){
        String id = new String();
        String res = new String();
        int s=0;
        JsonMapper jsonMapper = new DefaultJsonMapper();
        JsonObject obj = null;
        Connection<Conversation> conversations = fbClient.fetchConnection("me/conversations", Conversation.class);
        for(List<Conversation>  conversatioPage : conversations){
            for(Conversation convo : conversatioPage){
               id = id+convo.getId();
               Connection<Message> messages = fbClient.fetchConnection(id+"/messages",Message.class, Parameter.with("fields", "message, created_time, from, id"));
              //  List<Message> data = jsonMapper.toJavaList(obj.get("comments").toString(), Message.class);
             // for(Message m : data){
                //res = res+m.getMessage();
                    
                //}
              res = res+messages;
                List<Message> data = messages.getData();
               for(int i=0;i<data.size();i++){
                   Message m = data.get(i);
                   String text = m.getMessage();
                   //res= m.getFrom().getName()+": "+text;
                   recipient_id = m.getFrom().getId();
               }
                
            }
         //   msg = msg+"  list size"+String.valueOf(s);
        }
        return res;
    }
    
    
}
