

package upf.at.app.bot;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import upf.at.app.server.ServicesClient;
import upf.at.app.data.bicing.Station;
import upf.at.app.server.ServicesStations;


//Explained on the report in details
public class BotResponse extends Thread {

    private long chatID;
    private String text;
    private int phone;

    public BotResponse(int phone) {
        this.phone = phone;
    }

    public BotResponse(String text){
        this.text = text;
    }

    public BotResponse(long chatID, String text) {
        this.chatID = chatID;
        this.text = text;
    }

    @Override
    public void run() {
        String answer = null;
        String token  = "1027998945:AAEYcQ5eQTblo6U9Kb-7H2nn-vcQI7BeBXE";
        List<Integer> stationIds = null;

        if (chatID == 0) {
            chatID = -469250695;
            if (text != null){
                this.phone = Integer.MIN_VALUE;
                answer = text;
            }
        } else {
            phone = Integer.MIN_VALUE;
            answer = "I don't know what you mean";
            String[] parts = text.split(" ");
            if (parts[0].equalsIgnoreCase("/getSlots") && parts.length == 2) {
                this.phone = Integer.parseInt(parts[1]);
            } else if (parts[0].equalsIgnoreCase("/getSlots") && parts.length == 1){
                answer = "Please use: /getSlots {phone number}";
            }
        }

        if (this.phone != Integer.MIN_VALUE) {
            try {
                    List<upf.at.app.data.client.Client> clients = ServicesClient.getClients();
                for (upf.at.app.data.client.Client client : clients) {
                    if (client.getPhone() == this.phone) {
                        stationIds = client.getStationIds();
                        token = client.getTelegramToken();
                        break;
                    }
                }
                if (stationIds != null) {
                    answer = "";
                    for (int id : stationIds) {
                        for (Station s : ServicesStations.getStations()) {
                            if (s.getStation_id() == id) {
                                answer += "Station " + id + ": " + s.getNum_docks_available() + " docks available\n";
                                continue;
                            }
                        }
                    }

                }else{
                    answer = "This phone is not subscribed";
                }
            } catch (WebApplicationException e){
                if (e.getResponse().getStatus() == 225) {
                    answer = "There are no clients subscribed";
                }
            }
        }
        Message message = new Message(chatID, answer);
        Client client = ClientBuilder.newClient();
        WebTarget targetSendMessage = client.target("https://api.telegram.org").path("/bot" + token + "/sendMessage");
        String response = targetSendMessage.request().post(Entity.entity(message, MediaType.APPLICATION_JSON_TYPE),
                String.class);
    }

    private static class Message {
        private long chat_id;
        private String text;

        public Message() {
        }

        public Message(long chat_id, String text) {
            this.chat_id = chat_id;
            this.text = text;
        }

        public long getChat_id() {
            return chat_id;
        }

        public void setChat_id(long chat_id) {
            this.chat_id = chat_id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return "Message [chat_id=" + chat_id + ", text=" + text + "]";
        }

    }
}