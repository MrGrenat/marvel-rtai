package marvel.Tables;

/**
 * Created by PierreO on 17/03/2018.
 */

public class Caracteristiques {

    private long id;
    private String nom;


    //GETTER SETTER
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

    //AFFICHAGE CARACTERISTIQUES
    @Override
    public String toString() {
        return id + " "+nom;
    }
}
