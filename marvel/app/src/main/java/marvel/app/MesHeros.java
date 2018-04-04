package marvel.app;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import marvel.DataSource.AssociationsDataSource;
import marvel.DataSource.PartiesDataSource;
import marvel.DataSource.UtilisateursDataSource;
import marvel.Tables.Association;
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
    private Button testBtn;
    private String timeStamp;

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

        datasourceParties = new PartiesDataSource(getApplicationContext());
        datasourceParties.open();

        datasourceAssociations = new AssociationsDataSource(getApplicationContext());
        datasourceAssociations.open();

        List<Partie> parties = datasourceParties.getAllParties();
        List<Association> assocs = datasourceAssociations.getAllAssocs();
        if (parties != null){
            System.out.println("Au moins une partie");
            for (Partie p:parties){
                System.out.println("Partie: "+p.getId());
                if (p.getTermine()>=1 ) {
                    /*
                    System.out.println("Partie complete trouvée");
                    System.out.println(p.getId());
                    System.out.println(p.getLienPhoto());
                    System.out.println(p.getTermine());
                    System.out.println(p.getDate());*/

                    if (assocs != null && !assocs.isEmpty()) {
                        for (Association a:assocs){
                            if(a.getIdEtranger() == p.getId()){

                                System.out.println("");
                                System.out.println("Partie : "+p.getId());
                                System.out.println("Date : "+p.getDate());
                                System.out.println("idHero : "+a.getIdHeros());
                            }
                        }

                    }else{
                        System.out.println("Pas de parties complètes trouvées");
                    }
                }

            }
        }else{
            System.out.println("La table 'parties' est vide, commencer une nvlle partie afin de voir vos héros retournés");

        }

        testBtn = findViewById(R.id.detail);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View detail) {
                Intent pageFicheHeros = new Intent(MesHeros.this, FicheHeroActivity.class);
                startActivity(pageFicheHeros);
                finish();
            }
        });


    }

    public void testInsertTablePartie(){
        Partie partieToInsert = new Partie();
        partieToInsert.setLienPhoto("drawable/exemple.png");
        partieToInsert.setId(1);
        partieToInsert.setTermine(1);
        timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
        partieToInsert.setDate(timeStamp);

        datasourceParties = new PartiesDataSource(getApplicationContext());
        datasourceParties.open();
        datasourceParties.insertPartie(partieToInsert);

        Association assocToInsert = new Association();
        assocToInsert.setIdHeros(1);
        assocToInsert.setIdEtranger(1);

        datasourceAssociations = new AssociationsDataSource(getApplicationContext());
        datasourceAssociations.open();
        datasourceAssociations.insertAssociation(assocToInsert);

        System.out.println("Création d'une nouvelle partie");

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
        Intent pageSettingsLaw = new Intent(this, MenuDemarrer.class);
        startActivity(pageSettingsLaw);
    }

    private void back(){
        this.finish();
    }

}
