package marvel.DataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import marvel.DbHelper;
import marvel.Tables.Heros;
import marvel.Tables.Partie;

/**
 * Created by adefings on 03/04/2018.
 */

public class PartiesDataSource {


    //CHAMPS BASE DE DONNEES
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = {
            DbHelper.PARTIE_ID,
            DbHelper.PARTIE_PHOTO,
            DbHelper.PARTIE_DATE,
            DbHelper.PARTIE_ISTERMINE
    };

    public PartiesDataSource(Context context){
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Partie createPartie(String photoUrl){
        ContentValues values = new ContentValues();
        values.put(DbHelper.PARTIE_PHOTO, photoUrl);
        values.put(DbHelper.PARTIE_ISTERMINE, 0);

        long insertId = database.insert(DbHelper.TABLE_PARTIE, null, values);
        Cursor cursor = database.query(DbHelper.TABLE_PARTIE, allColumns,
                DbHelper.PARTIE_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        Partie newPartie = cursorToPartie(cursor);
        cursor.close();
        return newPartie;

    }

    public long insertPartie(Partie partie){
        ContentValues values = new ContentValues();
        values.put(DbHelper.PARTIE_PHOTO, partie.getLienPhoto());
        values.put(DbHelper.PARTIE_ISTERMINE, partie.getTermine());
        values.put(DbHelper.PARTIE_DATE, partie.getDate());

        long insertId = database.insert(DbHelper.TABLE_PARTIE, null,values);
        Cursor cursor = database.query(DbHelper.TABLE_PARTIE, allColumns,
                DbHelper.PARTIE_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        //Heros newHeros = cursorToHeros(cursor);
        cursor.close();

        return insertId;
    }



    public void updatePhoto(long id, String path){
        ContentValues values = new ContentValues();
        values.put(DbHelper.PARTIE_PHOTO, path);

        String where = DbHelper.PARTIE_ID + "= "+ id;

        try{
            database.update(DbHelper.TABLE_PARTIE, values, where, null);
        }
        catch (Exception e){
            //String error =  e.getMessage().toString();
        }

    }

    public void updateDate(long id, String date){
        ContentValues values = new ContentValues();
        values.put(DbHelper.PARTIE_DATE, date);

        String where = DbHelper.PARTIE_ID + "= "+ id;

        try{
            database.update(DbHelper.TABLE_PARTIE, values, where, null);
        }
        catch (Exception e){
            //String error =  e.getMessage().toString();
        }

    }

    public void setTermine(long id){
        ContentValues values = new ContentValues();
        values.put(DbHelper.PARTIE_ISTERMINE, 1);

        String where = DbHelper.PARTIE_ID + "= "+ id;

        try{
            database.update(DbHelper.TABLE_PARTIE, values, where, null);
        }
        catch (Exception e){
            //String error =  e.getMessage().toString();
        }

    }

    public void deletePartie(Partie partie) {
        long id = partie.getId();
        System.out.println("Heros deleted with id: " + id);
        database.delete(DbHelper.TABLE_PARTIE, DbHelper.PARTIE_ID
                + " = " + id, null);
    }

    public void clean(){
        System.out.println("Base de donnees videe");
        dbHelper.onUpgradeHeros(database,database.getVersion(),database.getVersion());
    }

    public List<Partie> getAllParties() {
        List<Partie> partie = new ArrayList<Partie>();

        Cursor cursor = database.query(DbHelper.TABLE_PARTIE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Partie partieObj = cursorToPartie(cursor);
            partie.add(partieObj);
            cursor.moveToNext();
        }
        cursor.close();
        return partie;
    }

    public Partie getPartieId(long id) {
        Partie partie = new Partie();

        Cursor cursor = database.query(DbHelper.TABLE_PARTIE,
                allColumns, DbHelper.PARTIE_ID+" = "+id, null, null, null, null);

        cursor.moveToFirst();
        partie = cursorToPartie(cursor);
        cursor.close();
        return partie;
    }

    private Partie cursorToPartie(Cursor cursor) {
        Partie partie = new Partie();
        partie.setId(cursor.getLong(0));
        partie.setLienPhoto(cursor.getString(1));
        partie.setDate(cursor.getString(2));
        partie.setTermine(cursor.getInt(3));
        return partie;
    }
}
