package marvel.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import marvel.JSON.enums.ECaracteristiques;
import marvel.Reponses;

/**
 * Created by wcourtade on 22/03/2018.
 */

public class Question_deux extends Fragment{

    private String question;
    private ArrayList<String> reponses;

    public void setup(String question, ArrayList<String> reponses)
    {
        this.reponses = new ArrayList<>();
        this.question = question;
        this.reponses = reponses;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_deux, container, false);

        TextView title = rootView.findViewById(R.id.lblQuestion);
        Button repA = (Button) rootView.findViewById(R.id.repBtnA);
        Button repB = (Button) rootView.findViewById(R.id.repBtnB);
        Button repC = (Button) rootView.findViewById(R.id.repBtnC);
        Button repD = (Button) rootView.findViewById(R.id.repBtnD);

        title.setText(this.question);
        repA.setText(this.reponses.get(0));
        repB.setText(this.reponses.get(1));
        repC.setText(this.reponses.get(2));
        repD.setText(this.reponses.get(3));

        View viewMain = inflater.inflate(R.layout.activity_questionnaire, container, false);

        final Button btnValider = viewMain.findViewById(R.id.btnValider);

        repA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Reponses.setRepB(ECaracteristiques.INTELLIGENT);
                Toast msgToast = Toast.makeText(view.getContext(), "Réponse enregistrée", Toast.LENGTH_SHORT);
                msgToast.show();

                if(Reponses.isOk())
                    btnValider.setEnabled(true);
            }
        });

        repB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Reponses.setRepB(ECaracteristiques.HEROIQUE);
                Toast msgToast = Toast.makeText(view.getContext(), "Réponse enregistrée", Toast.LENGTH_SHORT);
                msgToast.show();

                if(Reponses.isOk())
                    btnValider.setEnabled(true);
            }
        });

        repC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Reponses.setRepB(ECaracteristiques.CHARISMATIQUE);
                Toast msgToast = Toast.makeText(view.getContext(), "Réponse enregistrée", Toast.LENGTH_SHORT);
                msgToast.show();

                if(Reponses.isOk())
                    btnValider.setEnabled(true);
            }
        });

        repD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Reponses.setRepB(ECaracteristiques.PSYCHOPATHE);
                Toast msgToast = Toast.makeText(view.getContext(), "Réponse enregistrée", Toast.LENGTH_SHORT);
                msgToast.show();

                if(Reponses.isOk())
                    btnValider.setEnabled(true);
            }
        });

        return rootView;
    }
}
