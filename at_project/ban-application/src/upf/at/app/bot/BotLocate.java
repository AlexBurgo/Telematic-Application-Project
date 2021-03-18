

package upf.at.app.bot;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import upf.at.app.bot.utils.AirQuality;

//Explained in the report
public class BotLocate extends Thread {
    private String ip;

    public BotLocate(String ip) {
        this.ip = ip;
    }

    @Override
    public void run() {
    	try {
    		String API_KEY = "7b59d54202784879b527833bab79a256";
            String token = "9eeb3d444c6de968397da215a0f4c00105d9fdb7";
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("https://api.ipgeolocation.io/")
                        .path("/ipgeo")
                        .queryParam("apiKey", API_KEY)
                        .queryParam("ip", ip);
            Geolocation item = target.request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<Geolocation>() {});
            target = client.target("https://api.waqi.info").path("/feed/" + item.getCity() + "/")
                            .queryParam("token", token);
            AirQuality quality = target.request(MediaType.APPLICATION_JSON_TYPE)
                            .get(new GenericType<AirQuality>() {});
            int aiq = quality.getData().getAqi();
            String message = null;

            if (0<= aiq && aiq <= 50){
                message = "Good";
            }else if(51<= aiq && aiq <= 100){
                message = "Moderate";
            }else if(101<= aiq && aiq <= 150){
                message = "Unhealthy for Sensitive Groups";
            }else if(151<= aiq && aiq <= 200){
                message = "Unhealthy";
            }else if(201<= aiq && aiq <= 300){
                message = "Very Unhealthy";
            }else{
                message = "Hazardous";
            }

            BotResponse bot = new BotResponse("The Air Quality of " + item.getCity() + " is " + message);
            bot.start();
    	} catch(RuntimeException e) {
    		System.out.println(e.getMessage());
    	}
    }

    private static class Geolocation {
        private String city;

        public Geolocation() {

        }

        public Geolocation(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "Geolocation [city=" + city + "]";
        }
    }
}