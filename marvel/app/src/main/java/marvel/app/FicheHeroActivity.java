package marvel.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import marvel.DataSource.ComicsDataSource;
import marvel.DataSource.HerosDataSource;
import marvel.DataSource.SeriesDataSource;
import marvel.Tables.Comics;
import marvel.Tables.Heros;
import marvel.PartieStatic;
import marvel.Tables.Series;

public class FicheHeroActivity extends AppCompatActivity {

    private Toolbar toolbar;


    private HerosDataSource datasourceHeros =  new HerosDataSource(this);
    private ComicsDataSource datasourceComics = new ComicsDataSource(this);
    private SeriesDataSource datasourceSeries = new SeriesDataSource(this);

    private ImageView photo;
    private TextView tv_nom;
    private TextView tv_desc;
    private TextView tv_apparaitDans;
    private TextView tv_carac;
    private ProgressBar pg_intel;
    private ProgressBar pg_hero;
    private ProgressBar pg_charism;
    private ProgressBar pg_psycho;

    private List<String> heroApparaitComics;
    private List<String> heroApparaitSeries;

    StringBuilder builder1, builder2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Plein Ecran
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_hero);


        // Création de l'objet toolbar
        toolbar = findViewById(R.id.toolbar);

        // Imposer l'absence de titre toolbar
        toolbar.setTitle("");

        //Définir la toolbar comme action bar
        setSupportActionBar(toolbar);

        //Création du Display retour fournit par les actions bars
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Selection puis Modification du Texte view
        //de la toolbar = titre personnalisé selon la page
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Fiche héros");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                back();
            }
        });

        datasourceHeros.open();
        datasourceComics.open();
        datasourceSeries.open();

        builder1 = new StringBuilder();
        builder2 = new StringBuilder();

        heroApparaitComics = new ArrayList<>();
        heroApparaitSeries = new ArrayList<>();

        Heros unHero = new Heros();
        unHero = datasourceHeros.getUnHeros(PartieStatic.getIdheros());
        System.out.println(unHero.toString());
        List<Comics> lesComicsDuHero = new ArrayList<>();
        lesComicsDuHero = datasourceComics.getLesComicsDunHeros(unHero.getId());
        for(int i=0; i<lesComicsDuHero.size(); i++)
        {
            heroApparaitComics.add(lesComicsDuHero.get(i).getNom());
        }
        for (String details : heroApparaitComics) {
            builder1.append(details + "\n");
        }

        List<Series> lesSeriesDuHero = new ArrayList<Series>();
        lesSeriesDuHero = datasourceSeries.getLesSeriesDunHeros(unHero.getId());
        for(int i=0; i<lesSeriesDuHero.size(); i++)
        {
            heroApparaitSeries.add(lesSeriesDuHero.get(i).getNom());
           // System.out.println(lesSeriesDuHero.get(i).getNom());
        }
       // System.out.println("nb series : " + heroApparaitSeries.size());
        for (String details : heroApparaitSeries) {
            builder2.append(details + "\n");
        }

        tv_nom = (TextView)findViewById(R.id.tv_nomHeros);
        tv_desc = (TextView)findViewById(R.id.tv_desc);
        tv_apparaitDans = (TextView)findViewById(R.id.tv_apparait);
        tv_carac = findViewById(R.id.tv_carac);
        tv_nom.setText(unHero.getNom());

        if(unHero.getDesc().length() > 5)
            tv_desc.setText("Description : " + unHero.getDesc());
        else
            tv_desc.setText("Aucune description disponible.");

        photo = (ImageView)findViewById(R.id.iv_photo);
        Picasso.with(this).load(unHero.getUrlImage()).into(photo);
        tv_apparaitDans.setText("Les comics du héros \n"+builder1.toString()+"\n\nLes séries du héros :\n"+builder2.toString());


        String carac = "Les caractéristiques du héros:";

        pg_intel = (ProgressBar)findViewById(R.id.pg_intel);
        pg_hero = (ProgressBar)findViewById(R.id.pg_hero);
        pg_charism = (ProgressBar)findViewById(R.id.pg_charism);
        pg_psycho = (ProgressBar)findViewById(R.id.pg_psycho);

        pg_intel.setMax(5);
        pg_hero.setMax(5);
        pg_charism.setMax(5);
        pg_psycho.setMax(5);

        pg_intel.setProgress(unHero.getIntelligent());
        pg_hero.setProgress(unHero.getHeroique());
        pg_charism.setProgress(unHero.getCharismatique());
        pg_psycho.setProgress(unHero.getPsychopathe());
        /*
        carac += "\nIntelligent: " + unHero.getIntelligent() + "/5";
        carac += "\nHéroïque: " + unHero.getHeroique() + "/5";
        carac += "\nCharismatique: " + unHero.getCharismatique() + "/5";
        carac += "\nPsychopathe: " + unHero.getPsychopathe() + "/5";*/

        tv_carac.setText(carac);

    }

    //Appel du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    //Précise les fonctions à appeler selon le MenuItem selectionné par l'utilisateur
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_settings:
                openSettingsUser();
                return true;
            case R.id.action_settings2:
                openSettingsLaw();
                return true;
            case R.id.action_settings3:
                openMesHeros();
                return true;

            case R.id.action_home:
                openHome();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Fonctions à lancer selon les événements de la navabar
    private void openSettingsUser(){
        Intent pageSettingsUser = new Intent(this, SettingsUser.class);
        startActivity(pageSettingsUser);
        finish();
    }

    private void openSettingsLaw(){
        Intent pageSettingsLaw = new Intent(this, SettingsLaw.class);
        startActivity(pageSettingsLaw);
        finish();
    }

    private void openMesHeros(){
        Intent pageSettingsUser = new Intent(this, MesHeros.class);
        startActivity(pageSettingsUser);
        finish();
    }

    private void openHome(){
        Intent pageHome = new Intent(this, MenuDemarrer.class);
        pageHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pageHome);
        finish();
    }

    private void back(){
        this.finish();
    }

}
