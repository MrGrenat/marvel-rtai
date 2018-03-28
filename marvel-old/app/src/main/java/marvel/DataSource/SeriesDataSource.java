package marvel.DataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import marvel.DbHelper;
import marvel.Tables.Series;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by potmane on 21/03/2018.
 */

public class SeriesDataSource {

    //CHAMPS BASE DE DONNEES
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = {
            DbHelper.SERIES_ID,
            DbHelper.SERIES_NOM
    };

    public SeriesDataSource(Context context){
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Series createSeries(String series){
        ContentValues values = new ContentValues();
        values.put(DbHelper.SERIES_NOM, series);

        long insertId = database.insert(DbHelper.TABLE_SERIES, null,values);
        Cursor cursor = database.query(DbHelper.TABLE_SERIES, allColumns,
                DbHelper.SERIES_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        Series newSeries = cursorToSeries(cursor);
        cursor.close();
        return newSeries;

    }

    public void insertSeries(String nom){
        ContentValues values = new ContentValues();
        values.put(DbHelper.SERIES_NOM, nom);

        long insertId = database.insert(DbHelper.TABLE_SERIES, null,values);
        Cursor cursor = database.query(DbHelper.TABLE_SERIES, allColumns,
                DbHelper.SERIES_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void deleteSeries(Series series) {
        long id = series.getId();
        System.out.println("Series deleted with id: " + id);
        database.delete(DbHelper.TABLE_SERIES, DbHelper.SERIES_ID
                + " = " + id, null);
    }

    public void clean(Series series){
        System.out.println("Base de donnees videe");
        dbHelper.onUpgradeSeries(database,database.getVersion(),database.getVersion());
    }

    public List<Series> getAllSeries() {
        List<Series> series = new ArrayList<Series>();

        Cursor cursor = database.query(DbHelper.TABLE_SERIES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Series serie = cursorToSeries(cursor);
            series.add(serie);
            cursor.moveToNext();
        }
        cursor.close();
        return series;
    }
    private Series cursorToSeries(Cursor cursor) {
        Series series = new Series();
        series.setId(cursor.getLong(0));
        series.setNom(cursor.getString(1));
        return series;
    }
}
