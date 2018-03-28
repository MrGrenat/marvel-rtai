package marvel.Tables;

/**
 * Created by potmane on 08/03/2018.
 */

public class Heros {

    private long id;
    private String nom;
    private String desc;
    private String urlImage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return
                id + " " +
                nom + " " +
                desc + " " +
                urlImage;
    }
}
