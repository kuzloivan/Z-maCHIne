package z_machine.vkhackathon.com.z_machine.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.vk.sdk.VKAccessToken;

/**
 * Created by Kuzlo on 31.10.2015.
 */
public class SharedHelper {

    private static final String VK_TOKEN = "vk_token";
    private static final String USER_ID = "user_id";
    private static final String ALBUM_ID = "album_id";
    private SharedPreferences sharedPreferences;

    private Context context;

    public SharedHelper(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("zmachine",Context.MODE_PRIVATE);
    }

    public void saveVkToken(VKAccessToken vkAccessToken) {
        vkAccessToken.saveTokenToSharedPreferences(context, VK_TOKEN);
    }

    public VKAccessToken getVkAccessToken(){
        return VKAccessToken.tokenFromSharedPreferences(context,VK_TOKEN);
    }

    public boolean isValidVkToken(){
        return getVkAccessToken()!=null&&!getVkAccessToken().isExpired();
    }

    public int getUserId(){
        return sharedPreferences.getInt(USER_ID, 0);
    }

    public void setUserId(int Id){
        sharedPreferences.edit().putInt(USER_ID, Id).apply();
    }
    public int getAlbumId(){
        return sharedPreferences.getInt(ALBUM_ID, 0);
    }

    public void setAlbumId(int Id){
        sharedPreferences.edit().putInt(ALBUM_ID, Id).apply();
    }

}
