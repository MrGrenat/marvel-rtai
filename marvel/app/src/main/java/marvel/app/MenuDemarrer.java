package marvel.app;


import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.widget.TextView;

import marvel.Reponses;


public class MenuDemarrer extends AppCompatActivity{
    private Button startBtn = null;
    private Button mesHerosBtn = null;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Plein Ecran
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_demarrer);

        // Création de l'objet toolbar

        toolbar = findViewById(R.id.toolbar);

        // Imposer l'absence de titre toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //Selection puis Modification du Texte view
        //de la toolbar = titre personnalisé selon la page
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Accueil");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                back();
            }
        });

        startBtn = (Button) findViewById(R.id.startButton);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View startBtn) {
                Intent pageGetPseudo = new Intent(MenuDemarrer.this, GetPseudo.class);
                startActivity(pageGetPseudo);
            }
        });
        mesHerosBtn = (Button) findViewById(R.id.herosButton);
        mesHerosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View startBtn) {
                Intent pageMesHeros = new Intent(MenuDemarrer.this, MesHeros.class);
                startActivity(pageMesHeros);
            }
        });


    }

    //Appel du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_home).setVisible(false);
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
        Intent pageSettingsUser = new Intent(MenuDemarrer.this, SettingsUser.class);
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
