package z_machine.vkhackathon.com.z_machine.utils;

import android.text.TextUtils;

public final class Validator {

    public static boolean query(String query) {
        return !TextUtils.isEmpty(query) && query.length() > 2;
    }

}
