package z_machine.vkhackathon.com.z_machine.utils;

public final class SystemUtils {

    private static final String HASH_TAG_PLACE_PATTERN = "#KudaGoPlace";
    private static final String HASH_TAG_EVENT_PATTERN = "#kudago_";

    public static String placeHashTagById(int placeId) {
        return HASH_TAG_PLACE_PATTERN + placeId;
    }

    public static String eventHashTagById(int eventId) {
        return HASH_TAG_EVENT_PATTERN + eventId;
    }

    public static String eventHashTagByTitle(String title) {
        return HASH_TAG_EVENT_PATTERN + Transliterator.transliterate(title);
    }


}
