package marvel.DataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import marvel.DbHelper;
import marvel.Tables.Association;

/**
 * Created by adefings on 03/04/2018.
 */

public class AssociationsDataSource {


    //CHAMPS BASE DE DONNEES
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = {
            DbHelper.EAC_PARTIE_ID,
            DbHelper.EAC_HEROS_ID,
    };

    public AssociationsDataSource(Context context){
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void insertAssociation(Association assoc){
        ContentValues values = new ContentValues();
        values.put(DbHelper.EAC_PARTIE_ID, assoc.getIdEtranger());
        values.put(DbHelper.EAC_HEROS_ID, assoc.getIdHeros());

        database.insert(DbHelper.TABLE_EST_ASSOCIE_PARTIE, null,values );
    }


    public void deleteAssociation(Association assoc) {
        long id = assoc.getIdHeros();
        System.out.println("Heros deleted with id: " + id);
        database.delete(DbHelper.TABLE_EST_ASSOCIE_PARTIE, DbHelper.EAC_HEROS_ID
                + " = " + id, null);
    }

    public void clean(){
        System.out.println("Base de donnees vidée");
        dbHelper.onUpgradeHeros(database,database.getVersion(),database.getVersion());
    }

    public List<Association> getAllAssocs() {
        List<Association> assocs = new ArrayList<Association>();

        Cursor cursor = database.query(DbHelper.TABLE_EST_ASSOCIE_PARTIE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Association assocObj = cursorToAssociation(cursor);
            assocs.add(assocObj);
            cursor.moveToNext();
        }
        cursor.close();
        return assocs;
    }

    public Association getAssocId(long id) {
        Association assoc = new Association();

        Cursor cursor = database.query(DbHelper.TABLE_EST_ASSOCIE_PARTIE,
                allColumns, DbHelper.EAC_PARTIE_ID+" = "+id, null, null, null, null);

        cursor.moveToFirst();
        assoc = cursorToAssociation(cursor);
        cursor.close();
        return assoc;
    }

    private Association cursorToAssociation(Cursor cursor) {
        Association assoc = new Association();
        assoc.setIdEtranger(cursor.getInt(0));
        assoc.setIdHeros(cursor.getInt(1));
        return assoc;
    }
}
