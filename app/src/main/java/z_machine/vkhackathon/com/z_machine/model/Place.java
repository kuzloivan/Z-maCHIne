package z_machine.vkhackathon.com.z_machine.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.util.ArrayList;
import java.util.List;

public final class Place implements ClusterItem {

    private int id;
    private String title;
    @SerializedName("short_title")
    private String shortTitle;
    private String slug;
    private String address;
    private String phone;
    private Coords coords;
    private List<Image> images;
    private String description;
    @SerializedName("body_text")
    private String bodyText;
    private List<String> categories;
    private int comments_count;
    private int favorites_count;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Coords getCoords() {
        return coords;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getDescription() {
        return description;
    }

    public String getBodyText() {
        return bodyText;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(coords.getLat(), coords.getLon());
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getFavorites_count() {
        return favorites_count;
    }

    public void setFavorites_count(int favorites_count) {
        this.favorites_count = favorites_count;
    }

    public static Place facePlace() {
        final Place place = new Place();
        place.setTitle("императорское коммерческое училище (Университет ИТМО)");
        place.setAddress("ул.Ломоносова, д.9");
        place.setDescription("<p>Одно из старейших училищ России пережило переезд, закрытие и даже царскую династию. В настоящее время оно стало частью Университета ИТМО, встречает все новых студентов, провожает выпускников и предоставляет площадку для увлекательных и познавательных мероприятий Петербурга.</p>\n");
        place.setSlug("imperatorskoe-kommercheskoe-uchilishe");
        place.setShortTitle("императорское коммерческое училище");
        final List<Image> images = new ArrayList<>();
        final Image image = new Image();
        image.setImage("http://kudago.com/media/images/place/54/0e/540e81ab241ba3db4fec05078f365722.jpg");
        images.add(image);
        place.setImages(images);
        place.setId(24109);
        place.setPhone("+7 (812) 315–36–17");
        final Coords coords = new Coords();
        coords.setLat(59.927315000000014);
        coords.setLon(30.338272);
        place.setCoords(coords);
        return place;
    }
}
