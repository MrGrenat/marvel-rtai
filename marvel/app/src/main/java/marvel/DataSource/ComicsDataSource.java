package marvel.DataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import marvel.DbHelper;
import marvel.Tables.ApparaitDansComic;
import marvel.Tables.Comics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by potmane on 21/03/2018.
 */

public class ComicsDataSource {
    //CHAMPS BASE DE DONNEES
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = {
            DbHelper.COMICS_ID,
            DbHelper.COMICS_NOM
    };
    private String[] columnsComics = {
            DbHelper.EPC_COMICS_ID,
            DbHelper.EPC_HEROS_ID
    };

    public ComicsDataSource(Context context){
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public List<Comics> getLesComicsDunHeros(long id) {
        List<Comics> comics = new ArrayList<Comics>();
        List<Long> idComics = new ArrayList<Long>();

        Cursor cursor1 = database.query(DbHelper.TABLE_EST_PRESENT_COMICS,
                columnsComics, DbHelper.EPC_HEROS_ID+"="+id, null, null, null, null);

        cursor1.moveToFirst();
        while (!cursor1.isAfterLast()) {
            ApparaitDansComic comic = AssociationComicsDataSource.cursorToPartie(cursor1);
            idComics.add(comic.getIdComic());
            cursor1.moveToNext();
        }
        System.out.println("----- Nb comics :" + comics.size()+" -----");
        cursor1.close();

        for(int i=0; i<idComics.size(); i++)
        {
            Cursor cursor = database.query(DbHelper.TABLE_COMICS,
                    allColumns, DbHelper.COMICS_ID+"="+idComics.get(i).intValue(), null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Comics unComic = cursorToComics(cursor);
                comics.add(unComic);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return comics;
    }

    public Comics createComics(String comics){
        ContentValues values = new ContentValues();
        values.put(DbHelper.COMICS_NOM, comics);

        long insertId = database.insert(DbHelper.TABLE_COMICS, null,values);
        Cursor cursor = database.query(DbHelper.TABLE_COMICS, allColumns,
                DbHelper.COMICS_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        Comics newComics = cursorToComics(cursor);
        cursor.close();
        return newComics;

    }

    public long insertComicsGetID(String nom){
        ContentValues values = new ContentValues();
        values.put(DbHelper.COMICS_NOM, nom);

        long insertId = database.insert(DbHelper.TABLE_COMICS, null,values);
        Cursor cursor = database.query(DbHelper.TABLE_COMICS, allColumns,
                DbHelper.COMICS_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        cursor.close();

        return insertId;
    }

    public void insertComicsHeros(long idSeries, long idHeros){
        ContentValues values = new ContentValues();
        values.put(DbHelper.EPC_COMICS_ID, idSeries);
        values.put(DbHelper.EPC_HEROS_ID, idHeros);

        database.insert(DbHelper.TABLE_EST_PRESENT_COMICS, null,values);
    }

    public void deleteComics(Comics comics) {
        long id = comics.getId();
        System.out.println("Comics deleted with id: " + id);
        database.delete(DbHelper.TABLE_COMICS, DbHelper.COMICS_ID
                + " = " + id, null);
    }

    public void clean(Comics comics){
        System.out.println("Base de donnees videe");
        dbHelper.onUpgradeComics(database,database.getVersion(),database.getVersion());
    }

    public List<Comics> getAllComics() {
        List<Comics> comics = new ArrayList<Comics>();

        Cursor cursor = database.query(DbHelper.TABLE_COMICS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comics comic = cursorToComics(cursor);
            comics.add(comic);
            cursor.moveToNext();
        }
        cursor.close();
        return comics;
    }
    private Comics cursorToComics(Cursor cursor) {
        Comics comics = new Comics();
        comics.setId(cursor.getLong(0));
        comics.setNom(cursor.getString(1));
        return comics;
    }
}
