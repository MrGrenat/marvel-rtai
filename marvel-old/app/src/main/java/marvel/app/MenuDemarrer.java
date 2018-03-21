package marvel.app;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;


public class MenuDemarrer extends AppCompatActivity{
    Button startBtn = null;
    ImageView imageBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_demarrer);
        startBtn = (Button) findViewById(R.id.startButton);
        System.out.println(startBtn);
        System.out.println("ici");
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View startBtn) {
                Intent pageGetPseudo = new Intent(MenuDemarrer.this, GetPseudo.class);
                startActivity(pageGetPseudo);
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
    private void back(){
        this.finish();
    }
    private void openSettingsUser(){
        Intent pageSettingsUser = new Intent(MenuDemarrer.this, SettingsUser.class);
        startActivity(pageSettingsUser);
    }
    private void openSettingsLaw(){
        Intent pageSettingsLaw = new Intent(this, SettingsLaw.class);
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



   // public void menuToGet (View view){
     //   startActivity(new Intent(this, GetPseudoPage.class));
    //}
}
