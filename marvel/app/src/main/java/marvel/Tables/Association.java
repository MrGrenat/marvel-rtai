package marvel.Tables;

/**
 * Created by adefings on 04/04/2018.
 */

public class Association {
    private int idHeros;
    private int idEtranger;


    public int getIdHeros() {
        return idHeros;
    }

    public void setIdHeros(long idHeros) {
        this.idHeros = (int) idHeros;
    }

    public int getIdEtranger() {
        return idEtranger;
    }

    public void setIdEtranger(long idEtranger) {
        this.idEtranger = (int) idEtranger;
    }
}
