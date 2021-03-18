

package upf.at.app.data.bicing;

import java.util.List;
//stations structure, holding a list of station
public class Stations {
    private List<Station> stations;

    public Stations() {
    }

    public Stations(List<Station> stations) {
        this.stations = stations;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    @Override
    public String toString() {
        return "Stations [stations=" + stations + "]";
    }

}
