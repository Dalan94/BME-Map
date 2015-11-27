package bme.hci.bme_map;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ScheduleActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        intent = getIntent();

    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private ListView list;
        private  ScheduleActivity activity;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber,ScheduleActivity sa) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            fragment.activity = sa;
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
            int day_num = getArguments().getInt(ARG_SECTION_NUMBER);

            TextView textView = (TextView) rootView.findViewById(R.id.schedule_day);
            list = (ListView)rootView.findViewById(R.id.listView_schedule);

            List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> element;
            for (int i=0 ; i<3 ; i++){
                element = new HashMap<String, String>();
                switch (day_num){
                    case 1:
                        switch (i){
                            case 0 :
                                element.put("text1", "8:00 - 10:00 : HCI");
                                break;
                            case 1 :
                                element.put("text1", "10:00 - 12:00 : Intelligent sensor");
                                break;
                            case 2 :
                                element.put("text1", "14:00 - 16:00 : Programming");
                                break;
                        }
                        break;
                    case 2:
                        switch (i){
                            case 0 :
                                element.put("text1", "8:00 - 10:00 : Java");
                                break;
                            case 1 :
                                element.put("text1", "10:00 - 12:00 : Software Engineering");
                                break;
                            case 2 :
                                element.put("text1", "12:00 - 14:00 : Network");
                                break;
                        }
                        break;
                    case 3:
                        switch (i){
                            case 0 :
                                element.put("text1", "10:00 - 12:00 : HCI");
                                break;
                            case 1 :
                                element.put("text1", "12:00 - 14:00 : Intelligent sensor");
                                break;
                            case 2 :
                                element.put("text1", "14:00 - 16:00 : Java");
                                break;
                        }
                        break;
                    case 4:
                        switch (i){
                            case 0 :
                                element.put("text1", "8:00 - 10:00 : C++");
                                break;
                            case 1 :
                                element.put("text1", "10:00 - 12:00 : Web");
                                break;
                            case 2 :
                                element.put("text1", "14:00 - 16:00 : Programming");
                                break;
                        }
                        break;
                    case 5:
                        switch (i){
                            case 0 :
                                element.put("text1", "8:00 - 10:00 : Security");
                                break;
                            case 1 :
                                element.put("text1", "10:00 - 12:00 : HCI");
                                break;
                            case 2 :
                                element.put("text1", "14:00 - 16:00 : Artificial intelligence");
                                break;
                        }
                        break;
                }

                element.put("text2", SearchActivity.rooms.get(i+day_num-1).getName());
                liste.add(element);
            }
            ListAdapter adapter = new SimpleAdapter(activity, liste,
                    android.R.layout.simple_list_item_2,
                    new String[] {"text1", "text2"},
                    new int[] {android.R.id.text1, android.R.id.text2 });
            list.setAdapter(adapter);
            list.setOnItemClickListener(adapt);


            final RadioGroup radioGroup = (RadioGroup)rootView.findViewById(R.id.radiogroup);

            /*radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.radioButton1:
                            Toast.makeText(activity,"Value " + 0, Toast.LENGTH_SHORT).show();
                            //activity.mViewPager.setCurrentItem(0,true);
                            //activity.mViewPager.setCurrentItem(0);
                            break;
                        case R.id.radioButton2:
                            Toast.makeText(activity,"Value " + 1, Toast.LENGTH_SHORT).show();
                            //activity.mViewPager.setCurrentItem(1);
                            break;
                        case R.id.radioButton3:
                            Toast.makeText(activity,"Value " + 2, Toast.LENGTH_SHORT).show();
                            //activity.mViewPager.setCurrentItem(2);
                            break;
                        case R.id.radioButton4:
                            Toast.makeText(activity,"Value " + 3, Toast.LENGTH_SHORT).show();
                            //activity.mViewPager.setCurrentItem(3);
                            break;
                        case R.id.radioButton5:
                            Toast.makeText(activity,"Value " + 4, Toast.LENGTH_SHORT).show();
                            //activity.mViewPager.setCurrentItem(4);
                            break;
                    }
                }
            });*/

            String day = null;
            switch (day_num){
                case 1:
                    day = "Monday";
                    radioGroup.check(R.id.radioButton1);
                    break;
                case 2:
                    day = "Tuesday";
                    radioGroup.check(R.id.radioButton2);
                    break;
                case 3:
                    day = "Wednesday";
                    radioGroup.check(R.id.radioButton3);
                    break;
                case 4:
                    day = "Thursday";
                    radioGroup.check(R.id.radioButton4);
                    break;
                case 5:
                    day = "Friday";
                    radioGroup.check(R.id.radioButton5);
                    break;
            }
            textView.setText(day);

            return rootView;
        }

        private AdapterView.OnItemClickListener adapt = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int day_num = getArguments().getInt(ARG_SECTION_NUMBER);
                Room room = SearchActivity.rooms.get(position + day_num -1);
                activity.setResult(RESULT_OK, activity.intent);
                activity.intent.putExtra("ROOM",room);
                activity.finish();
            }
        };
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1,ScheduleActivity.this);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MONDAY";
                case 1:
                    return "TUESDAY";
                case 2:
                    return "WEDNESDAY";
                case 3:
                    return "THURSDAY";
                case 4:
                    return "FRIDAY";
            }
            return null;
        }
    }
}
