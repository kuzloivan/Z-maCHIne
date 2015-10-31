package z_machine.vkhackathon.com.z_machine.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.vk.sdk.VKAccessToken;

/**
 * Created by Kuzlo on 31.10.2015.
 */
public class SharedHelper {

    private static final String VK_TOKEN = "vk_token";
    private SharedPreferences preferences;
    private Context context;

    public SharedHelper(Context context){
        preferences = context.getSharedPreferences("zmachine",Context.MODE_PRIVATE);
        this.context = context;
    }

    public void saveVkToken(VKAccessToken token){
        token.saveTokenToSharedPreferences(context,VK_TOKEN);
    }

    public VKAccessToken getVkAccessToken(){
        return VKAccessToken.tokenFromSharedPreferences(context,VK_TOKEN);
    }

    public boolean isValidVkToken(){
        return getVkAccessToken()!=null&&!getVkAccessToken().isExpired();
    }
}
