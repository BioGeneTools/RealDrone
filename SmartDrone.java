import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SmartDrone {
	
	public static void main(String[] args) {
		
		// Customers
		new Delivery("Kronprinzenstraße 88, 40217 Düsseldorf", 51.211730, 6.770070);
		new Delivery("Kaiserstraße 2, 40479 Düsseldorf", 51.235180, 6.778450);
		new Delivery("Wildenbruchstraße 2, 40545 Düsseldorf", 51.228970, 6.753100);
		new Delivery("Schlesische Straße 5, 40231 Düsseldorf", 51.208150, 6.831160);
	
	}
}


class DroneStoreLocations{
	HashMap<String, Double[]> droneInformation = new LinkedHashMap<>();
	HashMap<String, Double[]> storeInformation = new LinkedHashMap<>();
	
	public DroneStoreLocations() {
		preliminaryInformation();
	}
	
	public void preliminaryInformation() {
		addStores("Schiessstraße 31, 40549 Düsseldorf", 51.237590, 6.720270);
		addStores("Friedrichstraße 152, 40217 Düsseldorf", 51.208830, 6.778070);
		addStores("Breslauer Str. 2, 41460 Neuss", 51.201920, 6.719300);
		addStores("Bataverstraße 93, 41462 Neuss", 51.231110, 6.685440);
		addStores("Am Sandbach 30, 40878 Ratingen", 51.295200, 6.831740);

		addDrones("Metrostrasse 12, 40235 Düsseldorf", 51.234720, 6.825350);
		addDrones("Am Albertussee 1, 40549 Düsseldorf", 51.236980, 6.723880);
	}
	
	public void addStores(String x, Double lan, Double lon) {
		Double[] lanlon = {lan, lon};
		storeInformation.put(x, lanlon);
	}
	public void addDrones(String x, Double lan, Double lon) {
		Double[] lanlon = {lan, lon};
		droneInformation.put(x, lanlon);
	}
	
}

class Delivery extends DroneStoreLocations{
	private String x;
	private Double lan;
	private Double lon;
	public Delivery(String x, Double lan, Double lon) {
		this.x = x;
		this.lan = lan;
		this.lon = lon;
		distanceCalculation(this.x, this.lan, this.lon);
	}
	private void distanceCalculation(String x, Double lan, Double lon) {
		String[] pickupStore = null;
		double timeofDelivery = 0;
		for(Map.Entry<String, Double[]> i : storeInformation.entrySet()){
			String location = null;
			double distance = 0; 
			Double distanceCustomertoStore = calculateDistance(lan, lon, i.getValue()[0], i.getValue()[1]);
			for(Map.Entry<String, Double[]> d : droneInformation.entrySet()){
				Double distanceStoretoDrone = calculateDistance(i.getValue()[0], i.getValue()[1], d.getValue()[0], d.getValue()[1]);
				if(location == null){
					location = d.getKey();
					distance = distanceStoretoDrone;
				}else if(distanceStoretoDrone < distance){
					location = d.getKey();
					distance = distanceStoretoDrone;
				}
			}
			String[] address = {i.getKey(), location};
			if(pickupStore == null){
				pickupStore = address;
				timeofDelivery = distance + distanceCustomertoStore;
			}else if((distance + distanceCustomertoStore) < timeofDelivery){
				pickupStore = address;
				timeofDelivery = distance + distanceCustomertoStore;
			}
		}
		printMe(pickupStore, timeofDelivery);
	}
	
	
	public void printMe(String[] address, double location) {
		System.out.println("Sehr geehrter Kunde,,\n"
				+ "Ihre Bestellung wird in unserem Geschäft abgeholt: \n((("+address[0]+"))) \nund unsere freundliche Drohne "
						+ "in \n((("+address[1]+"))) \nwurde versandt, um Ihre Wünsche"
				+ "in "+ String.format("%1.2f", location)+" Minuten zu erfüllen");
		System.out.println("-------------------------------\n");
	}

		// Function to calculate distance between two GPS coordinate
		final int Earch_radius = 6371;
		public Double calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {

		    double latDistance = Math.toRadians(userLat - venueLat);
		    double lngDistance = Math.toRadians(userLng - venueLng);

		    double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
		                    (Math.cos(Math.toRadians(userLat))) *
		                    (Math.cos(Math.toRadians(venueLat))) *
		                    (Math.sin(lngDistance / 2)) *
		                    (Math.sin(lngDistance / 2));
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		    double result =  Earch_radius * c;
		    return result;
		}
}




