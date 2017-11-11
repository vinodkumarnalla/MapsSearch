package com.example.vinu.mapssample;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SearchView.OnQueryTextListener {

    private GoogleMap mMap;
    private SearchView address_field;
    String address="Hyderabad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        address_field=(SearchView)findViewById(R.id.address_field);
        address_field.setOnQueryTextListener(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMark();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null ||address.size()==0) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        address=query;
        addMark();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    private void addMark(){
        LatLng sydney =getLocationFromAddress(this,address) ;
        if(sydney!=null) {
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in hyderabad"));
            mMap.setMinZoomPreference(15);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }else{
            Toast.makeText(MapsActivity.this,"Try another address",Toast.LENGTH_LONG).show();
        }
    }
}
