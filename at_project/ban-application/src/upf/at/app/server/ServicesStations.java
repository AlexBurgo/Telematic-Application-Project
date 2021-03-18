

package upf.at.app.server;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import upf.at.app.data.bicing.Data;

import upf.at.app.data.bicing.Station;

@Path("/stations")
public class ServicesStations {
	
	static upf.at.app.data.bicing.Data dataBicing; //Cache
	private static int expiration_interval_sec = 120; //Expiration time in seconds
	private static double expiration; //when will the data of the cache expire

	//Returns the list of stations with all the information
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public static List<Station> getStations() {
		try {
			if (dataBicing == null || System.currentTimeMillis() >= expiration){
				Client client = ClientBuilder.newClient();
				String bicing_url = "https://api.bsmsa.eu";
				String path = "/ext/api/bsm/gbfs/v2/en/station_status";
				WebTarget target = client.target(bicing_url).path(path);
				dataBicing = target.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType <Data>(){});
				expiration = System.currentTimeMillis() + expiration_interval_sec * 1000;
			}
			return dataBicing.getData().getStations();
		} catch (RuntimeException e) {
			throw new WebApplicationException(Response.status(404).entity("Error. Could not find stations").build());
		}
	}
}