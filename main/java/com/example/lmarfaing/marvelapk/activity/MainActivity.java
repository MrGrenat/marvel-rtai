package com.example.lmarfaing.marvelapk.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lmarfaing.marvelapk.R;
import com.example.lmarfaing.marvelapk.enums.EHeros;
import com.example.lmarfaing.marvelapk.utils.Comics;
import com.example.lmarfaing.marvelapk.utils.Heros;
import com.example.lmarfaing.marvelapk.utils.Series;

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
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Heros> lesHeros;
    private HashMap<Heros, ArrayList<Comics>> herosComics;
    private HashMap<Heros, ArrayList<Series>> herosSeries;
    private Thread threadJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lesHeros = new ArrayList<>();
        herosComics = new HashMap<>();
        herosSeries = new HashMap<>();

        System.out.println("START MARVEL APK");

        /*A RAJOUTER DANS LE AndroidManifest.xml

            <uses-permission android:name="android.permission.INTERNET"/>

            AVANT LE <application

        */


        threadJSON = new Thread(new Runnable() {

            @Override
            public void run() {
                try
                {
                    JSONObject json = null;

                    try
                    {
                        for(EHeros heros : EHeros.values())
                        {
                            String name = heros.toString().replaceAll(" ", "%20");
                            String link = "http://gateway.marvel.com/v1/public/characters?ts=1&apikey=7438083edc62a38cfbbcdb2f132502f0&hash=51c42a9ff12f032743ac50438e9ad95d&name=" + name;
                            System.out.println("LINK: " + link);

                            json = readJsonFromUrl(link);
                            JSONObject jsonD = json.getJSONObject("data");
                            JSONArray jsonA = jsonD.getJSONArray("results");
                            JSONObject jsonH = jsonA.getJSONObject(0);
                            Heros leHeros = new Heros();
                            leHeros.setNom(jsonH.get("name").toString());
                            leHeros.setDescription(jsonH.get("description").toString());

                            JSONObject jsonI = jsonH.getJSONObject("thumbnail");
                            String img = jsonI.getString("path") + "." + jsonI.getString("extension");

                            leHeros.setImgUrl(img);


                            lesHeros.add(leHeros);

                            JSONObject jsonCList = jsonH.getJSONObject("comics");
                            JSONArray jsonC = jsonCList.getJSONArray("items");

                            ArrayList<Comics> comicsHeros = new ArrayList<>();
                            for(int i = 0; i < jsonC.length(); i++)
                            {
                                Comics leComics = new Comics();
                                leComics.setNom(   ((JSONObject)jsonC.get(i)).getString("name")  );
                                comicsHeros.add(leComics);
                            }
                            herosComics.put(leHeros, comicsHeros);

                            JSONObject jsonSList = jsonH.getJSONObject("series");
                            JSONArray jsonS = jsonSList.getJSONArray("items");

                            ArrayList<Series> seriesHeros = new ArrayList<>();
                            for(int i = 0; i < jsonS.length(); i++)
                            {
                                Series laSerie = new Series();
                                laSerie.setNom(   ((JSONObject)jsonS.get(i)).getString("name")  );
                                seriesHeros.add(laSerie);
                            }
                            herosSeries.put(leHeros, seriesHeros);
                        }


                        for(Heros unHeros : lesHeros)
                        {
                            System.out.println("HERO: " + unHeros.getNom());
                            System.out.println("    - Description: " + unHeros.getDescription());
                            System.out.println("    - Image URL: " + unHeros.getImgUrl());
                            System.out.println("    - Comics: ");
                            for(Comics comics : herosComics.get(unHeros))
                            {
                                System.out.println("       - " + comics.getNom());
                            }
                            System.out.println("    - Séries: ");
                            for(Series series : herosSeries.get(unHeros))
                            {
                                System.out.println("       - " + series.getNom());
                            }
                        }

                        threadJSON.interrupt();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        threadJSON.start();
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
