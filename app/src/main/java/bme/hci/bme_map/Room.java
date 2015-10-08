package bme.hci.bme_map;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

/**
 * Created by dalan on 07/10/15.
 */
public class Room implements Parcelable, Comparator<Room> {
    private String name;
    private int floor;
    private double latitude;
    private double longitude;

    public Room(String name, int floor, LatLng place) {
        this.name = name;
        this.floor = floor;
        this.latitude = place.latitude;
        this.longitude = place.longitude;
    }

    public Room(String name, int floor, double lat, double longi) {
        this.name = name;
        this.floor = floor;
        this.latitude = lat;
        this.longitude = longi;
    }

    @Override
    public int compare(Room lhs, Room rhs) {
        return lhs.getName().compareTo(rhs.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return !(name != null ? !name.equals(room.name) : room.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public boolean nameContain(CharSequence str){
        return name.toLowerCase().contains(str.toString().toLowerCase());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public LatLng getPlace() {
        return new LatLng(latitude,longitude);
    }

    public void setPlace(LatLng place) {
        this.latitude = place.latitude;
        this.longitude = place.longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(floor);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public static final Parcelable.Creator<Room> CREATOR = new Parcelable.Creator<Room>(){

        @Override
        public Room createFromParcel(Parcel source) {
            return new Room(source);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public Room(Parcel in) {
        name = in.readString();
        floor = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }
}
