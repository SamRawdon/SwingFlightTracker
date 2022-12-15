public class Person implements Comparable<Person>{
    //State
    private String name;
    
    //Constructors
    public Person(String name){
       this.name = name;
    }
    
    //Getters
    public String getName(){
        return name;
    }
    
    //Setters
    public void setName(String name){
        this.name = name;
    }
    
    public int compareTo(Person p) {
    	if(this.getName().compareToIgnoreCase(p.getName())==1)
    		return 1;
    	else if(this.getName().compareToIgnoreCase(p.getName())==-1)
    		return -1;
    	else 
    		return 0;
    }
}
