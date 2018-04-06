package marvel.app;

/**
 * Created by adefings on 04/04/2018.
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import marvel.PartieStatic;


public class HerosAdapter extends RecyclerView.Adapter<HerosAdapter.MyViewHolder> {


    private List<MesHerosForm> herosFormList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageHeros;
        public TextView nom, date;
        public Button btn;

        public MyViewHolder(View view) {
            super(view);
            imageHeros = (ImageView) view.findViewById(R.id.imageHeroReturned);
            nom = (TextView) view.findViewById(R.id.nomHerosReturned);
            date = (TextView) view.findViewById(R.id.datePartieRetourned);
            btn = (Button) view.findViewById(R.id.boutonDetailHeros);
        }
    }


    public HerosAdapter(List<MesHerosForm> herosFormList) {
        this.herosFormList = herosFormList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.heros_form_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MesHerosForm herosform = herosFormList.get(position);

        Picasso.with(herosform.getCtxt()).load(herosform.getImagehero()).into(holder.imageHeros);
        holder.nom.setText(herosform.getNomhero());

        holder.date.setText(herosform.getDatepartie());
        holder.btn.setText("Détails");

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PartieStatic.setId(herosform.getPartieId());
                Intent pageFicheHeros = new Intent(herosform.getCtxt(), TonHerosSelectActivity.class);
                pageFicheHeros.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                herosform.getCtxt().startActivity(pageFicheHeros);
            }
        });

    }

    @Override
    public int getItemCount() {
        return herosFormList.size();
    }

}
