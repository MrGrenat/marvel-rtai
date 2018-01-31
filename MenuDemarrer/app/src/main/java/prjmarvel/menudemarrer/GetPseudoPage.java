package prjmarvel.menudemarrer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by adefings on 31/01/2018.
 */

public class GetPseudoPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pseudo);
        Intent i = getIntent();
        setContentView(R.layout.activity_get_pseudo);

    }
}
