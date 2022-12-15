import java.io.File;
import java.io.IOException;
import java.util.*;

public class Company {

	private String companyName;
  	private static ArrayList<Plane> allPlanes;
  	private ArrayList<Staff> allStaff;
  	private static ArrayList<Flight> allFlights;
  
 	public Company(String n) throws Exception{
 		
  		companyName = n;
		allPlanes = new ArrayList<Plane>();
		allStaff = new ArrayList<Staff>();
  		allFlights = new ArrayList<Flight>();
  		
  		String line = "";
		String name = "";
		String position = "";
		int planeIt = 0;
			
		File file = new File("Staff.txt");	
		Scanner s = new Scanner(file);
		
		for(int i = 1; i < 51; i++) {
			allPlanes.add(new Plane(i, 416, 5));
		}
  		
  		try {
		
			while(s.hasNextLine()) {
				line = s.nextLine();
				for(int i = 0; i < line.length(); i++) {
					if(line.charAt(i)==',') {
						name = line.substring(0, i);
						position = line.substring(i);
						for(int j = 0; j < position.length(); j++) {
							if(position.charAt(j) == 'f' || position.charAt(j) == 'p')
								position = position.substring(j);
						}
					}
				}
				
				try {
					allStaff.add(new Staff(name, position, allPlanes.get(planeIt)));
//					allStaff.get(allStaff.size()-1).setPlane(allPlanes.get(planeIt));
				} catch(Exception e) {
					if(planeIt<49) {
						planeIt++;
						allStaff.add(new Staff(name, position, allPlanes.get(planeIt)));
					}
				}
				
//				if(iterator%5==0) {
//					planeIt++;
//				}
//				if(planeIt<51) {
//					allStaff.get(allStaff.size()-1).setPlane(allPlanes.get(planeIt));
//				}
//				System.out.println(iterator + " " + planeIt);
//				iterator++;
				
			}			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
				
		s.close();
		
  	}
 	
	public static ArrayList<Flight> getAllFlights() {
		return allFlights;
	}
	
	public String getCompanyName() {
 		return companyName;
 	}
	
	public static Plane getPlaneInfo(int id) {
		return allPlanes.get(id-1);
	}

	public Flight getFlight(int id) {
		return allFlights.get(id);
	}
	
 	public void setCompanyName(String n) {
 		companyName = n;
 	}
	
	public void addStaff(Staff newStaff) {
		allStaff.add(newStaff);
	}
	
	public static void createFlight(String dep, String arr, String depDate, String depTime, Plane plane) throws InvalidTimeException, InvalidDateException, Exception {
		int id = allFlights.size();
		while(getIDAvailability(id) == false)
			id++;
			
		allFlights.add(new Flight (allFlights.size(), dep, arr, depDate, depTime, plane));
		
	}
	
	public static boolean getIDAvailability(int id) {
		for(int i = 0; i<allFlights.size(); i++) {
			if(allFlights.get(i).getFlightID() == id)
				return false;
		}
		return true;
	}

}
