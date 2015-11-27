package bme.hci.bme_map;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.IndoorLevel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MainActivity extends AppCompatActivity
        implements  OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener{

    private GoogleMap mMap;
    private Room locatedRoom = null;
    public final static int SEARCH_ACTIVITY = 0;
    public final static int SCHEDULE_ACTIVITY = 1;
    public final static int SEARCH_ACTIVITY_ITINERARY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, SEARCH_ACTIVITY);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

        mMap.setOnCameraChangeListener(onCameraChange);
        mMap.setOnIndoorStateChangeListener(changeListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        if (id == R.id.search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, SEARCH_ACTIVITY);
        }

        if (id == R.id.itinerary) {
            if (locatedRoom != null){
                Intent intent = new Intent(MainActivity.this, Itinerary.class);
                intent.putExtra("ROOM",locatedRoom);
                startActivity(intent);
            } else{
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, SEARCH_ACTIVITY_ITINERARY);
                //Toast.makeText(MainActivity.this, "You need to have search a map before create the itinerary.", Toast.LENGTH_LONG).show();
            }
        }

        if (id == R.id.schedule) {
            Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
            startActivityForResult(intent,SCHEDULE_ACTIVITY);
        }

        if (id == R.id.import_schedule){
            FileDialog fd = new FileDialog(this);
            fd.setListener(new FileDialog.ActionListener(){
                public void userAction(int action, String filePath)
                {
                    if (action == FileDialog.ACTION_SELECTED_FILE)
                        Toast.makeText(MainActivity.this, "The schedule was well imported.", Toast.LENGTH_LONG).show();
                }});;
            fd.selectFileStrict();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private GoogleMap.OnCameraChangeListener onCameraChange = new GoogleMap.OnCameraChangeListener(){
        @Override
        public void onCameraChange(CameraPosition cameraPosition) {
            LatLng new_pos = cameraPosition.target;
            float new_zoom = cameraPosition.zoom;
            if (new_zoom < 17.2){
                mMap.moveCamera(CameraUpdateFactory.zoomTo((float)17.2));
            }
            double latitude = new_pos.latitude;
            double longitude = new_pos.longitude;
            // Top
            if (new_pos.latitude > 48.8965)
                latitude = 48.8965;
                // Bottom
            else if (new_pos.latitude < 48.8952)
                latitude = 48.8952;
            if (new_pos.longitude > 2.3895)
                longitude = 2.3895;
            else if (new_pos.longitude < 2.3865)
                longitude = 2.3865;

            if ((latitude != new_pos.latitude) || (longitude != new_pos.longitude)){
                LatLng pos = new LatLng(latitude,longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
            }
        }
    };

    public GoogleMap.OnIndoorStateChangeListener changeListener = new GoogleMap.OnIndoorStateChangeListener(){

        @Override
        public void onIndoorBuildingFocused() {

        }

        @Override
        public void onIndoorLevelActivated(IndoorBuilding indoorBuilding) {
            if (locatedRoom != null) {
                mMap.clear();
                if ((-indoorBuilding.getActiveLevelIndex() + 3) == locatedRoom.getFloor())
                    mMap.addMarker(new MarkerOptions().position(locatedRoom.getPlace()).title(locatedRoom.getName()));
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEARCH_ACTIVITY || requestCode == SCHEDULE_ACTIVITY || requestCode == SEARCH_ACTIVITY_ITINERARY ){
            if (resultCode == RESULT_OK) {
                locatedRoom = data.getParcelableExtra("ROOM");
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(locatedRoom.getPlace()).title(locatedRoom.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(locatedRoom.getPlace()));

                IndoorBuilding b = mMap.getFocusedBuilding();
                List<IndoorLevel> il = b.getLevels();
                il.get(-locatedRoom.getFloor()+3).activate();

                if (requestCode == SEARCH_ACTIVITY_ITINERARY){
                    Intent intent = new Intent(MainActivity.this, Itinerary.class);
                    intent.putExtra("ROOM",locatedRoom);
                    startActivity(intent);
                }
            }
        }
    }
}
