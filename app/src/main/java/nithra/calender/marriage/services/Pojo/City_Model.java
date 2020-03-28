package nithra.calender.marriage.services.Pojo;


import nithra.calender.marriage.services.searchdialog.core.Searchable;

public class City_Model implements Searchable {
    private String mTitle;
    private String id;
    private String did;

    public String getDid() {
        return did;
    }



    public City_Model(String mid, String mname, String mdid) {
        id = mid;
        mTitle = mname;
        did = mdid;

    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public City_Model setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getId() {
        return id;
    }

    public City_Model setId(String mid) {
        id = mid;
        return this;
    }
}
