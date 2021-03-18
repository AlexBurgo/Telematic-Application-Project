

package upf.at.app.server;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import upf.at.app.data.client.Data;

@Path("/data")
public class ServicesClient {

    static Data dataClient = new Data(); 


    //This method takes the client information received by parameters and subscribes it to the client database.
    //There is already one client with that phone, we will update its list of subscribed stations
    @POST
    @Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
    public static void subscribe_client(NestedClient clientString) {
    	String phone = clientString.getPhone(); 
    	List<String> stationsIdsString = clientString.getStationsIds();
    	if(phone.equals("") && stationsIdsString.get(0).equals("")) {
    		throw new WebApplicationException(Response.status(450).entity("Please insert phone number "
    				+ "and the list of stations you want to get subscribed to").build());
    	} else if(phone.equals("")) {
    		throw new WebApplicationException(Response.status(450).entity("Please insert phone number").build());
    	} else if(stationsIdsString.get(0).equals("")) {
    		throw new WebApplicationException(Response.status(450).entity("Please insert the list of stations you want "
    				+ "to get subscribed to").build());
    	}else {

    		List<Integer> stationsIds = new LinkedList<>();

    		for(String stationID : stationsIdsString)
    			stationsIds.add(Integer.parseInt(stationID));

    		for (upf.at.app.data.client.Client client : dataClient.getClients()) {
    			if(Integer.parseInt(phone) == client.getPhone()){
    				List<Integer> old_stations = client.getStationIds();
    				client.setStationIds(stationsIds);
    				dataClient.updateStorage();
    				throw new WebApplicationException(Response.status(210).entity("The subscribed stations for the client "
    						+ clientString.getPhone() + " have been updates from " + old_stations 
    						+ " to " + client.getStationIds()).build());
    			}
    		}
    		String telegramTokenDefault = "1027998945:AAEYcQ5eQTblo6U9Kb-7H2nn-vcQI7BeBXE";
    		dataClient.addClient(new upf.at.app.data.client.Client(Integer.parseInt(clientString.getPhone()),
									stationsIds, telegramTokenDefault));
    		throw new WebApplicationException(Response.status(200).entity("Phone number " + clientString.getPhone()
					+ " has been sucessfully subsribed.\r\n"
					+ "Join the group https://t.me/joinchat/LmkD9hv4Moe_U5v6AWdsyQ to view the petitions made from the web or "
					+ "open a chat with @Albert_Alex_bot, this last only supports /getSlots {phone}").build());
    	}
    }
    

    //This method return the list of all the subscribed clients, if it is empty it will send a response
    @GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public static List<upf.at.app.data.client.Client> getClients() {
		if(dataClient.getClients().isEmpty()){
			throw new WebApplicationException(Response.status(225).entity("No clients subscribed").build());
		}else{
			return dataClient.getClients();
		}
	}

	//Nested Class used to catch the received JSON file in subscribe method, it is declared here
    //since no other will need it
	static class NestedClient{
		private String phone;
		private List<String> stationsIds;
		private String telegramToken;

		public NestedClient() {
		}

		public NestedClient(String phone, List<String> stationsIds, String telegramToken) {
			this.phone = phone;
			this.stationsIds = stationsIds;
			this.telegramToken = telegramToken;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public List<String> getStationsIds() {
			return stationsIds;
		}

		public void setStationsIds(List<String> stationsIds) {
			this.stationsIds = stationsIds;
		}

		public String getTelegramToken() {
			return telegramToken;
		}

		public void setTelegramToken(String telegramToken) {
			this.telegramToken = telegramToken;
		}

		@Override
		public String toString() {
			return "InnerClient [phone=" + phone + ", stationsIds=" + stationsIds + ", telegramToken=" + telegramToken
					+ "]";
		}

		
	}
}