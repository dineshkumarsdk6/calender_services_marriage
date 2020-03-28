package nithra.calender.marriage.services.Pojo;

/**
 * Created by nithra on 17/8/17.
 */

public class State {

    private String title;
    private boolean selected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    private int subid;

    public String getEng_tit() {
        return eng_tit;
    }

    public void setEng_tit(String eng_tit) {
        this.eng_tit = eng_tit;
    }

    private String eng_tit;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
