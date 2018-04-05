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

public class Question_quatre extends Fragment{

    private String question;
    private ArrayList<String> reponses;

    public void setup(String question, ArrayList<String> reponses)
    {
        this.reponses = new ArrayList<>();
        this.question = question;
        this.reponses = reponses;
        Reponses.setqD(question);
        System.out.println(Reponses.getqD());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_quatre, container, false);

        if(Reponses.getRepD() != null)
        {
            if(Reponses.getRepD().equals(ECaracteristiques.INTELLIGENT))
                view = inflater.inflate(R.layout.reponse_a, container, false);
            else if(Reponses.getRepD().equals(ECaracteristiques.HEROIQUE))
                view = inflater.inflate(R.layout.reponse_b, container, false);
            else if(Reponses.getRepD().equals(ECaracteristiques.CHARISMATIQUE))
                view = inflater.inflate(R.layout.reponse_c, container, false);
            else if(Reponses.getRepD().equals(ECaracteristiques.PSYCHOPATHE))
                view = inflater.inflate(R.layout.reponse_d, container, false);

            TextView tvNbr = view.findViewById(R.id.tvNumber);
            tvNbr.setText("4/5");
        }


        TextView title = view.findViewById(R.id.lblQuestion);
        final Button repA = (Button) view.findViewById(R.id.repBtnA);
        final Button repB = (Button) view.findViewById(R.id.repBtnB);
        final Button repC = (Button) view.findViewById(R.id.repBtnC);
        final Button repD = (Button) view.findViewById(R.id.repBtnD);

        title.setText(this.question);
        repA.setText(this.reponses.get(0));
        repB.setText(this.reponses.get(1));
        repC.setText(this.reponses.get(2));
        repD.setText(this.reponses.get(3));


        repA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Reponses.setRepD(ECaracteristiques.INTELLIGENT);
                Reponses.setRepDValue(repA.getText().toString());

                if(Reponses.isOk())
                    showMsg(view);

                repA.setBackgroundColor(getResources().getColor(R.color.colorRed));
                repB.setBackgroundResource(android.R.color.darker_gray);
                repC.setBackgroundResource(android.R.color.darker_gray);
                repD.setBackgroundResource(android.R.color.darker_gray);
            }
        });

        repB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Reponses.setRepD(ECaracteristiques.HEROIQUE);
                Reponses.setRepDValue(repB.getText().toString());

                if(Reponses.isOk())
                    showMsg(view);

                repB.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                repA.setBackgroundResource(android.R.color.darker_gray);
                repC.setBackgroundResource(android.R.color.darker_gray);
                repD.setBackgroundResource(android.R.color.darker_gray);

            }
        });

        repC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Reponses.setRepD(ECaracteristiques.CHARISMATIQUE);
                Reponses.setRepDValue(repC.getText().toString());

                if(Reponses.isOk())
                    showMsg(view);

                repC.setBackgroundColor(getResources().getColor(R.color.colorLBlue));
                repA.setBackgroundResource(android.R.color.darker_gray);
                repB.setBackgroundResource(android.R.color.darker_gray);
                repD.setBackgroundResource(android.R.color.darker_gray);

            }
        });

        repD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Reponses.setRepD(ECaracteristiques.PSYCHOPATHE);
                Reponses.setRepDValue(repD.getText().toString());

                if(Reponses.isOk())
                    showMsg(view);

                repD.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                repA.setBackgroundResource(android.R.color.darker_gray);
                repB.setBackgroundResource(android.R.color.darker_gray);
                repC.setBackgroundResource(android.R.color.darker_gray);

            }
        });

        return view;
    }

    private void showMsg(View view)
    {
        Toast msgToast = Toast.makeText(view.getContext(), "Vous pouvez découvrir votre héros partenaire", Toast.LENGTH_SHORT);
        msgToast.show();
    }
}
