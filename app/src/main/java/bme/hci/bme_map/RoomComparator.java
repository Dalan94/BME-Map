package bme.hci.bme_map;

import java.util.Comparator;

/**
 * Created by dalan on 08/10/15.
 */
public class RoomComparator implements Comparator<Room> {

    public RoomComparator() {
    }

    @Override
    public int compare(Room arg0, Room arg1) {
        return arg0.getName().compareTo(arg1.getName());
    }

}
