package marvel.DataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import marvel.DbHelper;
import marvel.Tables.Utilisateurs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PierreO on 17/03/2018.
 */

public class UtilisateursDataSource {
    //CHAMPS BASE DE DONNEES
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = {
            DbHelper.UTILISATEUR_ID, DbHelper.UTILISATEUR_PSEUDO
    };

    public UtilisateursDataSource(Context context){
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Utilisateurs createUtilisateurs(String utilisateur){
        ContentValues values = new ContentValues();
        values.put(DbHelper.UTILISATEUR_PSEUDO, utilisateur);

        long insertId = database.insert(DbHelper.TABLE_UTILISATEUR, null,values);
        Cursor cursor = database.query(DbHelper.TABLE_UTILISATEUR, allColumns,
                DbHelper.UTILISATEUR_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        Utilisateurs newUtilisateur = cursorToUtilisateurs(cursor);
        cursor.close();
        return newUtilisateur;

    }

    public void insertUtilisateurs(String pseudo){
        ContentValues values = new ContentValues();
        values.put(DbHelper.UTILISATEUR_PSEUDO, pseudo);


        long insertId = database.insert(DbHelper.TABLE_UTILISATEUR, null,values);
        Cursor cursor = database.query(DbHelper.TABLE_UTILISATEUR, allColumns,
                DbHelper.UTILISATEUR_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        cursor.close();
    }
    public void updatePseudoUtilisateur(String nvPseudo){
        List<Utilisateurs> utilisateurs = getAllUtilisateurs();
        Utilisateurs utilisateur = utilisateurs.get(0);
        long id = utilisateur.getId();

        ContentValues values = new ContentValues();
        values.put(DbHelper.UTILISATEUR_PSEUDO, nvPseudo);

        database.update(DbHelper.TABLE_UTILISATEUR, values, DbHelper.UTILISATEUR_ID
                + " = " + id, null);

    }
    public void deleteUtilisateur(Utilisateurs utilisateur) {
        long id = utilisateur.getId();
        System.out.println("Utilisateur deleted with id: " + id);
        database.delete(DbHelper.TABLE_UTILISATEUR, DbHelper.UTILISATEUR_ID
                + " = " + id, null);
    }

    public void clean(Utilisateurs utilisateur){
        System.out.println("Base de donnees videe");
        dbHelper.onUpgradeUtilisateur(database,database.getVersion(),database.getVersion());
    }

    public List<Utilisateurs> getAllUtilisateurs() {
        List<Utilisateurs> utilisateurs = new ArrayList<Utilisateurs>();

        Cursor cursor = database.query(DbHelper.TABLE_UTILISATEUR,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Utilisateurs utilisateur = cursorToUtilisateurs(cursor);
            utilisateurs.add(utilisateur);
            cursor.moveToNext();
        }
        cursor.close();
        return utilisateurs;
    }
    private Utilisateurs cursorToUtilisateurs(Cursor cursor) {
        Utilisateurs utilisateurs = new Utilisateurs();
        utilisateurs.setId(cursor.getLong(0));
        utilisateurs.setNom(cursor.getString(1));
        //System.out.println("NOOOOOOOOOOOOOOOOOOOM: " + cursor.getString(1));
        return utilisateurs;
    }
}
