

package upf.at.app.bot;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import upf.at.app.bot.utils.Response;
import upf.at.app.bot.utils.Update;

//Thread that keeps listening to the getUpdates, with an offset
//to avoid getting already served messages all over again
public class BotHear extends Thread{
    private String token = "1027998945:AAEYcQ5eQTblo6U9Kb-7H2nn-vcQI7BeBXE";
    private long last_updateId;

    @Override
    public void run() {
        Client client = ClientBuilder.newClient();
        try {
        	 while(true){
 	            WebTarget target = client.target("https://api.telegram.org")
 	            .path("/bot"+ token +"/getUpdates").queryParam("offset", last_updateId);
 	            Response item = target.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<Response>() {});
 	            for(Update update : item.getResult()){
 	                if(update == item.getResult().get(item.getResult().size()-1)){
 	                    last_updateId = update.getUpdate_id()+1;
 	                }
 	                BotResponse response = new BotResponse(update.getMessage().getChat().getId(), update.getMessage().getText());
 	                response.start();
 	            }
 	        }
        }catch (RuntimeException e) {
	       System.out.println(e.getMessage());
        }
    }    
}