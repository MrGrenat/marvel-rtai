package marvel.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;

import marvel.DataSource.AssociationsDataSource;
import marvel.DataSource.HerosDataSource;
import marvel.DataSource.PartiesDataSource;
import marvel.Tables.Association;
import marvel.Tables.Heros;
import marvel.Tables.Partie;

@SuppressLint("Registered")
public class MesHeros extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView photo;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText etPseudo;
    private String pseudo;

    private PartiesDataSource datasourceParties;
    private AssociationsDataSource datasourceAssociations;
    private HerosDataSource datasourceHeros;

    private Button testBtn;
    private String timeStamp;

    private List<MesHerosForm> herosFormList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HerosAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Plein Ecran
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_heros);

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
        title.setText("Mes Héros");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                back();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new HerosAdapter(herosFormList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareHerosData();


    }

    private void prepareHerosData() {
        MesHerosForm herosForm;
        datasourceParties = new PartiesDataSource(getApplicationContext());
        datasourceParties.open();
        List<Partie> parties = datasourceParties.getAllParties();

        datasourceAssociations = new AssociationsDataSource(getApplicationContext());
        datasourceAssociations.open();
        List<Association> assocs = datasourceAssociations.getAllAssocs();

        datasourceHeros = new HerosDataSource((getApplicationContext()));
        datasourceHeros.open();
        List<Heros> heros = datasourceHeros.getAllHeros();

        if (parties != null){
            System.out.println("Au moins une partie");
            for (Partie p:parties){
                System.out.println("Partie trouvée : partie "+p.getId());
                if (p.getTermine()>=1 ) {

                    if (assocs != null && !assocs.isEmpty()) {
                        for (Association a:assocs){
                            if(a.getIdEtranger() == p.getId()){

                                int i = 0;
                                for (Heros h:heros){
                                    if(a.getIdHeros() == h.getId()){

                                        System.out.println("Partie Complète: partie "+p.getId());
                                        System.out.println("urlPhoto : "+h.getUrlImage());
                                        System.out.println("NomHeros : "+h.getNom());
                                        System.out.println("Date : "+p.getDate());

                                        Context ctxt = getApplicationContext();
                                        herosForm = new MesHerosForm(h.getNom(), h.getUrlImage(), p.getDate(), ctxt, p.getId());
                                        herosFormList.add(herosForm);
                                    }
                                }
                            }
                        }

                    }else System.out.println("Pas de parties complètes trouvées");
                }
            }
        }else System.out.println("La table 'parties' est vide, commencer une nvlle partie afin de voir vos héros retournés");
        datasourceAssociations.close();
        datasourceParties.close();
        datasourceHeros.close();

        mAdapter.notifyDataSetChanged();
    }

    //Appel du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    //Précise les fonctions à appeler
    //selon le MenuItem selectionné par l'utilisateur
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


    //Fonctions à lancer
    //selon les événements de la navabar
    private void openSettingsUser(){
        Intent pageSettingsUser = new Intent(this, SettingsUser.class);
        startActivity(pageSettingsUser);
    }
    private void openSettingsLaw(){
        Intent pageSettingsLaw = new Intent(this, SettingsLaw.class);
        startActivity(pageSettingsLaw);
    }

    private void openMesHeros(){
        Intent pageSettingsUser = new Intent(this, MesHeros.class);
        startActivity(pageSettingsUser);
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
