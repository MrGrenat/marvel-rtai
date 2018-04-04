package marvel;

import marvel.JSON.enums.ECaracteristiques;

/**
 * Created by lmarfaing on 03/04/2018.
 */

public class Reponses {

    private static ECaracteristiques repA;
    private static ECaracteristiques repB;
    private static ECaracteristiques repC;
    private static ECaracteristiques repD;
    private static ECaracteristiques repE;

    public static ECaracteristiques getRepA() {
        return repA;
    }

    public static void setRepA(ECaracteristiques pRepA) {
        repA = pRepA;
    }

    public static ECaracteristiques getRepB() {
        return repB;
    }

    public static void setRepB(ECaracteristiques pRepB) {
        repB = pRepB;
    }

    public static ECaracteristiques getRepC() {
        return repC;
    }

    public static void setRepC(ECaracteristiques pRepC) {
        repC = pRepC;
    }

    public static ECaracteristiques getRepD() {
        return repD;
    }

    public static void setRepD(ECaracteristiques pRepD) {
        repD = pRepD;
    }

    public static ECaracteristiques getRepE() {
        return repE;
    }

    public static void setRepE(ECaracteristiques pRepE) {
        repE = pRepE;
    }

    public static void reset()
    {
        setRepA(null);
        setRepB(null);
        setRepC(null);
        setRepD(null);
        setRepE(null);
    }

    public static boolean isOk()
    {
        if(repA != null && repB != null && repC != null && repD != null && repE != null)
            return true;
        else
            return false;
    }
}
