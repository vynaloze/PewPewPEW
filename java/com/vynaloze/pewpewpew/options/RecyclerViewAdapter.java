package com.vynaloze.pewpewpew.options;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vynaloze.pewpewpew.R;
import com.vynaloze.pewpewpew.logic.Protagonist;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ProtagonistViewHolder> {
    private final Context context;
    private final List<Protagonist> protagonists;
    private final ImageView iv;
    private final TextView tx;


    public RecyclerViewAdapter(Context context, List<Protagonist> protagonistList, ImageView iv, TextView tx) {
        this.context = context;
        this.protagonists = protagonistList;
        this.iv = iv;
        this.tx = tx;
    }

    @Override
    public ProtagonistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_adapter, parent, false);
        return new ProtagonistViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ProtagonistViewHolder protagonistViewHolder, final int position) {
        final int imageID = protagonists.get(position).getImageID();
        final String name = protagonists.get(position).getName();
        final String description = protagonists.get(position).getDescription();
        final int speed = Integer.parseInt(description.substring(7, 10));

        protagonistViewHolder.icon.setImageResource(imageID);
        protagonistViewHolder.name.setText(name);
        protagonistViewHolder.description.setText(description);

        protagonistViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(imageID);
                tx.setText(description);
                saveChoiceToPreferences(imageID, name, description, speed);
            }
        });
    }

    private void saveChoiceToPreferences(int imageID, String name, String desc, int speed) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putInt("SHIP_IMAGE_ID", imageID);
        editor.putString("SHIP_NAME", name);
        editor.putString("SHIP_DESC", desc);
        editor.putInt("SPEED", speed);
        editor.apply();
    }

    @Override
    public int getItemCount() {
        return protagonists.size();
    }


    public class ProtagonistViewHolder extends RecyclerView.ViewHolder {
        private final CardView cv;
        private final TextView name;
        private final TextView description;
        private final ImageView icon;

        public ProtagonistViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.textview1_cv);
            description = (TextView) itemView.findViewById(R.id.textview2_cv);
            icon = (ImageView) itemView.findViewById(R.id.imageview_cv);

        }
    }

}