package com.example.travelplanner.detailActivity.Map;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travelplanner.R;
import com.example.travelplanner.addTravelActivity.Travel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayMapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView = null;
    private int dayposition;

    private PolylineOptions polylineOptions;
    private ArrayList<LatLng> arrayPoints =new ArrayList<LatLng>();

    Map_item map_item = new Map_item();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    String DBKey;
    Travel travel;

    private ArrayList<Map_item> map_items = new ArrayList<Map_item>();

    public DayMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_map, container, false);

        Bundle bundle = getArguments();
        dayposition = bundle.getInt("dayposition");
        travel = (Travel)(bundle.getSerializable("travel"));
        Log.i("dayposition","//"+dayposition);

        mapView = (MapView)view.findViewById(R.id.dayMap);
        mapView.getMapAsync(this);

        SharedPreferences preferences = getActivity().getSharedPreferences("prefDB",MODE_PRIVATE);
        DBKey = preferences.getString("DBKey",""); //key,defaultValue
        myRef = database.getReference(DBKey);

        mapSetting(dayposition);


        return view;
    }

    public void mapSetting(int dayposition){
        DatabaseReference reference = myRef.child(travel.getTitle()).child("Plan").child("Day"+dayposition).getRef();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //plan0,1,2
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.i("DayMap","key="+snapshot.getKey());
                    //Map_item map_item = snapshot.child("Map").getValue(Map_item.class);
                    if(snapshot.child("Map").getValue()!=null) {
                        Map_item map_item = new Map_item();
                        map_item.setLatitude(snapshot.child("Map").child("latitude").getValue(Double.class));
                        map_item.setLongitude(snapshot.child("Map").child("longitude").getValue(Double.class));
                        map_item.setMarker(snapshot.child("Map").child("marker").getValue(String.class));

                        map_items.add(map_item);
                    }
                }
                Log.i("DayMap",""+map_items.get(0).marker);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//액티비티가 처음 생성될 때 실행되는 함수

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       // LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions();

       for(Map_item m: map_items) {
          // String input = m.getMarker();
           LatLng location = new LatLng(m.getLatitude(), m.getLongitude());
           markerOptions.position(location);
           markerOptions.title(m.getMarker());
           googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
           googleMap.addMarker(markerOptions);
           googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
           polylineOptions = new PolylineOptions();

           arrayPoints.add(location);

           polylineOptions.color(Color.RED);
           polylineOptions.width(5);
           arrayPoints.add(location);
           polylineOptions.addAll(arrayPoints);
           googleMap.addPolyline(polylineOptions);
       }

        //////빨간줄 그어주는 코드 --> setting datasnapshot뒤에
        /*polylineOptions = new PolylineOptions();

        arrayPoints.add(location);

        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        arrayPoints.add(location);
        polylineOptions.addAll(arrayPoints);
        mMap.addPolyline(polylineOptions);*/


       //
       /* m = mMap.addMarker(new MarkerOptions().position(location).title(input));*/
    }
}
