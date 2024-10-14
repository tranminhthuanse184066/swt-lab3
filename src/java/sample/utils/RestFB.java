package sample.utils;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;

/**
 *
 * @author Hieu Phi Trinh
 */
public class RestFB {

    private static final String FACEBOOK_APP_ID = "1511614989774551";
    private static final String FACEBOOK_APP_SECRET = "61c56b21e110caffa647c9c42cf75949";
    private static final String FACEBOOK_REDIRECT_URL = "http://localhost:8084/Clothes_Store_JSTL/FacebookController";
    private static final String FACEBOOK_LINK_GET_TOKEN = "https://graph.facebook.com/oauth/access_token?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s";

    public static String getToken(final String code) throws ClientProtocolException, IOException {
        String link = String.format(FACEBOOK_LINK_GET_TOKEN, FACEBOOK_APP_ID, FACEBOOK_APP_SECRET, FACEBOOK_REDIRECT_URL, code);
        String response = Request.Get(link).execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static User getUserInfo(String accessToken) {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, FACEBOOK_APP_SECRET, Version.LATEST);
        return facebookClient.fetchObject("me", User.class);
    }
}
