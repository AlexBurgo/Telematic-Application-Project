

package upf.at.app.data.client;

import java.io.Serializable;
import java.util.List;

//Structure of a client, based on the requirements
public class Client implements Serializable{
    private int phone;
    private List<Integer> stationIds;
    private String telegramToken;

    public Client() {
    }

    public Client(int phone, List<Integer> stationIds, String telegramToken) {
        this.phone = phone;
        this.stationIds = stationIds;
        this.telegramToken = telegramToken;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getTelegramToken() {
        return telegramToken;
    }

    public void setTelegramToken(String telegramToken) {
        this.telegramToken = telegramToken;
    }

    public List<Integer> getStationIds() {
        return stationIds;
    }

    public void setStationIds(List<Integer> stationIds) {
        this.stationIds = stationIds;
    }

    @Override
    public String toString() {
        return "Client [phone=" + phone + ", stationIds=" + stationIds + ", telegramToken=" + telegramToken + "]";
    }
       
}