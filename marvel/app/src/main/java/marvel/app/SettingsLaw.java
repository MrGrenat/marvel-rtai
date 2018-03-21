package marvel.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Adrien on 07/03/2018.
 */

public class SettingsLaw  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_law);

    }
    //Appel du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    private void back(){
        this.finish();
    }
    private void openSettingsUser(){
        Intent pageSettingsUser = new Intent(SettingsLaw.this, SettingsUser.class);
        startActivity(pageSettingsUser);
    }
    private void openSettingsLaw(){
        Intent pageSettingsLaw = new Intent(SettingsLaw.this, SettingsLaw.class);
        startActivity(pageSettingsLaw);
    }
    private void openHome(){
        Intent pageSettingsLaw = new Intent(this, MenuDemarrer.class);
        startActivity(pageSettingsLaw);
    }
    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_back:
                back();
                return true;
            case R.id.action_settings:
                openSettingsUser();
                return true;
            case R.id.action_settings2:
                openSettingsLaw();
                return true;
            case R.id.action_home:
                openHome();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}