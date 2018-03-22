package marvel.Tables;

/**
 * Created by potmane on 21/03/2018.
 */

public class Comics {

    private long id;
    private String nom;

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

    @Override
    public String toString() {
        return "Comics{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
