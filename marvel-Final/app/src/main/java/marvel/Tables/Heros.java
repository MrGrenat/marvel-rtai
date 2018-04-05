package marvel.Tables;

/**
 * Created by potmane on 08/03/2018.
 */

public class Heros {

    private long id;
    private String nom;
    private String desc;
    private String urlImage;
    private int heroique;
    private int psychopathe;
    private int intelligent;
    private int charismatique;



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

    public int getHeroique() {
        return heroique;
    }

    public void setHeroique(int heroique) {
        this.heroique = heroique;
    }

    public int getPsychopathe() {
        return psychopathe;
    }

    public void setPsychopathe(int psychopathe) {
        this.psychopathe = psychopathe;
    }

    public int getIntelligent() {
        return intelligent;
    }

    public void setIntelligent(int intelligent) {
        this.intelligent = intelligent;
    }

    public int getCharismatique() {
        return charismatique;
    }

    public void setCharismatique(int charismatique) {
        this.charismatique = charismatique;
    }

    public void setCarateristiques(int heroique, int intelligent, int charismatique, int psychopathe)
    {
        this.intelligent = intelligent;
        this.heroique = heroique;
        this.charismatique = charismatique;
        this.psychopathe = psychopathe;
    }

    @Override
    public String toString() {
        return "Heros{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", desc='" + desc + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", heroique=" + heroique +
                ", psychopathe=" + psychopathe +
                ", intelligent=" + intelligent +
                ", charismatique=" + charismatique +
                '}';
    }
}
