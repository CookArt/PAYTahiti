package com.cookart.paytahiti;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<Parking> parkings;

    public RecyclerViewAdapter(ArrayList<Parking> parkings){
        this.parkings = parkings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parkings, parent, false);
        ViewHolder mViewHolder = new ViewHolder(itemView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Parking parking = parkings.get(position);
        holder.name.setText(parking.getNom());
        holder.nb_places.setText( "Nombre de places restantes:" + Long.toString(parking.getNb_places_restantes() ) );
    }

    @Override
    public int getItemCount() {
        return parkings.size();
    }


}
