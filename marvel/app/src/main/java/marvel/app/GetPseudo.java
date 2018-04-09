package marvel.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import marvel.DataSource.PartiesDataSource;
import marvel.DataSource.UtilisateursDataSource;
import marvel.Reponses;
import marvel.Tables.Partie;
import marvel.PartieStatic;
import marvel.Tables.Utilisateurs;

@SuppressLint("Registered")
public class GetPseudo extends AppCompatActivity {
    private Toolbar toolbar;

    private ImageView photo;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText etPseudo;
    private String pseudo;
    private String path;

    private UtilisateursDataSource datasourceUtilisateur;
    private PartiesDataSource datasourceParties;

    private Partie partie;
    private PartieStatic partieStatic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Plein Ecran
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pseudo);

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
        title.setText("Etape 1");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                back();
            }
        });

        datasourceUtilisateur = new UtilisateursDataSource(getApplicationContext());
        datasourceUtilisateur.open();

        Button btnCamera = (Button)findViewById(R.id.bt_photo);
        Button btValider = (Button)findViewById(R.id.bt_valider);

        etPseudo = (EditText)findViewById(R.id.et_pseudo);

        if(datasourceUtilisateur.getAllUtilisateurs().isEmpty()){
            pseudo = etPseudo.getText().toString();
        }
        else{
            List<Utilisateurs> pseudoList = datasourceUtilisateur.getAllUtilisateurs();
            for (Utilisateurs utils:pseudoList) {
                System.out.println("-------------------------PSEUDO----------------------");

                pseudo = utils.getNom();

                System.out.println(pseudo);

            }


            etPseudo.setVisibility(View.INVISIBLE);
            ((TextView)findViewById(R.id.tv_pseudo2)).setVisibility(View.INVISIBLE);
            ((TextView)findViewById(R.id.tv_pseudo)).setText("\nBonjour "+pseudo);
        }

        photo = (ImageView)findViewById(R.id.iv_photo);

        //test récupération image
        loadImageFromStorage("data/data/marvel.app/app_imageDir/");

        //Appel de la fonction showPictureDialog au clic sur le bouton Camera
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        btValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                gererErreur();
            }
        });
    }

    //gere les erreurs
    public void gererErreur() {
        //si l'utilisateur ne rentre pas de pseudo
        if(etPseudo.getText().toString().trim().length() == 0 && datasourceUtilisateur.getAllUtilisateurs().isEmpty()) {
            etPseudo.setError("Veuillez entrer votre pseudo");
            return;
        }
        //si l'utilisateur n'entre pas de photo
        else if(null == photo.getDrawable()) {
            Toast errorToast = Toast.makeText(getApplicationContext(), "Veuillez sélectionner une photo", Toast.LENGTH_SHORT);
            errorToast.show();
        }
        //sinon ouvre le questionnaire
        else
        {
            pseudo = etPseudo.getText().toString();
            if(datasourceUtilisateur.getAllUtilisateurs().isEmpty()){
                datasourceUtilisateur.insertUtilisateurs(pseudo);
            }

            System.out.println("Création du nouvelle partie..");

            partie = new Partie();
            partie.setLienPhoto(path);
            partie.setTermine(0);


            PartieStatic.setLienPhoto(path);
            PartieStatic.setTermine(0);

            datasourceParties = new PartiesDataSource(getApplicationContext());
            datasourceParties.open();

            long idPartie = datasourceParties.insertPartie(partie);
            PartieStatic.setId(idPartie);

            //Reset les réponses
            Reponses.reset();

            Intent pageQuestionnaire = new Intent(GetPseudo.this, Questionnaire.class);
            startActivity(pageQuestionnaire);
            finish();
        }
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
    }

    //Fonction permettant de prendre une photo
    private void takePhotoFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,Calendar.getInstance()
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

    private void loadImageFromStorage(String path)
    {
        try {
            File f=new File(path, "photopartie.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            System.out.println("Bitmap : "+b);
            photo.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
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
                    System.out.println("Path retourned: "+path);

                    Toast.makeText(GetPseudo.this, "Image enregistrée !", Toast.LENGTH_SHORT).show();
                    photo.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(GetPseudo.this, "Une erreur s'est produite, veuillez recommencer!", Toast.LENGTH_SHORT).show();
                }

            }

        } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                System.out.println("Nouvelle photo image");
                Bundle extras = data.getExtras();

                //on récupère l'image dans la variable imageBitmap
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                path = saveToInternalStorage(imageBitmap);
                System.out.println("Path retourneeed: "+path);

                //on change l'image sur la page
                photo.setImageBitmap(imageBitmap);

                System.out.println("PATH DE LA GALERIE: "+path);
            }
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
        finish();
    }

    private void openSettingsLaw(){
        Intent pageSettingsLaw = new Intent(this, SettingsLaw.class);
        startActivity(pageSettingsLaw);
        finish();
    }

    private void openMesHeros(){
        Intent pageSettingsUser = new Intent(this, MesHeros.class);
        startActivity(pageSettingsUser);
        finish();
    }

    private void openHome(){
        Intent pageHome = new Intent(this, MenuDemarrer.class);
        pageHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pageHome);
        finish();
    }

    private void back(){
        this.finish();
    }

}
