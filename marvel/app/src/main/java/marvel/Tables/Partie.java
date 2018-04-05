package marvel.Tables;

/**
 * Created by adefings on 03/04/2018.
 */

public class Partie{
    private long id;
    private String lienPhoto;
    private int isTermine;
    private String date;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getLienPhoto() {
        return lienPhoto;
    }

    public void setLienPhoto(String lienPhoto) {
        this.lienPhoto = lienPhoto;
    }

    public Integer getTermine() {
        return isTermine;
    }

    public void setTermine(Integer i) {
        isTermine = i;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
