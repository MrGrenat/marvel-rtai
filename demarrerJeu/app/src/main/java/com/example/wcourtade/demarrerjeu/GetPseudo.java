package com.example.wcourtade.demarrerjeu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

@SuppressLint("Registered")
public class GetPseudo extends AppCompatActivity {

    ImageView photo;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pseudo);


        Button btnCamera = (Button)findViewById(R.id.bt_photo);
        photo = (ImageView)findViewById(R.id.iv_photo);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ouverture de la capture de photo
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //Si la photo a bien été capturée
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            //on récupère l'image dans la variable imageBitmap
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //on change l'image sur la page
            photo.setImageBitmap(imageBitmap);
        }
    }
}
