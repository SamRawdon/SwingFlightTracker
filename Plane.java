import java.util.*;

public class Plane {

  private int planeID;
  private boolean available;
  private final int CAPACITY;
  private int numOfStaff;
  private Staff[] currentStaff;

  public Plane(int id, int capacity, int maxStaff) {
 
    planeID = id;
    available = true;
    CAPACITY = capacity;
    currentStaff = new Staff[maxStaff];
    numOfStaff = 0;
    available = true;
      
  }
  
  public int getID() {
	  return planeID;
  }
  
  public Staff[] getStaff(){
	  return currentStaff;
  }
  
  public boolean getAvailability() {
	  return available;
  }
  
  public int getCapacity() {
	  return CAPACITY;
  }
  
  public int getNumOfStaff() {
	  return numOfStaff;
  }
  
  public void setAvailability(boolean b) {
	  available = b;
  }
  
  public void addStaff(Staff s) throws Exception{
	  if(numOfStaff>(currentStaff.length)) {
		  throw new Exception("Too many staff");
	  } else {
		  currentStaff[numOfStaff] = s;
	  }
	  numOfStaff++;
  }
  
  public void removeStaff(Staff s) {
	  for(int i = 0; i < currentStaff.length; i++) {
		  if(currentStaff[i].equals(s)) {
			  for(int j = i; j < currentStaff.length - (i+1) ; j++) {
				  currentStaff[j] = currentStaff[j+1];
			  }
			  numOfStaff--;
		  }
			  
	  }
  }
  
  public String toString() {
	  return planeID + " " + Arrays.toString(currentStaff);
  }
  
}
