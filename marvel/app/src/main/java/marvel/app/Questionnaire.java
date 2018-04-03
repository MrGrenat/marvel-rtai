package marvel.app;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import marvel.Reponses;

public class Questionnaire extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    //Liste des questions avec les réponses possibles
    private HashMap<String, ArrayList<String>> questions = new HashMap();

    //Liste des 5 questions posées au joueur
    private HashMap<String, ArrayList<String>> questionnaire = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        Button btnValider = (Button) findViewById(R.id.btnValider);
        btnValider.setEnabled(false);

        //Reset les réponses
        Reponses.reset();

        //Création de toutes les questions
        setupQuestions();

        //Création du questionnaire de la partie
        Random rand = new Random();
        List keys = new ArrayList(questions.keySet());

        while(questionnaire.size() != 5)
        {
            int i = rand.nextInt(keys.size());
            String q = keys.get(i).toString();
            questionnaire.put(q, questions.get(q));
            keys.remove(i);
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questionnaire, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        //Liste des 5 questions posées au joueur
        private List keys;

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
            this.keys = new ArrayList(questionnaire.keySet());
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    String qS1 = keys.get(0).toString();
                    Question_un q1 = new Question_un();
                    q1.setup(qS1, questionnaire.get(qS1));
                    return  q1;
                case 1:
                    String qS2 = keys.get(1).toString();
                    Question_deux q2 = new Question_deux();
                    q2.setup(qS2, questionnaire.get(qS2));
                    return  q2;
                case 2:
                    String qS3 = keys.get(2).toString();
                    Question_trois q3 = new Question_trois();
                    q3.setup(qS3, questionnaire.get(qS3));
                    return  q3;
                case 3:
                    String qS4 = keys.get(3).toString();
                    Question_quatre q4 = new Question_quatre();
                    q4.setup(qS4, questionnaire.get(qS4));
                    return  q4;
                case 4:
                    String qS5 = keys.get(4).toString();
                    Question_cinq q5 = new Question_cinq();
                    q5.setup(qS5, questionnaire.get(qS5));
                    return  q5;
                default: return null;
            }
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }
    }

    private void setupQuestions()
    {
        ArrayList<String> reps = new ArrayList();

        //Question 1
        String q = "Vous voyez une personne faire une crise cardiaque en pleine rue, que faites vous ?";
        String rep1 = "J'appelle les secours";
        String rep2 = "Je lui vient en aide";
        String rep3 = "Je dis au gens de s’écarter";
        String rep4 = "Premier réflexe : j'ai tweeté";

        reps.add(rep1);
        reps.add(rep2);
        reps.add(rep3);
        reps.add(rep4);

        questions.put(q, reps);

        //Question 2
        q = "Vous voyez une maison en feu, que faites vous ?";
        rep1 = "J'appelle les pompiers";
        rep2 = "Je fonce aider les gens à l'intérieur";
        rep3 = "Je dis au gens autour de m’aider à les sauvers";
        rep4 = "Je fais une vidéo";

        reps = new ArrayList<>();
        reps.add(rep1);
        reps.add(rep2);
        reps.add(rep3);
        reps.add(rep4);

        questions.put(q, reps);

        //Question 3
        q = "Une vieille femme se fait voler son sac à main, votre réaction";
        rep1 = "J’appelle la police";
        rep2 = "J’essai de récupérer son sac";
        rep3 = "J’ordonne au voleur de s'arrêter";
        rep4 = "Je suis le voleur";

        reps = new ArrayList<>();
        reps.add(rep1);
        reps.add(rep2);
        reps.add(rep3);
        reps.add(rep4);

        questions.put(q, reps);

        //Question 4
        q = "Enfant, vous étiez";
        rep1 = "Travailleur";
        rep2 = "Le protecteur";
        rep3 = "Populaire";
        rep4 = "La brute";

        reps = new ArrayList<>();
        reps.add(rep1);
        reps.add(rep2);
        reps.add(rep3);
        reps.add(rep4);

        questions.put(q, reps);

        //Question 5
        q = "Votre meilleur ami(e) est";
        rep1 = "Une personne normal";
        rep2 = "La justice";
        rep3 = "Une star";
        rep4 = "La mort";

        reps = new ArrayList<>();
        reps.add(rep1);
        reps.add(rep2);
        reps.add(rep3);
        reps.add(rep4);

        questions.put(q, reps);

        //Question 6
        q = "Qui est un héros ?";
        rep1 = "Les pompiers / la police / le samu";
        rep2 = "Vous même";
        rep3 = "Les gens disent vous";
        rep4 = "Les chasseurs de prime";

        reps = new ArrayList<>();
        reps.add(rep1);
        reps.add(rep2);
        reps.add(rep3);
        reps.add(rep4);

        questions.put(q, reps);

        //Question 7
        q = "Vous voulez mourir";
        rep1 = "En paix";
        rep2 = "En sauvant quelqu’un";
        rep3 = "Entouré de pleins de gens";
        rep4 = "Dans un incendie";

        reps = new ArrayList<>();
        reps.add(rep1);
        reps.add(rep2);
        reps.add(rep3);
        reps.add(rep4);

        questions.put(q, reps);

        //Question 8
        q = "Votre costume de super héro se compose de";
        rep1 = "Des gadgets sophistiqués";
        rep2 = "Une armure solide";
        rep3 = "Pas besoin de costume";
        rep4 = "Un masque de film d’horreur";

        reps = new ArrayList<>();
        reps.add(rep1);
        reps.add(rep2);
        reps.add(rep3);
        reps.add(rep4);

        questions.put(q, reps);

        //Question 9
        q = "Ton plus gros défaut est";
        rep1 = "Maniaque";
        rep2 = "Je suis trop gentil";
        rep3 = "Je passe trop de temps dans la salle de bain";
        rep4 = "Narcissique";

        reps = new ArrayList<>();
        reps.add(rep1);
        reps.add(rep2);
        reps.add(rep3);
        reps.add(rep4);

        questions.put(q, reps);

        //Question 10
        q = "Un animal abandonné semble blessé, que faîtes vous";
        rep1 = "J’utilise une aiguille et un caillou pour le sauver ";
        rep2 = "Je le porte jusqu’au vétérinaire";
        rep3 = "Je créer une collecte de fond et recherche son propriétaire";
        rep4 = "Je vais pouvoir me faire un nouveau manteau en fourrure !";

        reps = new ArrayList<>();
        reps.add(rep1);
        reps.add(rep2);
        reps.add(rep3);
        reps.add(rep4);

        questions.put(q, reps);
    }
}
