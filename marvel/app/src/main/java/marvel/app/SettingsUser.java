package marvel.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import marvel.DataSource.UtilisateursDataSource;
import marvel.Tables.Utilisateurs;

/**
 * Created by Adrien on 07/03/2018.
 */

public class SettingsUser  extends AppCompatActivity {

    private UtilisateursDataSource datasourceUtilisateur;
    private EditText pseudoInput;
    private String pseudo;


    protected void onCreate(Bundle savedInstanceState) {
        //Plein Ecran
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_user);

        // Création d el'objet toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Imposer l'absence de titre toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //Création du Display retour fournit par les actions bars
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Selection puis Modification du Texte view
        //de la toolbar = titre personnalisé selon la page
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Paramètres");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                back();
            }
        });

        Button btValider = (Button)findViewById(R.id.btnChangerPseudo);

        datasourceUtilisateur = new UtilisateursDataSource(getApplicationContext());
        datasourceUtilisateur.open();

        pseudoInput = (EditText)findViewById(R.id.pseudoInput);

        if(datasourceUtilisateur.getAllUtilisateurs().isEmpty()){
            //pseudo = etPseudo.getText().toString();
            ((TextView)findViewById(R.id.paraphe01)).setText("Désolé, vous devez avoir commencé au moins une partie pour pouvoir modifier votre pseudo, retournez sur l'ecran d'accueil et appuyer sur le bouton start. ");
            pseudoInput.setVisibility(View.INVISIBLE);
        }else{
            List<Utilisateurs> pseudoList = datasourceUtilisateur.getAllUtilisateurs();
            for (Utilisateurs utils:pseudoList) {
                //System.out.println("-------------------------PSEUDO----------------------");

                pseudo = utils.getNom();

                System.out.println(pseudo);

            }

            ((TextView)findViewById(R.id.paraphe01)).setText("Bonjour "+pseudo+"\nSi vous le souhaitez vous pouvez modifier votre pseudo ci dessous: ");
            pseudoInput.setVisibility(View.VISIBLE);

        }
        btValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                changementPseudo();
            }
        });

    }

    //gere les erreurs
    public void changementPseudo() {
        //si l'utilisateur ne rentre pas de pseudo
        if(pseudoInput.getText().toString().trim().length() == 0 && datasourceUtilisateur.getAllUtilisateurs().isEmpty()) {
            pseudoInput.setError("Veuillez entrer votre pseudo");
            return;
        }
        //sinon ouvre la page suivante
        else
        {
            pseudo = pseudoInput.getText().toString();
            //Mise à jour du pseudo
            if(! datasourceUtilisateur.getAllUtilisateurs().isEmpty()){
                datasourceUtilisateur.updatePseudoUtilisateur(pseudo);
            }

            Intent accueil = new Intent(SettingsUser.this, MenuDemarrer.class);
            startActivity(accueil);
            finish();
        }
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
            case R.id.action_home:
                openHome();
                return true;
            case R.id.action_settings3:
                openMesHeros();
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
        Intent pageSettingsLaw = new Intent(this, SettingsLaw.class);
        startActivity(pageSettingsLaw);
        finish();
    }
    private void openHome(){
        Intent pageSettingsLaw = new Intent(this, MenuDemarrer.class);
        startActivity(pageSettingsLaw);
        finish();
    }

    private void back(){
        this.finish();
    }


}
