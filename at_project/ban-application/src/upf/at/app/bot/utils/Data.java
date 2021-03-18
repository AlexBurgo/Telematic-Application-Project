

package upf.at.app.bot.utils;

//structure of airquality api
public class Data {
    private int aqi;

    public Data() {

    }

    public Data(int aqi) {
        this.aqi = aqi;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    @Override
    public String toString() {
        return "Data [aqi=" + aqi + "]";
    }

}