package marvel.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.ECField;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import marvel.DataSource.AssociationsDataSource;
import marvel.DataSource.HerosDataSource;
import marvel.DataSource.PartiesDataSource;
import marvel.JSON.enums.ECaracteristiques;
import marvel.Reponses;
import marvel.Tables.Association;
import marvel.Tables.Heros;
import marvel.Tables.PartieStatic;

public class TonHerosActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private HerosDataSource dtHeros;
    private AssociationsDataSource datasourceAssoc;
    private PartiesDataSource datasourceParties;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String path;
    private ImageView imgUser;


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

        Button accueil = (Button) findViewById(R.id.buttonAccueil);
        Button ficheHeros = (Button) findViewById(R.id.buttonFicheHeros);

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

        HashMap<ECaracteristiques, Integer> arrayMax = getCaracCorrespondante();
        String carac = null;
        int valeurCarac = 0;

        System.out.println("TAILLE: " + arrayMax.size());

        if (arrayMax.size() > 1) {
            Random rand = new Random();
            List keys = new ArrayList(arrayMax.keySet());
            int i = rand.nextInt(keys.size());
            carac = keys.get(i).toString();
            valeurCarac = arrayMax.get(keys.get(i));
        }
        else
        {
            List keys = new ArrayList(arrayMax.keySet());
            carac = keys.get(0).toString();
            valeurCarac = arrayMax.get(keys.get(0));
        }

        dtHeros = new HerosDataSource(getApplicationContext());
        dtHeros.open();

        List<Heros> listHeros = dtHeros.getAllHerosCarac(carac.toLowerCase(), valeurCarac);

        if(listHeros.isEmpty()){
            Toast msgError = Toast.makeText(getApplicationContext(), "Vous n'avez pas les compétences nécessaires pour devenir un Super Héros !", Toast.LENGTH_LONG);
            msgError.show();
            Reponses.reset();
            Intent pageFicheHeros = new Intent(TonHerosActivity.this, Questionnaire.class);
            startActivity(pageFicheHeros);
            finish();
        }
        else
        {

            datasourceParties = new PartiesDataSource(getApplicationContext());
            datasourceParties.open();
            datasourceParties.setTermine(PartieStatic.getId());
            String date = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
            datasourceParties.updateDate(PartieStatic.getId(), date);
            Random rand = new Random();
            int i = rand.nextInt(listHeros.size());
            Heros leHeros = listHeros.get(i);


            datasourceAssoc = new AssociationsDataSource(getApplicationContext());
            datasourceAssoc.open();

            Association assoc = new Association();
            assoc.setIdHeros(leHeros.getId());
            assoc.setIdEtranger(PartieStatic.getId());

            datasourceAssoc.insertAssociation(assoc);

            System.out.println(leHeros.getNom());

            TextView nomHeros =  findViewById(R.id.textViewNomHeros);
            nomHeros.setText(leHeros.getNom());

            ImageView imgHeros = (ImageView) findViewById(R.id.imageViewHeros);
            Picasso.with(this).load(leHeros.getUrlImage()).into(imgHeros);

            String name = PartieStatic.getLienPhoto();

            File imgFile = new  File(name);

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                imgUser = (ImageView) findViewById(R.id.imageViewUser);

                imgUser.setImageBitmap(myBitmap);

            }

            TextView descHeros = findViewById(R.id.textViewDescHeros);

            if(leHeros.getDesc().length() > 5)
                descHeros.setText(leHeros.getDesc());
            else
                descHeros.setText("Aucune description disponible.");




            Button btnCamera = (Button) findViewById(R.id.buttonReprendrePhoto);

            //Appel de la fonction showPictureDialog au clic sur le bouton Camera
            btnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPictureDialog();
                }
            });

        }
    }

    //Appel du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //Demande à l'utilisateur s'il veut prendre une photo ou en sélectionnez une dans sa galerie
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Sélectionner depuis la gallerie",
                "Prendre une photo depuis la caméra" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    //Fonction permettant de sélectionnez une photo depuis la galerie
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, 2);
        cleanBackground();
    }

    //Fonction permettant de prendre une photo
    private void takePhotoFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            cleanBackground();
        }
    }

    public void cleanBackground(){
        ImageView photo = findViewById(R.id.imageViewUser);
        photo.setBackground(null);
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, Calendar.getInstance()
                .getTimeInMillis()+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            return mypath.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    //Gestion des erreurs / résultat de la selection de la photo
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == 2) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    System.out.println("Image depuis la galerie");
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    path = saveToInternalStorage(bitmap);
                    System.out.println("Path retourned: " + path);

                    Toast.makeText(TonHerosActivity.this, "Image enregistrée !", Toast.LENGTH_SHORT).show();
                    imgUser.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(TonHerosActivity.this, "Une erreur s'est produite, veuillez recommencer!", Toast.LENGTH_SHORT).show();
                }

            }

        } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                System.out.println("Nouvelle photo image");
                Bundle extras = data.getExtras();

                //on récupère l'image dans la variable imageBitmap
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                path = saveToInternalStorage(imageBitmap);
                System.out.println("Path retourneeed: " + path);

                //on change l'image sur la page
                imgUser.setImageBitmap(imageBitmap);

                System.out.println("PATH DE LA GALERIE: "+path);
            }
        }

        datasourceParties.updatePhoto(PartieStatic.getId(), path);

    }

    public HashMap<ECaracteristiques, Integer> getCaracCorrespondante(){
        HashMap<ECaracteristiques, Integer> arrayValeur = new HashMap<>();

        String repA = Reponses.getRepA().name();
        String repB = Reponses.getRepB().name();
        String repC = Reponses.getRepC().name();
        String repD = Reponses.getRepD().name();
        String repE = Reponses.getRepE().name();


        switch (repA){
            case "INTELLIGENT" : arrayValeur.put(ECaracteristiques.INTELLIGENT, 1);
            break;

            case "HEROIQUE" : arrayValeur.put(ECaracteristiques.HEROIQUE, 1);
            break;

            case "CHARISMATIQUE" : arrayValeur.put(ECaracteristiques.CHARISMATIQUE, 1);
            break;

            case "PSYCHOPATHE" : arrayValeur.put(ECaracteristiques.PSYCHOPATHE, 1);
            break;
        }

        switch (repB){
            case "INTELLIGENT" : if(arrayValeur.containsKey(ECaracteristiques.INTELLIGENT)){
                                    arrayValeur.put(ECaracteristiques.INTELLIGENT, arrayValeur.get(ECaracteristiques.INTELLIGENT) + 1  );
                                }
                                else
                                    arrayValeur.put(ECaracteristiques.INTELLIGENT, 1);
                break;

            case "HEROIQUE" : if(arrayValeur.containsKey(ECaracteristiques.HEROIQUE)){
                                arrayValeur.put(ECaracteristiques.HEROIQUE, arrayValeur.get(ECaracteristiques.HEROIQUE) + 1  );
                            }
                                else
                                    arrayValeur.put(ECaracteristiques.HEROIQUE, 1);
                break;

            case "CHARISMATIQUE" : if(arrayValeur.containsKey(ECaracteristiques.CHARISMATIQUE)){
                                        arrayValeur.put(ECaracteristiques.CHARISMATIQUE, arrayValeur.get(ECaracteristiques.CHARISMATIQUE) + 1  );
                                    }
                                    else
                                        arrayValeur.put(ECaracteristiques.CHARISMATIQUE, 1);
                break;

            case "PSYCHOPATHE" : if(arrayValeur.containsKey(ECaracteristiques.PSYCHOPATHE)){
                                    arrayValeur.put(ECaracteristiques.PSYCHOPATHE, arrayValeur.get(ECaracteristiques.PSYCHOPATHE) + 1  );
                                }
                                else
                                    arrayValeur.put(ECaracteristiques.PSYCHOPATHE, 1);
                break;
        }

        switch (repC){
            case "INTELLIGENT" : if(arrayValeur.containsKey(ECaracteristiques.INTELLIGENT)){
                                    arrayValeur.put(ECaracteristiques.INTELLIGENT, arrayValeur.get(ECaracteristiques.INTELLIGENT) + 1  );
                                }
                                else
                                    arrayValeur.put(ECaracteristiques.INTELLIGENT, 1);
                break;

            case "HEROIQUE" : if(arrayValeur.containsKey(ECaracteristiques.HEROIQUE)){
                                arrayValeur.put(ECaracteristiques.HEROIQUE, arrayValeur.get(ECaracteristiques.HEROIQUE) + 1  );
                            }
                            else
                                arrayValeur.put(ECaracteristiques.HEROIQUE, 1);
                break;

            case "CHARISMATIQUE" : if(arrayValeur.containsKey(ECaracteristiques.CHARISMATIQUE)){
                                    arrayValeur.put(ECaracteristiques.CHARISMATIQUE, arrayValeur.get(ECaracteristiques.CHARISMATIQUE) + 1  );
                                    }
                                    else
                                        arrayValeur.put(ECaracteristiques.CHARISMATIQUE, 1);
                break;

            case "PSYCHOPATHE" : if(arrayValeur.containsKey(ECaracteristiques.PSYCHOPATHE)){
                                arrayValeur.put(ECaracteristiques.PSYCHOPATHE, arrayValeur.get(ECaracteristiques.PSYCHOPATHE) + 1  );
                                }
                                else
                                    arrayValeur.put(ECaracteristiques.PSYCHOPATHE, 1);
                break;
        }

        switch (repD){
            case "INTELLIGENT" : if(arrayValeur.containsKey(ECaracteristiques.INTELLIGENT)){
                                    arrayValeur.put(ECaracteristiques.INTELLIGENT, arrayValeur.get(ECaracteristiques.INTELLIGENT) + 1  );
                                }
                                else
                                    arrayValeur.put(ECaracteristiques.INTELLIGENT, 1);
                break;

            case "HEROIQUE" : if(arrayValeur.containsKey(ECaracteristiques.HEROIQUE)){
                                arrayValeur.put(ECaracteristiques.HEROIQUE, arrayValeur.get(ECaracteristiques.HEROIQUE) + 1  );
                            }
                            else
                                arrayValeur.put(ECaracteristiques.HEROIQUE, 1);
                break;

            case "CHARISMATIQUE" : if(arrayValeur.containsKey(ECaracteristiques.CHARISMATIQUE)){
                                    arrayValeur.put(ECaracteristiques.CHARISMATIQUE, arrayValeur.get(ECaracteristiques.CHARISMATIQUE) + 1  );
                                }
                                else
                                    arrayValeur.put(ECaracteristiques.CHARISMATIQUE, 1);
                break;

            case "PSYCHOPATHE" : if(arrayValeur.containsKey(ECaracteristiques.PSYCHOPATHE)){
                                    arrayValeur.put(ECaracteristiques.PSYCHOPATHE, arrayValeur.get(ECaracteristiques.PSYCHOPATHE) + 1  );
                                }
                                else
                                    arrayValeur.put(ECaracteristiques.PSYCHOPATHE, 1);
                break;
        }

        switch (repE){
            case "INTELLIGENT" : if(arrayValeur.containsKey(ECaracteristiques.INTELLIGENT)){
                arrayValeur.put(ECaracteristiques.INTELLIGENT, arrayValeur.get(ECaracteristiques.INTELLIGENT) + 1  );
            }
            else
                arrayValeur.put(ECaracteristiques.INTELLIGENT, 1);
                break;

            case "HEROIQUE" : if(arrayValeur.containsKey(ECaracteristiques.HEROIQUE)){
                arrayValeur.put(ECaracteristiques.HEROIQUE, arrayValeur.get(ECaracteristiques.HEROIQUE) + 1  );
            }
            else
                arrayValeur.put(ECaracteristiques.HEROIQUE, 1);
                break;

            case "CHARISMATIQUE" : if(arrayValeur.containsKey(ECaracteristiques.CHARISMATIQUE)){
                arrayValeur.put(ECaracteristiques.CHARISMATIQUE, arrayValeur.get(ECaracteristiques.CHARISMATIQUE) + 1  );
            }
            else
                arrayValeur.put(ECaracteristiques.CHARISMATIQUE, 1);
                break;

            case "PSYCHOPATHE" : if(arrayValeur.containsKey(ECaracteristiques.PSYCHOPATHE)){
                arrayValeur.put(ECaracteristiques.PSYCHOPATHE, arrayValeur.get(ECaracteristiques.PSYCHOPATHE) + 1  );
            }
            else
                arrayValeur.put(ECaracteristiques.PSYCHOPATHE, 1);
                break;
        }

        HashMap<ECaracteristiques, Integer> arrayFinal = new HashMap<>();


        Map.Entry<ECaracteristiques, Integer> maxEntry = null;

        for (Map.Entry<ECaracteristiques, Integer> entry : arrayValeur.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
                arrayFinal = new HashMap<>();
                arrayFinal.put(entry.getKey(), entry.getValue());

            }
            else
            {
                if (maxEntry != null && entry.getValue().compareTo(maxEntry.getValue()) == 0)
                {
                    arrayFinal.put(entry.getKey(), entry.getValue());
                }
            }
        }

        return arrayFinal;
    }


}
