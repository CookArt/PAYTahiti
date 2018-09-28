package com.cookart.paytahiti;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView name, nb_places;

    public ViewHolder(View view){
        super(view);
        name = (TextView) view.findViewById(R.id.name);
        nb_places = (TextView) view.findViewById(R.id.nb_places);
    }
}
