package pl.app.citiesonmap.data;


public class City {

    private final String name;
    private final double lat;
    private final double lng;

    public City(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public City() {
        this.name = "";
        this.lat = 0;
        this.lng = 0;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return name;
    }
}
