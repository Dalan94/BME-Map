package bme.hci.bme_map;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.IndoorLevel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    public final static int SEARCH_ACTIVITY = 0;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Zoom to the right place
        LatLng place = new LatLng(48.895588, 2.387898);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        mMap.setIndoorEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Intent intent = new Intent(MapsActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.exit) {
            finish();
            return true;
        }

        if (id == R.id.search) {
            Intent intent = new Intent(MapsActivity.this, SearchActivity.class);
            startActivityForResult(intent, SEARCH_ACTIVITY);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        Toast.makeText(MapsActivity.this,R.string.map_back_pressed, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEARCH_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                Room r = data.getParcelableExtra("ROOM");
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(r.getPlace()).title(r.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(r.getPlace()));

                IndoorBuilding b = mMap.getFocusedBuilding();
                List<IndoorLevel> il = b.getLevels();
                il.get(-r.getFloor()+3).activate();
            }
        }
    }
}
