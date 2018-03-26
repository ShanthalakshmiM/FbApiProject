
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HP
 */
public class FBGraph {
    private String accesstoken;
    
    public FBGraph(String accesstoken){
        this.accesstoken = accesstoken;
    }
    
    public String getFBGraph(){
        String graph = null;
        try{
            String g =  "https://graph.facebook.com/me?" + accesstoken;
            URL u = new URL(g);
            URLConnection con = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String InputLine;
            StringBuffer b = new StringBuffer();
            while((InputLine = in.readLine())!= null)
                b.append(InputLine +"\n");
            in.close();
            graph = b.toString();
            System.out.println(graph);
        
        }
        catch(IOException ie){
            ie.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return graph;
    }
    public Map getGraphData(String fbGraph) throws org.json.JSONException {
		Map fbProfile = new HashMap();
                JSONObject json = new JSONObject(fbGraph);
                fbProfile.put("id", json.getString("id"));
                fbProfile.put("first_name", json.getString("first_name"));
                if (json.has("email"))
                    fbProfile.put("email", json.getString("email"));
                if (json.has("gender"))
                    fbProfile.put("gender", json.getString("gender"));
		return fbProfile;
	}
}
