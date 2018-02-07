package prjmarvel.menudemarrer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                Intent pageGetPseudo = new Intent(MenuDemarrer.this, GetPseudoPage.class);
                startActivity(pageGetPseudo);
            }
        });
    }

   // public void menuToGet (View view){
     //   startActivity(new Intent(this, GetPseudoPage.class));
    //}
}
