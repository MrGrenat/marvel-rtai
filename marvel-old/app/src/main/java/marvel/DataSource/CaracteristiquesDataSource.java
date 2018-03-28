package marvel.DataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import marvel.DbHelper;
import marvel.Tables.Caracteristiques;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PierreO on 17/03/2018.
 */

public class CaracteristiquesDataSource {

    //CHAMPS BASE DE DONNEES
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = {
            DbHelper.CARACTERISTIQUES_ID, DbHelper.CARACTERISTIQUES_NOM
    };

    public CaracteristiquesDataSource(Context context){
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Caracteristiques createCaracteristiques(String carac){
        ContentValues values = new ContentValues();
        values.put(DbHelper.CARACTERISTIQUES_NOM, carac);

        long insertId = database.insert(DbHelper.TABLE_CARACTERISTIQUES, null,values);
        Cursor cursor = database.query(DbHelper.TABLE_CARACTERISTIQUES, allColumns,
                DbHelper.CARACTERISTIQUES_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        Caracteristiques newCarac = cursorToCaracteristiques(cursor);
        cursor.close();
        return newCarac;

    }

    public void deleteCaracteristiques(Caracteristiques carac) {
        long id = carac.getId();
        System.out.println("Heros deleted with id: " + id);
        database.delete(DbHelper.TABLE_CARACTERISTIQUES, DbHelper.CARACTERISTIQUES_ID
                + " = " + id, null);
    }

    public void clean(Caracteristiques caracs){
        System.out.println("Base de donnees videe");
        dbHelper.onUpgradeCaracteristiques(database,database.getVersion(),database.getVersion());
    }

    public List<Caracteristiques> getAllCaracteristiques() {
        List<Caracteristiques> caracs = new ArrayList<Caracteristiques>();

        Cursor cursor = database.query(DbHelper.TABLE_CARACTERISTIQUES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Caracteristiques carac = cursorToCaracteristiques(cursor);
            caracs.add(carac);
            cursor.moveToNext();
        }
        cursor.close();
        return caracs;
    }
    private Caracteristiques cursorToCaracteristiques(Cursor cursor) {
        Caracteristiques carac = new Caracteristiques();
        carac.setId(cursor.getLong(0));
        carac.setNom(cursor.getString(1));
        return carac;
    }

}
