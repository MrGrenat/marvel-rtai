package marvel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by potmane on 08/03/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    //TABLE HEROS
    public static final String TABLE_HEROS = "heros";
    public static final String HEROS_ID = "_id";
    public static final String HEROS_NOM = "nom";
    public static final String HEROS_DESC = "descHeros";
    public static final String HEROS_IMAGE ="herosImage";

    //TABLE UTILISATEUR
    public static final String TABLE_UTILISATEUR = "utilisateur";
    public static final String UTILISATEUR_ID = "_id";
    public static final String UTILISATEUR_PSEUDO = "pseudo";

    //TABLE CARACTERISTIQUES
    public static final String TABLE_CARACTERISTIQUES = "caracteristiques";
    public static final String CARACTERISTIQUES_ID = "_id";
    public static final String CARACTERISTIQUES_NOM = "nomCaracterisitique";


    //TABLE CONTIENT : HEROS <-> CARACTERISTIQUES
    public static final String TABLE_CONTIENT = "contient";
    public static final String CT_HEROS_ID = "_idContientHeros";
    public static final String CT_CARACTERISTIQUES_ID = "_idContientCarac";

    //TABLE CORRESPOND : HEROS <-> UTILISATEUR
    //public static final String TABLE_CORRESPOND = "correspond";
    //public static final String CORR_ID_HEROS = "_idCorrespondHeros";
    //public static final String CORR_ID_UTILISATEUR = "_idCorrespondUtilisateur";

    //TABLE COMICS
    public static final String TABLE_COMICS = "comics";
    public static final String COMICS_ID = "_idComics";
    public static final String COMICS_NOM = "nomComics";

    //TABLE FILMS
    public static final String TABLE_SERIES = "series";
    public static final String SERIES_ID = "_idSeries";
    public static final String SERIES_NOM = "nomSeries";

    //TABLE PARTIE
    public static final String TABLE_PARTIE= "partie";
    public static final String PARTIE_ID = "_idPartie";
    public static final String PARTIE_PHOTO = "photoPartie";
    public static final String PARTIE_DATE = "datePartie";
    public static final String PARTIE_ISTERMINE = "isTerminePartie";

    //TABLE EST_PRESENT_COMICS : HEROS <-> COMICS
    public static final String TABLE_EST_ASSOCIE_PARTIE = "estAssociePartie";
    public static final String EAC_PARTIE_ID = "_idEstAssociePartie";
    public static final String EAC_HEROS_ID = "_idEstAssocieHeros";

    //TABLE EST_PRESENT_COMICS : HEROS <-> COMICS
    public static final String TABLE_EST_PRESENT_COMICS = "estPresentComics";
    public static final String EPC_COMICS_ID = "_idEstPresentComics";
    public static final String EPC_HEROS_ID = "_idEstPresentHeros";

    //TABLE EST_PRESENT_COMICS : HEROS <-> SERIES
    public static final String TABLE_EST_PRESENT_SERIES = "estPresentSeries";
    public static final String EPS_SERIES_ID = "_idEstPresentSeries";
    public static final String EPS_HEROS_ID = "_idEstPresentHeros";


    //NOM BDD + VERSION BDD
    private static final String DATABASE_NAME = "marvel";
    private static final int DATABASE_VERSION = 4;

    //CREATION BDD
    private static final String DATABASE_HEROS = "create table "
            + TABLE_HEROS + "(" + HEROS_ID
            + " integer primary key autoincrement, "
            + HEROS_NOM+" text not null, "
            + HEROS_DESC+" text, "
            + HEROS_IMAGE+" text not null);";
    private static final String DATABASE_UTILISATEUR = "create table "

            + TABLE_UTILISATEUR + "(" + UTILISATEUR_ID
            + " integer primary key autoincrement, " + UTILISATEUR_PSEUDO
            + " text not null);";
    private static final String DATABASE_CARACTERISTIQUES =
            "create table "
            + TABLE_CARACTERISTIQUES + "(" + CARACTERISTIQUES_ID
            + " integer primary key autoincrement, " + CARACTERISTIQUES_NOM
            + " text not null);";
    private static final String DATABASE_CONTIENT =
            "create table "
            + TABLE_CONTIENT + "(" + CT_HEROS_ID
            + " integer,"
            + CT_CARACTERISTIQUES_ID
            + " integer, "
            + " primary key("+CT_HEROS_ID+", "+CT_CARACTERISTIQUES_ID+" ));";

    //private static final String DATABASE_CORRESPOND =
    //        "create table "
    //        + TABLE_CORRESPOND + "(" + CORR_ID_HEROS
    //        + " integer, "
    //        + CORR_ID_UTILISATEUR
    //        + " integer,"
    //        + " primary key("+CORR_ID_HEROS+", "+CORR_ID_UTILISATEUR+" ));";

    private static final String DATABASE_PARTIE =
            "create table "
                    + TABLE_PARTIE + "(" + PARTIE_ID
                    + " integer primary key autoincrement, " + PARTIE_PHOTO
                    + " text not null, " + PARTIE_DATE
                    + " datetime default CURRENT_TIMESTAMP, " + PARTIE_ISTERMINE
                    + " integer default 0);";

    private static final String DATABASE_ASSOCIE =
            "create table "
                    + TABLE_EST_ASSOCIE_PARTIE + "(" + EAC_PARTIE_ID
                    + " integer,"
                    + EAC_HEROS_ID
                    + " integer, "
                    + " primary key("+EAC_PARTIE_ID+", "+EAC_HEROS_ID+" ));";


    private static final String DATABASE_COMICS =
            "create table "
            + TABLE_COMICS + "(" + COMICS_ID
            + " integer primary key autoincrement,"
            + COMICS_NOM
            + " text not null);";
    private static final String DATABASE_SERIES =
            "create table "
            + TABLE_SERIES + "(" + SERIES_ID
            + " integer primary key autoincrement,"
            + SERIES_NOM
            + " text not null);";
    private static final String DATABASE_EST_PRESENT_COMICS =
            "create table "
            + TABLE_EST_PRESENT_COMICS + "(" + EPC_HEROS_ID
            + " integer,"
            + EPC_COMICS_ID
            + " integer, "
            + " primary key("+EPC_HEROS_ID+", "+EPC_COMICS_ID+" ));";
    private static final String DATABASE_EST_PRESENT_SERIES =
            "create table "
            + TABLE_EST_PRESENT_SERIES + "(" + EPS_SERIES_ID
            + " integer,"
            + EPS_HEROS_ID
            + " integer, "
            + " primary key("+EPS_HEROS_ID+", "+EPS_SERIES_ID+" ));";
            

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_HEROS);
        database.execSQL(DATABASE_SERIES);
        database.execSQL(DATABASE_CARACTERISTIQUES);
        database.execSQL(DATABASE_COMICS);
        database.execSQL(DATABASE_CONTIENT);
        database.execSQL(DATABASE_UTILISATEUR);
        database.execSQL(DATABASE_EST_PRESENT_COMICS);
        database.execSQL(DATABASE_EST_PRESENT_SERIES);
        database.execSQL(DATABASE_PARTIE);
        database.execSQL(DATABASE_ASSOCIE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int versionCourante, int nouvelleVersion) {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + versionCourante + " to "
                        + nouvelleVersion + ", which will delete all table");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HEROS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_SERIES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CARACTERISTIQUES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_COMICS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTIENT);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EST_PRESENT_COMICS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EST_PRESENT_SERIES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTIE);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EST_ASSOCIE_PARTIE);
        onCreate(database);
    }

    public void onUpgradeHeros(SQLiteDatabase database, int versionCourante, int nouvelleVersion){
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + versionCourante + " to "
                        + nouvelleVersion + ", which will clean table HEROS");
        database.execSQL("DELETE FROM '"+TABLE_HEROS+"'");
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='"+TABLE_HEROS+"'");

    }

    public void onUpgradeUtilisateur(SQLiteDatabase database, int versionCourante, int nouvelleVersion){
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + versionCourante + " to "
                        + nouvelleVersion + ", which will clean table UTILISATEUR");
        database.execSQL("DELETE FROM '"+TABLE_UTILISATEUR+"'");
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='"+TABLE_UTILISATEUR+"'");

    }
    public void onUpgradeCaracteristiques(SQLiteDatabase database, int versionCourante, int nouvelleVersion) {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + versionCourante + " to "
                        + nouvelleVersion + ", which will clean table CARACTERISITQUES");
        database.execSQL("DELETE FROM '" + TABLE_CARACTERISTIQUES + "'");
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_CARACTERISTIQUES + "'");
    }

    public void onUpgradeSeries(SQLiteDatabase database, int versionCourante, int nouvelleVersion) {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + versionCourante + " to "
                        + nouvelleVersion + ", which will clean table SERIES & EPS_SERIES");
        database.execSQL("DELETE FROM '" + TABLE_SERIES + "'");
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_SERIES + "'");

        database.execSQL("DELETE FROM '" + TABLE_EST_PRESENT_SERIES + "'");
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_EST_PRESENT_SERIES + "'");
    }

    public void onUpgradeComics(SQLiteDatabase database, int versionCourante, int nouvelleVersion) {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + versionCourante + " to "
                        + nouvelleVersion + ", which will clean table COMICS & EPC_COMICS");
        database.execSQL("DELETE FROM '" + TABLE_COMICS + "'");
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_COMICS + "'");

        database.execSQL("DELETE FROM '" + TABLE_EST_PRESENT_COMICS+ "'");
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_EST_PRESENT_COMICS + "'");

    }
    public void onUpgradeParties(SQLiteDatabase database, int versionCourante, int nouvelleVersion) {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + versionCourante + " to "
                        + nouvelleVersion + ", which will clean table COMICS & EPC_COMICS");
        database.execSQL("DELETE FROM '" + TABLE_PARTIE + "'");
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_PARTIE + "'");

        database.execSQL("DELETE FROM '" + TABLE_EST_ASSOCIE_PARTIE+ "'");
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_EST_ASSOCIE_PARTIE + "'");
    }

}
