public class Staff extends Person{

    private int position;
    private Plane plane;

    //constructor: 
    public Staff (String name, String pos, Plane p) throws Exception{
        super(name);
        if(isValidPosition(pos)==-1){
        	throw new Exception();
        } else 
        	position = isValidPosition(pos);
        plane = p;
        if(plane != null)
        	plane.addStaff(this);
    }

    public int getPosition(){
       return position;
    }
    
    public Plane getPlane() {
    	return plane;
    }
    
    public void setPlane(Plane p) throws Exception{
    	if(plane != null)
    		plane.removeStaff(this);
    	plane = p;
    	plane.addStaff(this);
    }

    public static int isValidPosition(String s) {
    	
    	if(s.toLowerCase().charAt(0) == 'f')
    		return 1;
    	else if(s.toLowerCase().charAt(0) == 'p')
    		return 2;
    	else 
    		return -1;
    }
    
    public boolean equals(Staff s) {
    	if((this.getName() == s.getName()) && (position == s.getPosition()) && (plane == s.getPlane()))
    		return true;
    	else
    		return false;
    }
    
    public String toString() {
    	String pos = "";
    	if(position == 1)
    		pos = "Flight Attendent";
    	else
    		pos = "Pilot";
    	return this.getName() + ": " + pos;
    }
    
    
}
