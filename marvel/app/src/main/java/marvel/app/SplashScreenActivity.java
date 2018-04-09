package marvel.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import marvel.DataSource.ComicsDataSource;
import marvel.DataSource.HerosDataSource;
import marvel.DataSource.SeriesDataSource;
import marvel.JSON.enums.EHeros;
import marvel.Tables.Comics;
import marvel.Tables.Heros;
import marvel.Tables.Series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class SplashScreenActivity extends AppCompatActivity {

    private HerosDataSource datasource;
    private SeriesDataSource datasourceSeries;
    private ComicsDataSource datasourceComics;


    private ArrayList<Heros> lesHeros;
    private HashMap<Heros, ArrayList<Comics>> herosComics;
    private HashMap<Heros, ArrayList<Series>> herosSeries;

    private Thread threadJSON;

    private TextView textView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Plein Ecran
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        lesHeros = new ArrayList<>();
        herosComics = new HashMap<>();
        herosSeries = new HashMap<>();

        datasource = new HerosDataSource(this);
        datasourceSeries = new SeriesDataSource(this);
        datasourceComics = new ComicsDataSource(this);


        datasource.open();
        datasourceComics.open();
        datasourceSeries.open();

        textView = (TextView)findViewById(R.id.textView);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();


        if(activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED)
        {
            connected = true;
        }


        if(connected)
        {
            if (datasource.getAllHeros().isEmpty()) {

                textView.setText("Connexion avec les Avengers...");

                threadJSON = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            JSONObject json = null;

                            try {
                                for (EHeros heros : EHeros.values()) {
                                    String name = heros.toString().replaceAll(" ", "%20");
                                    String link = "http://gateway.marvel.com/v1/public/characters?ts=1&apikey=7438083edc62a38cfbbcdb2f132502f0&hash=51c42a9ff12f032743ac50438e9ad95d&name=" + name;
                                    System.out.println("LINK: " + link);

                                    json = readJsonFromUrl(link);
                                    JSONObject jsonD = json.getJSONObject("data");
                                    JSONArray jsonA = jsonD.getJSONArray("results");
                                    JSONObject jsonH = jsonA.getJSONObject(0);
                                    Heros leHeros = new Heros();
                                    leHeros.setNom(jsonH.get("name").toString());
                                    leHeros.setDesc(jsonH.get("description").toString());

                                    JSONObject jsonI = jsonH.getJSONObject("thumbnail");
                                    String img = jsonI.getString("path") + "." + jsonI.getString("extension");

                                    leHeros.setUrlImage(img);


                                    lesHeros.add(leHeros);

                                    JSONObject jsonCList = jsonH.getJSONObject("comics");
                                    JSONArray jsonC = jsonCList.getJSONArray("items");

                                    ArrayList<Comics> comicsHeros = new ArrayList<>();
                                    for (int i = 0; i < jsonC.length(); i++) {
                                        Comics leComics = new Comics();
                                        leComics.setNom(((JSONObject) jsonC.get(i)).getString("name"));
                                        comicsHeros.add(leComics);
                                    }
                                    herosComics.put(leHeros, comicsHeros);

                                    JSONObject jsonSList = jsonH.getJSONObject("series");
                                    JSONArray jsonS = jsonSList.getJSONArray("items");

                                    ArrayList<Series> seriesHeros = new ArrayList<>();
                                    for (int i = 0; i < jsonS.length(); i++) {
                                        Series laSerie = new Series();
                                        laSerie.setNom(((JSONObject) jsonS.get(i)).getString("name"));
                                        seriesHeros.add(laSerie);
                                    }
                                    herosSeries.put(leHeros, seriesHeros);
                                }


                                for (Heros unHeros : lesHeros) {
                                    textView.post(new Runnable() {
                                        public void run() {

                                            textView.setText("Enregistrement de la liste des héros.\nVeuillez patienter...");
                                        }
                                    });
                                    Thread.sleep(100);

                                    System.out.println(unHeros.getNom() + " - IMG: " + unHeros.getUrlImage());
                                    long idHeros = datasource.insertHeroesGetID(unHeros.getNom(), unHeros.getDesc(), unHeros.getUrlImage());

                                    for (Series series : herosSeries.get(unHeros)) {
                                        long idSeries = datasourceSeries.insertSeriesGetID(series.getNom());
                                        datasourceSeries.insertSeriesHeros(idSeries, idHeros);
                                    }
                                    for (Comics comics : herosComics.get(unHeros)) {
                                        long idComics = datasourceComics.insertComicsGetID(comics.getNom());
                                        datasourceComics.insertComicsHeros(idComics, idHeros);
                                    }

                                    if(unHeros.getNom().equals("Iron Man")){
                                        unHeros.setCarateristiques(4,5,5,1);
                                        datasource.updateCarac(unHeros.getNom(),4,5,5,1);
                                    }
                                    else if(unHeros.getNom().equals("Deadpool")){
                                        unHeros.setCarateristiques(2,3,4,5);
                                        datasource.updateCarac(unHeros.getNom(),2,3,4,5);
                                    }
                                    else if(unHeros.getNom().equals("Captain America")){
                                        unHeros.setCarateristiques(5,3,3,0);
                                        datasource.updateCarac(unHeros.getNom(),5,3,3,0);
                                    }
                                    else if(unHeros.getNom().equals("Black Widow")){
                                        unHeros.setCarateristiques(3,4,2,3);
                                        datasource.updateCarac(unHeros.getNom(),3,4,2,3);
                                    }
                                    else if(unHeros.getNom().equals("HULK")){
                                        unHeros.setCarateristiques(2,1,1,4);
                                        datasource.updateCarac(unHeros.getNom(),2,1,1,2);
                                    }
                                    else if(unHeros.getNom().equals("Rogue")){
                                        unHeros.setCarateristiques(3,3,3,2);
                                        datasource.updateCarac(unHeros.getNom(),3,3,3,2);
                                    }
                                    else if(unHeros.getNom().equals("3-D Man")){
                                        unHeros.setCarateristiques(2,4,1,2);
                                        datasource.updateCarac(unHeros.getNom(),2,4,1,2);
                                    }
                                    else if(unHeros.getNom().equals("Squirrel Girl")){
                                        unHeros.setCarateristiques(3,2,4,3);
                                        datasource.updateCarac(unHeros.getNom(),3,2,4,3);
                                    }
                                    else if(unHeros.getNom().equals("Nova")){
                                        unHeros.setCarateristiques(2,3,2,1);
                                        datasource.updateCarac(unHeros.getNom(),2,3,2,1);
                                    }
                                    else if(unHeros.getNom().equals("Falcon")){
                                        unHeros.setCarateristiques(3,4,2,0);
                                        datasource.updateCarac(unHeros.getNom(),3,4,2,0);
                                    }
                                    else if(unHeros.getNom().equals("Blade")){
                                        unHeros.setCarateristiques(4,2,3,2);
                                        datasource.updateCarac(unHeros.getNom(),4,2,3,2);
                                    }
                                    else if(unHeros.getNom().equals("Black Panther")){
                                        unHeros.setCarateristiques(4,4,3,1);
                                        datasource.updateCarac(unHeros.getNom(),4,4,3,1);
                                    }
                                    else if(unHeros.getNom().equals("Doctor Strange")){
                                        unHeros.setCarateristiques(3,3,2,3);
                                        datasource.updateCarac(unHeros.getNom(),3,3,2,3);
                                    }
                                    else if(unHeros.getNom().equals("Iron Fist (Danny Rand)")){
                                        unHeros.setCarateristiques(3,4,2,0);
                                        datasource.updateCarac(unHeros.getNom(),3,4,2,0);
                                    }
                                    else if(unHeros.getNom().equals("Groot")){
                                        unHeros.setCarateristiques(5,1,2,0);
                                        datasource.updateCarac(unHeros.getNom(),5,1,2,0);
                                    }
                                    else if(unHeros.getNom().equals("Rocket Raccoon")){
                                        unHeros.setCarateristiques(3,5,3,2);
                                        datasource.updateCarac(unHeros.getNom(),3,5,3,2);
                                    }
                                    else if(unHeros.getNom().equals("Iceman")){
                                        unHeros.setCarateristiques(3,2,1,1);
                                        datasource.updateCarac(unHeros.getNom(),3,2,1,1);
                                    }
                                    else if(unHeros.getNom().equals("Jean Grey")){
                                        unHeros.setCarateristiques(5,4,4,3);
                                        datasource.updateCarac(unHeros.getNom(),5,4,4,3);
                                    }
                                    else if(unHeros.getNom().equals("War Machine (Ultimate)")){
                                        unHeros.setCarateristiques(3,2,4,2);
                                        datasource.updateCarac(unHeros.getNom(),3,2,4,2);
                                    }
                                    else if(unHeros.getNom().equals("Cyclops")){
                                        unHeros.setCarateristiques(3,2,2,3);
                                        datasource.updateCarac(unHeros.getNom(),3,2,2,3);
                                    }
                                    else if(unHeros.getNom().equals("Daredevil")){
                                        unHeros.setCarateristiques(3,2,3,3);
                                        datasource.updateCarac(unHeros.getNom(),3,2,3,3);
                                    }
                                    else if(unHeros.getNom().equals("Thor")){
                                        unHeros.setCarateristiques(4,1,5,2);
                                        datasource.updateCarac(unHeros.getNom(),4, 1,5,2);
                                    }
                                    else if(unHeros.getNom().equals("Hawkeye")){
                                        unHeros.setCarateristiques(2,3,0,3);
                                        datasource.updateCarac(unHeros.getNom(),2, 3,0,3);
                                    }
                                    else if(unHeros.getNom().equals("Johnny Blaze")){
                                        unHeros.setCarateristiques(3,1,2,5);
                                        datasource.updateCarac(unHeros.getNom(),3, 1,2,5);
                                    }
                                    else if(unHeros.getNom().equals("Punisher")){
                                        unHeros.setCarateristiques(2,3,4,5);
                                        datasource.updateCarac(unHeros.getNom(),2, 3,4,5);
                                    }
                                    else if(unHeros.getNom().equals("Black Bolt")){
                                        unHeros.setCarateristiques(1,4,4,0);
                                        datasource.updateCarac(unHeros.getNom(),1, 4,4,0);
                                    }
                                    else if(unHeros.getNom().equals("Gambit")){
                                        unHeros.setCarateristiques(2,4,4,2);
                                        datasource.updateCarac(unHeros.getNom(),2, 4,4,2);
                                    }
                                    else if(unHeros.getNom().equals("Moon Knight")){
                                        unHeros.setCarateristiques(3,2,4,3);
                                        datasource.updateCarac(unHeros.getNom(),3, 2,4,3);
                                    }
                                    else if(unHeros.getNom().equals("Spider-Man")){
                                        unHeros.setCarateristiques(3,4,0,2);
                                        datasource.updateCarac(unHeros.getNom(),3, 4,0,2);
                                    }
                                    else if(unHeros.getNom().equals("Vision")){
                                        unHeros.setCarateristiques(3,5,0,1);
                                        datasource.updateCarac(unHeros.getNom(),3, 5,0,1);
                                    }

                                    System.out.println(unHeros.toString());
                                }

                                Intent pageMenuDemarrer = new Intent(SplashScreenActivity.this, MenuDemarrer.class);
                                startActivity(pageMenuDemarrer);
                                finish();

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                threadJSON.start();
            }
            else if( !(datasource.getAllHeros().isEmpty()) )
            {
                textView.setText("Récupération de la liste des héros.\nVeuillez patienter...");

                Timer timer = new Timer();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent pageMenuDemarrer = new Intent(SplashScreenActivity.this, MenuDemarrer.class);
                        startActivity(pageMenuDemarrer);
                        finish();
                    }
                }, 2000);
            }
        }
        else
        {
            textView.setText("Connexion avec les Avengers impossible!");
            noConnectionMessage();
        }
    }




    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        datasource.close();
        super.onPause();
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1)
        {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        URL is = new URL(url);
        BufferedReader rd = new BufferedReader(new InputStreamReader(is.openStream()));

        try
        {
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;

        }
        finally
        {
            rd.close();
        }
    }

    public void noConnectionMessage() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Pas d'internet")
                .setCancelable(false)
                .setMessage("Veuillez activer votre conenxion internet pour continuer.")
                .setPositiveButton("Quitter", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .show();
    }
}
