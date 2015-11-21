package bme.hci.bme_map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Itinerary extends AppCompatActivity {

    private Room room;
    private Intent intent;

    private ListView list;
    private TextView title;
    private ArrayAdapter<String> adapter;
    private List<String> itinerary_path;
    //private String[] list_path = {"",""};
    private Random ran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        intent = getIntent();

        room = intent.getParcelableExtra("ROOM");

        list = (ListView)findViewById(R.id.itinerary_list);
        title = (TextView)findViewById(R.id.itinerary_title);

        title.setText(room.getName());

        itinerary_path = new ArrayList<String>();

        ran = new Random();
        if (ran.nextBoolean())
            itinerary_path.add("Go to left side of the building");
        else
            itinerary_path.add("Go to right side of the building");
        if (room.getFloor() != 0)
            itinerary_path.add("Take the lift or the stairs to the " + room.getFloor() + "th floor");
        if (ran.nextBoolean())
            itinerary_path.add("Turn left");
        else
            itinerary_path.add("Turn right");
        if (ran.nextBoolean())
            itinerary_path.add("It's the " + ran.nextInt(25) + "th door to the left");
        else
            itinerary_path.add("It's the " + ran.nextInt(25) + "th door to the right");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itinerary_path);
        list.setAdapter(adapter);
    }
}
