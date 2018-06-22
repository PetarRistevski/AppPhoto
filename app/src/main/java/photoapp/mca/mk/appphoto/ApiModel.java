package photoapp.mca.mk.appphoto;

public class ApiModel {
    public String mTitle;

    public String thumbnailUrl;
    public String Url;

    public  String getmTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public  String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getUrl() {
        return Url;
    }

    public ApiModel() {
    }


    @Override
    public String toString() {
        return String.format("%s", mTitle);
    }

    }


