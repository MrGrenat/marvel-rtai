package marvel.app;

import android.content.Context;

/**
 * Created by adefings on 04/04/2018.
 */

public class MesHerosForm {

    private long partieId;
    private String nomhero;
    private String imagehero;
    private String datepartie;
    private Context ctxt;

    public MesHerosForm(String nomhero, String imagehero, String datepartie, Context ctxt, long pId ){
        this.nomhero = nomhero;
        this.imagehero = imagehero;
        this.datepartie = datepartie;
        this.ctxt = ctxt;
        this.setPartieId(pId);
    }

    public String getNomhero() {
        return nomhero;
    }

    public void setNomhero(String nomhero) {
        this.nomhero = nomhero;
    }

    public String getImagehero() {
        return imagehero;
    }

    public void setImagehero(String imagehero) {
        this.imagehero = imagehero;
    }

    public String getDatepartie() {
        return datepartie;
    }

    public void setDatepartie(String datepartie) {
        this.datepartie = datepartie;
    }

    public Context getCtxt() {
        return ctxt;
    }

    public void setCtxt(Context ctxt) {
        this.ctxt = ctxt;
    }

    public long getPartieId() {
        return partieId;
    }

    public void setPartieId(long partieId) {
        this.partieId = partieId;
    }
}
