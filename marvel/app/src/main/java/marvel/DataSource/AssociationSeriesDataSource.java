package marvel.DataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import marvel.DbHelper;
import marvel.Tables.ApparaitDansSerie;

/**
 * Created by wcourtade on 05/04/2018.
 */

public class AssociationSeriesDataSource {
    //CHAMPS BASE DE DONNEES
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = {
            DbHelper.EPS_SERIES_ID,
            DbHelper.EPS_HEROS_ID,
    };

    public AssociationSeriesDataSource(Context context){
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void insertAssociation(ApparaitDansSerie assoc){
        ContentValues values = new ContentValues();
        values.put(DbHelper.EPC_COMICS_ID, assoc.getIdSerie());
        values.put(DbHelper.EPC_HEROS_ID, assoc.getIdHeros());

        database.insert(DbHelper.TABLE_EST_PRESENT_COMICS, null,values );
    }


    public void deleteAssociation(ApparaitDansSerie assoc) {
        long id = assoc.getIdHeros();
        System.out.println("Heros deleted with id: " + id);
        database.delete(DbHelper.TABLE_EST_PRESENT_COMICS, DbHelper.EPC_HEROS_ID
                + " = " + id, null);
    }

    public void clean(){
        System.out.println("Base de donnees videe");
        dbHelper.onUpgradeHeros(database,database.getVersion(),database.getVersion());
    }

    public List<ApparaitDansSerie> getAllAssocs() {
        List<ApparaitDansSerie> assocs = new ArrayList<ApparaitDansSerie>();

        Cursor cursor = database.query(DbHelper.TABLE_EST_PRESENT_COMICS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ApparaitDansSerie assocObj = cursorToPartie(cursor);
            assocs.add(assocObj);
            cursor.moveToNext();
        }
        cursor.close();
        return assocs;
    }

    public static ApparaitDansSerie cursorToPartie(Cursor cursor) {
        ApparaitDansSerie assoc = new ApparaitDansSerie();
        assoc.setIdSerie(cursor.getInt(0));
        assoc.setIdHeros(cursor.getInt(1));
        return assoc;
    }
}
