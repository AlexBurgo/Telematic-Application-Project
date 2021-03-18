

package upf.at.app.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import upf.at.app.bot.BotHear;
import upf.at.app.bot.BotLocate;
import upf.at.app.bot.BotResponse;

@Path("/bot")
public class ServicesNotify {
    
    private static boolean bot_awake;

    //wakes up the bot to listen to all the incoming messages via telegram
    @POST
    @Path("/wakeup")
    public static void wakeup(){
        if(!bot_awake){
            bot_awake = true;
            BotHear bot = new BotHear();
            bot.start();
            throw new WebApplicationException(Response.status(230).entity("Bot listening").build());
        }
        throw new WebApplicationException(Response.status(235).entity("Bot was already listening").build());
    }

    //Method used to send the list of docks available, relies on BotResponse
    @POST
    @Path("/notifyStt")
    @Consumes(MediaType.APPLICATION_JSON)
    public static void notify_station(Phone p){
    	if(p.getPhone().equals("")) {
    		throw new WebApplicationException(Response.status(450).entity("Please insert a phone number").build());
    	}else {
    		int phone = Integer.parseInt(p.getPhone());
    		BotResponse response = new BotResponse(phone);
    		response.start();
    		throw new WebApplicationException(Response.status(210).entity("Check the telegram group").build());
    	}
    }

    //Method used to send the air quality to telegram file, relies on BotResponse
    @POST
    @Path("/airquality")
    @Consumes(MediaType.APPLICATION_JSON)
    public static void notify_airqualityremote(IP ip){
        BotLocate locate = new BotLocate(ip.getIp());
        locate.start();   
        throw new WebApplicationException(Response.status(210).entity("Check the telegram group").build()); 
    }

    //Nested class used to catch the received JSON file in notify stations
    private static class IP{
        private String ip;

        public IP() {
        }

        public IP(String ip) {
            this.ip = ip;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        @Override
        public String toString() {
            return "IP [ip=" + ip + "]";
        }
    }
    
  //Nested class used to catch the received JSON file in air quality method
    private static class Phone{

        private String phone;


        public Phone(){
        
        }

        public Phone(String phone){
            this.phone = phone;
        }


        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "Phone [phone=" + phone + "]";
        }

        
    }
}