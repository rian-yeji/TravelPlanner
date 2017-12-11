package com.example.travelplanner.detailActivity.Map;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.travelplanner.R;
import com.example.travelplanner.addTravelActivity.Travel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class AddMapActivity extends AppCompatActivity implements OnMapReadyCallback{

    protected static final String TAG = "AddMapActivity";
    private PolylineOptions polylineOptions;
    private ArrayList<LatLng> arrayPoints;
    private GoogleMap mMap;
    String input;
    Address bestResult;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int RC_LOCATION = 1;
    protected Location mLastLocation;
    private TextView textView;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Travels");
    private Travel travel;
    private int dayposition;
    private int planposition;

    private Map_item map_item = new Map_item();
    private Marker m;

    private LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_map);

        Intent intent = getIntent();
        travel = (Travel) intent.getSerializableExtra("TravelDetail");
        dayposition = intent.getIntExtra("dayposition",0);
        planposition = intent.getIntExtra("planposition",0);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        final Button searchBtn = (Button)findViewById(R.id.searchBtn);
        final EditText editText = (EditText)findViewById(R.id.editText);
        textView = (TextView)findViewById(R.id.location_text);

        getLastLocation();


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = editText.getText().toString();
                map_item.setMarker(input);
                searchAddress(getApplicationContext(),input);
            }
        });
    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        arrayPoints = new ArrayList<LatLng>();

        LatLng place = new LatLng(38, 127);
       // settingMap();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
    }

    public void settingMap(){ //db 불러오기
        final Query plans = myRef.orderByPriority();

        plans.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*DB로딩*/
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String plan = snapshot.getValue(String.class);
                    Log.i("hahaneul", plan);
                    //  mMap.addMarker(new MarkerOptions().position(-34, 151).title(plan));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private int i=0;

    protected void updateMap() {
        textView.setText("위도 : " + bestResult.getLatitude() + "경도 : " + bestResult.getLongitude());
        location = new LatLng(bestResult.getLatitude(), bestResult.getLongitude());

        if(m != null) {
            m.remove();
        }
        ////////////////////////////////////////////////////
        m = mMap.addMarker(new MarkerOptions().position(location).title(input));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));



        myRef.child(travel.getTitle()).child("Plan").child("Day"+i)/*setValue(input)*/;
        i++;

        map_item.setLatitude(bestResult.getLatitude());
        map_item.setLongitude(bestResult.getLongitude());
        addMap(map_item);
    }

    private void addMap(Map_item map_item) {

        DatabaseReference titleRef = database.getReference(travel.getTitle());
        //날짜 추가 수정 필요
        myRef.child(titleRef.getKey()).child("Plan").child("Day"+dayposition).child("plan"+planposition).child("Map").setValue(map_item);


    }

    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION) // automatically re-invoked after getting the permission
    public void getLastLocation() {
        String[] perms = {android.Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already Have permissions.
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(this, new OnCompleteListener<Location>() { ////////////
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && (task.getResult() != null)) {
                                mLastLocation = task.getResult();
                                try {
                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREA);
                                    List<Address> addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                                    if (addresses.size() > 0) {
                                        bestResult = (Address) addresses.get(0);
                                        //Log.i("hhhhhh",bestResult.getLongitude()+","+bestResult.getLatitude());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                updateMap();
                            }else {
                                Log.w(TAG, "getLastLocation:exception", task.getException());
                            }
                        }
                    });
        }
        else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this,
                    "This app needs access to your location to know where you are.",
                    RC_LOCATION, perms);
        }
    }

    public void searchAddress(Context mcontext, String input) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocationName(input,1); //input 입력받은 주소
            if (addresses.size() >0) {
                bestResult = (Address) addresses.get(0);
                //LatLng serarch = new LatLng(bestResult.getLatitude(), bestResult.getLongitude());
            }
        } catch (IOException e) {
            Log.e(getClass().toString(),"Failed in using Geocoder.", e);
            return;
        }
        updateMap();
    }
}
