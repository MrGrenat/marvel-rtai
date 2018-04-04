package marvel.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TonHerosActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ton_heros);

        // Création de l'objet toolbar
        toolbar = findViewById(R.id.toolbar);

        // Imposer l'absence de titre toolbar
        toolbar.setTitle("");

        //Définir la toolbar comme action bar
        setSupportActionBar(toolbar);

        //Création du Display retour fournit par les actions bars
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        //Selection puis Modification du Texte view
        //de la toolbar = titre personnalisé selon la page
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Ton Héros");

        Button accueil = (Button)findViewById(R.id.buttonAccueil);
        Button ficheHeros = (Button)findViewById(R.id.buttonFicheHeros);

        accueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pageAccueil = new Intent(TonHerosActivity.this, MenuDemarrer.class);
                startActivity(pageAccueil);
                finish();
            }
        });


        ficheHeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pageFicheHeros = new Intent(TonHerosActivity.this, FicheHeroActivity.class);
                startActivity(pageFicheHeros);
                finish();
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


}
