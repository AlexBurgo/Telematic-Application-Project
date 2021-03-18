

package upf.at.app.bot.utils;

//structure of airquality API
public class AirQuality {
    private Data data;

    public AirQuality() {
    }

    public AirQuality(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AirQuality [data=" + data + "]";
    }
}