package marvel.DataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import marvel.DbHelper;
import marvel.Tables.Heros;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by potmane on 08/03/2018.
 */

public class HerosDataSource {

    //CHAMPS BASE DE DONNEES
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = {
            DbHelper.HEROS_ID,
            DbHelper.HEROS_NOM,
            DbHelper.HEROS_DESC,
            DbHelper.HEROS_IMAGE
    };

    public HerosDataSource(Context context){
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Heros createHeros(String heros, String desc, String imgUrl){
        ContentValues values = new ContentValues();
        values.put(DbHelper.HEROS_NOM, heros);
        values.put(DbHelper.HEROS_DESC, desc);
        values.put(DbHelper.HEROS_IMAGE, imgUrl);

        long insertId = database.insert(DbHelper.TABLE_HEROS, null,values);
        Cursor cursor = database.query(DbHelper.TABLE_HEROS, allColumns,
                DbHelper.HEROS_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        Heros newHeros = cursorToHeros(cursor);
        cursor.close();
        return newHeros;

    }

    public void insertHeroes(String heros, String desc, String imgUrl){
        ContentValues values = new ContentValues();
        values.put(DbHelper.HEROS_NOM, heros);
        values.put(DbHelper.HEROS_DESC, desc);
        values.put(DbHelper.HEROS_IMAGE, imgUrl);

        long insertId = database.insert(DbHelper.TABLE_HEROS, null,values);
        Cursor cursor = database.query(DbHelper.TABLE_HEROS, allColumns,
                DbHelper.HEROS_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        //Heros newHeros = cursorToHeros(cursor);
        cursor.close();
    }

    public void deleteHeros(Heros heros) {
        long id = heros.getId();
        System.out.println("Heros deleted with id: " + id);
        database.delete(DbHelper.TABLE_HEROS, DbHelper.HEROS_ID
                + " = " + id, null);
    }

    public void clean(Heros heros){
        System.out.println("Base de donnees videe");
        dbHelper.onUpgradeHeros(database,database.getVersion(),database.getVersion());
    }

    public List<Heros> getAllHeros() {
        List<Heros> heros = new ArrayList<Heros>();

        Cursor cursor = database.query(DbHelper.TABLE_HEROS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Heros hero = cursorToHeros(cursor);
            heros.add(hero);
            cursor.moveToNext();
        }
        cursor.close();
        return heros;
    }

    private Heros cursorToHeros(Cursor cursor) {
        Heros heros = new Heros();
        heros.setId(cursor.getLong(0));
        heros.setNom(cursor.getString(1));
        heros.setDesc(cursor.getString(2));
        heros.setUrlImage(cursor.getString(3));
        return heros;
    }
}
