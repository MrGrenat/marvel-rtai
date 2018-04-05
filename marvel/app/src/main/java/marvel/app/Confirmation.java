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
import android.widget.Button;
import android.widget.TextView;

import marvel.JSON.enums.ECaracteristiques;
import marvel.Reponses;

public class Confirmation extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Plein Ecran
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);


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
        title.setText("Etape confirmation");

        TextView qA = findViewById(R.id.qA);
        TextView repA = findViewById(R.id.repA);

        TextView qB = findViewById(R.id.qB);
        TextView repB = findViewById(R.id.repB);

        TextView qC = findViewById(R.id.qC);
        TextView repC = findViewById(R.id.repC);

        TextView qD = findViewById(R.id.qD);
        TextView repD = findViewById(R.id.repD);

        TextView qE = findViewById(R.id.qE);
        TextView repE = findViewById(R.id.repE);

        qA.setText(Reponses.getqA());
        qB.setText(Reponses.getqB());
        qC.setText(Reponses.getqC());
        qD.setText(Reponses.getqD());
        qE.setText(Reponses.getqE());

        repA.setText(Reponses.getRepAValue());
        repB.setText(Reponses.getRepBValue());
        repC.setText(Reponses.getRepCValue());
        repD.setText(Reponses.getRepDValue());
        repE.setText(Reponses.getRepEValue());


        if(Reponses.getRepA().equals(ECaracteristiques.INTELLIGENT))
            repA.setBackgroundColor(getResources().getColor(R.color.colorRed));
        else if(Reponses.getRepA().equals(ECaracteristiques.HEROIQUE))
            repA.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        else if(Reponses.getRepA().equals(ECaracteristiques.CHARISMATIQUE))
            repA.setBackgroundColor(getResources().getColor(R.color.colorLBlue));
        else if(Reponses.getRepA().equals(ECaracteristiques.PSYCHOPATHE))
            repA.setBackgroundColor(getResources().getColor(R.color.colorOrange));

        if(Reponses.getRepB().equals(ECaracteristiques.INTELLIGENT))
            repB.setBackgroundColor(getResources().getColor(R.color.colorRed));
        else if(Reponses.getRepB().equals(ECaracteristiques.HEROIQUE))
            repB.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        else if(Reponses.getRepB().equals(ECaracteristiques.CHARISMATIQUE))
            repB.setBackgroundColor(getResources().getColor(R.color.colorLBlue));
        else if(Reponses.getRepB().equals(ECaracteristiques.PSYCHOPATHE))
            repB.setBackgroundColor(getResources().getColor(R.color.colorOrange));

        if(Reponses.getRepC().equals(ECaracteristiques.INTELLIGENT))
            repB.setBackgroundColor(getResources().getColor(R.color.colorRed));
        else if(Reponses.getRepC().equals(ECaracteristiques.HEROIQUE))
            repC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        else if(Reponses.getRepC().equals(ECaracteristiques.CHARISMATIQUE))
            repC.setBackgroundColor(getResources().getColor(R.color.colorLBlue));
        else if(Reponses.getRepC().equals(ECaracteristiques.PSYCHOPATHE))
            repC.setBackgroundColor(getResources().getColor(R.color.colorOrange));

        if(Reponses.getRepD().equals(ECaracteristiques.INTELLIGENT))
            repD.setBackgroundColor(getResources().getColor(R.color.colorRed));
        else if(Reponses.getRepD().equals(ECaracteristiques.HEROIQUE))
            repD.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        else if(Reponses.getRepD().equals(ECaracteristiques.CHARISMATIQUE))
            repD.setBackgroundColor(getResources().getColor(R.color.colorLBlue));
        else if(Reponses.getRepD().equals(ECaracteristiques.PSYCHOPATHE))
            repD.setBackgroundColor(getResources().getColor(R.color.colorOrange));

        if(Reponses.getRepE().equals(ECaracteristiques.INTELLIGENT))
            repE.setBackgroundColor(getResources().getColor(R.color.colorRed));
        else if(Reponses.getRepE().equals(ECaracteristiques.HEROIQUE))
            repE.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        else if(Reponses.getRepE().equals(ECaracteristiques.CHARISMATIQUE))
            repE.setBackgroundColor(getResources().getColor(R.color.colorLBlue));
        else if(Reponses.getRepE().equals(ECaracteristiques.PSYCHOPATHE))
            repE.setBackgroundColor(getResources().getColor(R.color.colorOrange));

        Button btnModif = (Button) findViewById(R.id.bt_modifier);

        btnModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pageQuestionnaire = new Intent(Confirmation.this, Questionnaire.class);
                startActivity(pageQuestionnaire);
                finish();
            }
        });

        Button btnValider = (Button) findViewById(R.id.bt_valider);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pageTonHeros = new Intent(Confirmation.this, TonHerosActivity.class);
                startActivity(pageTonHeros);
                finish();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                back();
            }
        });


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
