package marvel;

import marvel.JSON.enums.ECaracteristiques;

/**
 * Created by lmarfaing on 03/04/2018.
 */

public class Reponses {

    private static String qA;
    private static String qB;
    private static String qC;
    private static String qD;
    private static String qE;

    private static String repAValue;
    private static String repBValue;
    private static String repCValue;
    private static String repDValue;
    private static String repEValue;

    private static ECaracteristiques repA;
    private static ECaracteristiques repB;
    private static ECaracteristiques repC;
    private static ECaracteristiques repD;
    private static ECaracteristiques repE;

    public static String getqA() {
        return qA;
    }

    public static void setqA(String qAV) {
        qA = qAV;
    }

    public static String getqB() {
        return qB;
    }

    public static void setqB(String qBV) {
        qB = qBV;
    }

    public static String getqC() {
        return qC;
    }

    public static void setqC(String qCV) {
        qC = qCV;
    }

    public static String getqD() {
        return qD;
    }

    public static void setqD(String qDV) {
        qD = qDV;
    }

    public static String getqE() {
        return qE;
    }

    public static void setqE(String qEV) {
        qE = qEV;
    }



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



    public static String getRepAValue() {
        return repAValue;
    }

    public static void setRepAValue(String repAV) {
        repAValue = repAV;
    }

    public static String getRepBValue() {
        return repBValue;
    }

    public static void setRepBValue(String repBV) {
        repBValue = repBV;
    }

    public static String getRepCValue() {
        return repCValue;
    }

    public static void setRepCValue(String repCV) {
        repCValue = repCV;
    }

    public static String getRepDValue() {
        return repDValue;
    }

    public static void setRepDValue(String repDV) {
        repDValue = repDV;
    }

    public static String getRepEValue() {
        return repEValue;
    }

    public static void setRepEValue(String repEV) {
        repEValue = repEV;
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
