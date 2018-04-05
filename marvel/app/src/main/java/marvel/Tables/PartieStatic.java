package marvel.Tables;

/**
 * Created by adefings on 03/04/2018.
 */

public class PartieStatic{
    private static long id;
    private static String lienPhoto;
    private static int isTermine;
    private static String date;

    //POUR DETAILS DU HEROS
    private static long idheros;

    public static long getId() {
        return id;
    }

    public static void setId(long idSet) {
        id = idSet;
    }


    public static String getLienPhoto() {
        return lienPhoto;
    }

    public static void setLienPhoto(String lienPhotoSet) {
       lienPhoto = lienPhotoSet;
    }

    public static Integer getTermine() {
        return isTermine;
    }

    public static void setTermine(Integer i) {
        isTermine = i;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String dateSet) {
        date = dateSet;
    }

    public static long getIdheros() {
        return idheros;
    }

    public static void setIdheros(long idheros) {
        PartieStatic.idheros = idheros;
    }
}
