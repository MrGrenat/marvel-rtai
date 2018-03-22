package marvel.app;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

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

import marvel.R;

public class SplashScreenActivity extends AppCompatActivity {

    private HerosDataSource datasource;
    private SeriesDataSource datasourceSeries;
    private ComicsDataSource datasourceComics;


    private ArrayList<Heros> lesHeros;
    private HashMap<Heros, ArrayList<Comics>> herosComics;
    private HashMap<Heros, ArrayList<Series>> herosSeries;

    private Thread threadJSON;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

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


        if (datasource.getAllHeros().isEmpty()) {
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


                            System.out.println("VIDE");
                            for (Heros unHeros : lesHeros) {

                                System.out.println(unHeros.getNom());
                                datasource.insertHeroes(unHeros.getNom(), unHeros.getDesc(), unHeros.getUrlImage());

                                for (Series series : herosSeries.get(unHeros)) {
                                    datasourceSeries.insertSeries(series.getNom());

                                }
                                for (Comics comics : herosComics.get(unHeros)) {
                                    datasourceComics.insertComics(comics.getNom());

                                }


                            }

                            Intent pageMenuDemarrer = new Intent(SplashScreenActivity.this, MenuDemarrer.class);
                            startActivity(pageMenuDemarrer);

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
        else{
            Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent pageMenuDemarrer = new Intent(SplashScreenActivity.this, MenuDemarrer.class);
                    startActivity(pageMenuDemarrer);
                }
            }, 2000);
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
}
