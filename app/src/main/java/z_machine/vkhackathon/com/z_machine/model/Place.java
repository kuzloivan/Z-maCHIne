package z_machine.vkhackathon.com.z_machine.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

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
        return new LatLng(coords.getLat(),coords.getLon());
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
}
