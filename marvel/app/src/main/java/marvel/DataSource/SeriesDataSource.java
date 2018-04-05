package marvel.DataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import marvel.DbHelper;
import marvel.Tables.ApparaitDansSerie;
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
    private String[] columnsSerie = {
            DbHelper.EPS_SERIES_ID,
            DbHelper.EPS_HEROS_ID
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

    public List<Series> getLesSeriesDunHeros(long id) {
        List<Series> series = new ArrayList<Series>();
        List<Long> idSeries = new ArrayList<Long>();

        Cursor cursor1 = database.query(DbHelper.TABLE_EST_PRESENT_SERIES,
                columnsSerie, DbHelper.EPS_HEROS_ID+"="+id, null, null, null, null);

        cursor1.moveToFirst();
        while (!cursor1.isAfterLast()) {
            ApparaitDansSerie serie = AssociationSeriesDataSource.cursorToPartie(cursor1);
            idSeries.add(serie.getIdSerie());
            cursor1.moveToNext();
        }
        cursor1.close();

        for(int i=0; i<idSeries.size(); i++)
        {
            Cursor cursor = database.query(DbHelper.TABLE_SERIES,
                    allColumns, "_idSeries="+idSeries.get(i).intValue(), null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Series serie = cursorToSeries(cursor);
                series.add(serie);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return series;
    }

    public long insertSeriesGetID(String nom){
        ContentValues values = new ContentValues();
        values.put(DbHelper.SERIES_NOM, nom);

        long insertId = database.insert(DbHelper.TABLE_SERIES, null,values);
        Cursor cursor = database.query(DbHelper.TABLE_SERIES, allColumns,
                DbHelper.SERIES_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        cursor.close();

        return insertId;
    }

    public void insertSeriesHeros(long idSeries, long idHeros){
        ContentValues values = new ContentValues();
        values.put(DbHelper.EPS_SERIES_ID, idSeries);
        values.put(DbHelper.EPS_HEROS_ID, idHeros);

        database.insert(DbHelper.TABLE_EST_PRESENT_SERIES, null,values);
    }

    public void deleteSeries(Series series) {
        long id = series.getId();
       // System.out.println("Series deleted with id: " + id);
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
