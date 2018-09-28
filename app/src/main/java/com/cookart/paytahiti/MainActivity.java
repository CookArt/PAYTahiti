package com.cookart.paytahiti;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.cookart.paytahiti.Parking;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( isServicesOk()){
            init();
        }
    }

    private void init(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference parkingRef = database.getReference("parkings");

        parkingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Parking> parkings = new ArrayList<>();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Parking parking = new Parking();
                    parking.setNom( (String) messageSnapshot.child("nom").getValue() );
                    parking.setNb_places_restantes( (Long) messageSnapshot.child("nb_places_restantes").getValue() );
                    parking.setLatitude( (Double) messageSnapshot.child("latitude").getValue() );
                    parking.setLongitude( (Double) messageSnapshot.child("longitude").getValue() );

                    parkings.add(parking);
                }
                String[] noms = new String[]{};
                for( Parking parking: parkings ){
                    Arrays.fill( noms,parking.getNom() );
                }
                populateListView( parkings );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isServicesOk(){
        Log.d(TAG, "isServicesOk: checking Google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isServicesOk: Google PMlay services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOk: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You can't make maps request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void populateListView(final ArrayList<Parking> parkings){
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Parking parking = parkings.get(position);
                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        intent.putExtra("nom", parking.getNom());
                        intent.putExtra("latitude", parking.getLatitude());
                        intent.putExtra("longitude", parking.getLongitude());
                        intent.putExtra("nb_places", parking.getNb_places_restantes());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Log.d(TAG, parkings.get(position).getNom() + ":" + parkings.get(position).getLatitude() + "=>" + parkings.get(position).getLongitude());
                    }
                })
        );

        orderBy(parkings, "");

        mRecyclerViewAdapter = new RecyclerViewAdapter(parkings);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void orderBy(ArrayList<Parking> parkings, String order){
        switch (order){
            case "nb_asc":
                Collections.sort(parkings, new Comparator<Parking>() {
                    @Override
                    public int compare(Parking p1, Parking p2) {
                        return String.valueOf(p1.getNb_places_restantes()).compareTo( String.valueOf(p2.getNb_places_restantes()) );
                    }
                });
                break;
            case "nb_desc":
                Collections.sort(parkings, new Comparator<Parking>() {
                    @Override
                    public int compare(Parking p1, Parking p2) {
                        return String.valueOf(p2.getNb_places_restantes()).compareTo( String.valueOf(p1.getNb_places_restantes()) );
                    }
                });
                break;
            case "dst_asc":
                Collections.sort(parkings, new Comparator<Parking>() {
                    @Override
                    public int compare(Parking p1, Parking p2) {
                        return String.valueOf(p2.getNb_places_restantes()).compareTo( String.valueOf(p1.getNb_places_restantes()) );
                    }
                });
                break;
            default:
                Collections.sort(parkings, new Comparator<Parking>() {
                    @Override
                    public int compare(Parking p1, Parking p2) {
                        return p1.getNom().compareTo( p2.getNom() );
                    }
                });
                break;
        }

    }
}
