package pl.app.citiesonmap;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.app.citiesonmap.data.City;
import pl.app.citiesonmap.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final List<City> cities = new ArrayList<>();
    private final HashMap<String, Marker> markers = new HashMap<>();
    private final List<LatLng> pathToDraw = new ArrayList<>();
    private Polyline polyLine = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.spinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                cities));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                City city = (City) adapterView.getItemAtPosition(position);
                if (mMap != null) {
                    mMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(CameraPosition
                                    .fromLatLngZoom(new LatLng(city.getLat(), city.getLng()), 12f)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.addCity.setOnClickListener(view -> showAddCityFragment());
        binding.clearLines.setOnClickListener(view -> clearLines());
    }

    private void clearLines() {
        pathToDraw.clear();
        if (polyLine != null) {
            polyLine.remove();
            polyLine= null;
        }
    }

    private void showAddCityFragment() {
        AddCityFragment.newInstance().show(getSupportFragmentManager(), "tag_add_city");
    }

    private void updateSpinner(List<City> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        ((ArrayAdapter<City>) binding.spinner.getAdapter()).notifyDataSetChanged();
        updateMap(cities);
    }

    private void updateMap(List<City> cities) {
        if (mMap != null) {
            for (City city : cities) {
                Marker marker = markers.get(city.getName());
                if (marker == null) {
                    markers.put(city.getName(),
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(city.getLat(),
                                            city.getLng()))
                                    .title(city.getName())));
                } else {
                    marker.setPosition(new LatLng(city.getLat(),
                            city.getLng()));
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(marker -> {
            LatLng latLng = marker.getPosition();
            pathToDraw.add(latLng);
            if (polyLine == null) {
                polyLine = mMap.addPolyline(new PolylineOptions().add(latLng).color(Color.BLACK));
            } else {
                polyLine.setPoints(pathToDraw);
            }
            return false;
        });
        db.collection("cities").addSnapshotListener(this, (value, error) -> {
            updateSpinner(value.toObjects(City.class));
        });
    }
}