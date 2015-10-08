package bme.hci.bme_map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dalan on 07/10/15.
 */
public class SearchActivity extends AppCompatActivity {
    static private List<Room> rooms;
    private EditText searchText;
    private ListView searchList;
    private List<String> rooms_founded;
    private Intent intent;
    private ArrayAdapter<String> adapter;
    static {
        rooms = new ArrayList<>();
        rooms.add(new Room("La cité des enfants", 0, new LatLng(48.895592, 2.388550)));
        rooms.add(new Room("Le Cafe de Cité", 0, new LatLng(48.895457, 2.3879570)));
        rooms.add(new Room("Ombres et lumières",0,new LatLng(48.895046, 2.387378)));
        rooms.add(new Room("Restaurant",1,new LatLng(48.895400, 2.388067)));
        rooms.add(new Room("La science dans ma vie",1,new LatLng(48.895113, 2.386825)));
        rooms.add(new Room("Le grand récit de l'univers",2,new LatLng(48.895386, 2.386511 )));
        rooms.add(new Room("Planetarium",2,new LatLng(48.895121, 2.386844)));
        Collections.sort(rooms, new RoomComparator());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        intent = getIntent();

        searchText = (EditText)findViewById(R.id.editTextSearch);
        searchText.addTextChangedListener(searchWatchr);

        searchList = (ListView)findViewById(R.id.listViewSearch);

        rooms_founded = new ArrayList<String>();
        for (Room r:rooms){
            rooms_founded.add(r.getName());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rooms_founded);
        searchList.setAdapter(adapter);
        searchList.setOnItemClickListener(adapt);
    }

    private TextWatcher searchWatchr = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            rooms_founded.clear();
            for (Room r:rooms){
                if (r.nameContain(s)){
                    rooms_founded.add(r.getName());
                }
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private AdapterView.OnItemClickListener adapt = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String s = rooms_founded.get(position);
            Room room = null;
            for(Room r:rooms){
                if (r.getName().equals(s)){
                    room = r;
                    break;
                }
            }
            setResult(RESULT_OK, intent);
            intent.putExtra("ROOM",room);
            finish();
        }
    };
}