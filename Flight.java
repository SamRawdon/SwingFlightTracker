import java.util.*;

public class Flight {
	// Fields
	private int flightID;
	private String depLoc;
	private String arrLoc;
	private String depDate;
	private String depTime;
	private Plane plane;
	    
	// Methods
	public Flight(int id, String departure, String arrival, String date, String time, Plane plane) throws InvalidTimeException, InvalidDateException, Exception{
		
		this.flightID = id;
		
		depLoc = tidyLoc(departure);
		arrLoc = tidyLoc(arrival);
				
		if(isValidTime(time) == true)
			depTime = time;
		else
			throw new InvalidTimeException("Invalid time: " + time + ". Please enter in format xx:xx in military time.");
		
		if(isValidDate(date) == true)
			depDate = date;
		else
			throw new InvalidDateException();
		
		if(plane.getAvailability()) {
			this.plane = plane;
			plane.setAvailability(false);
		} else
			throw new Exception("Plane is unavailable");
		
	}
	
	public Plane getPlane() {
		return plane;
	}
	
	public int getPlaneID() {
		return plane.getID();
	}

	public int getFlightID() {
		return flightID;
	}

	public String getDeparture() {
		return depLoc;
	}

	public String getArrival() {
		return arrLoc;
	}
	
	public String getDepartureTime() {
		return depTime;
	}
	
	public String getDate() {
		return depDate;
	}
	
	public String getDepartureDate() {
		return depDate;
	}
	
	public void setFlightID(int id) {
		this.flightID = id;
	}
	
	public void setDeparture(String depart) throws Exception {
		if(tidyLoc(depart).equals(arrLoc) == false)
			depLoc = tidyLoc(depart);
		else
			throw new Exception("Departure and arrival locations cannot match");
	}
	
	public void setArrival(String arrive) throws Exception{
		if(tidyLoc(arrive).equals(depLoc) == false)
			arrLoc = tidyLoc(arrive);
		else
			throw new Exception("Departure and arrival locations cannot match");
	}
	
	public void setDepartureTime(String s) throws InvalidTimeException{
		if(isValidTime(s)==true) {
			depTime = s;
		} else {
			throw new InvalidTimeException("Invalid time: " + s + ". Please enter in format xx:xx in military time.");
		}
	}
	
	public void setDepartureDate(String s) throws InvalidDateException{
		if(isValidDate(s)==true) {
			depDate = s;
		} else {
			throw new InvalidDateException();
		}
	}
	
	public void setPlane(Plane p) throws Exception{
		if(p.getAvailability()==true) {
			plane.setAvailability(true);
			plane = p;
			plane.setAvailability(false);
		} else
			throw new Exception("Plane is unavailable");
	}
	
	public String toString() {
		return "---REPORT FOR FLIGHT " + flightID + "---\nSERVICE FROM: " + depLoc + "\nSERVICE TO: " + arrLoc + "\nDATE: " + depDate + "\nDEPARTURE TIME: " + depTime + "\n";
	}
	
	public static Date convertDate(Flight f) {
		
		String s = f.getDate();
		
		int month = Integer.parseInt(s.substring(0, 2)) - 1;
		int day = Integer.parseInt(s.substring(3,5));
		int year = Integer.parseInt(s.substring(6, 10));
		
		Date flightDate = new Date(year, month, day);
		
		return flightDate;
		
	}
	
	public static boolean isValidDate(String s) {
		Date date = new Date();
		Date flightDate = new Date(s);
		
		if(date.before(flightDate))
			return true;
		else
			return false;
		
	}
	
	public static boolean isValidTime(String s) {
		
		int a = Integer.parseInt(s.substring(0, 2));
		int b = Integer.parseInt(s.substring(3, 5));
		
		if( a > 24 || a < 0 || b > 59 || b < 0) {
			return false;
		} else if(s.charAt(2) != ':') {
			return false;
		} else {
			return true;
		}
	}
	
	public static String tidyLoc(String s) {

		int it = 0;
		String temp = "";
		
		if(s.length()>1) {
			
			for(int i = 0; i < s.length(); i++) {
				
				if(s.charAt(i) == ' ') {	
					it = 0;
					
					do {
						i++;
						if(it == 0 && temp.isEmpty() == false) {
							temp += " ";
							it++;
						}
					} while(s.charAt(i)==' ');
					
					temp = temp + s.substring(i, i+1).toUpperCase();
					
				} else
					temp += s.substring(i,i+1).toUpperCase();

			}
		} else if(s.length() == 1)
			temp = s.toUpperCase();
		
		return temp;
	}

}

class InvalidTimeException extends Exception {
	
	public InvalidTimeException(String errorMessage) {
	    super(errorMessage);
	}
	
	public InvalidTimeException() {
		this("Please enter time formatted xx:xx military time");
	}
	
}

class InvalidDateException extends Exception {
	
	public InvalidDateException(String errorMessage) {
	    super(errorMessage);
	}
	
	public InvalidDateException() {
		this("Please enter date formatted mm/dd/yyyy");
	}
	
}
