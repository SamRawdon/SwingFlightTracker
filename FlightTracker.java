import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.*;
import java.io.*;
import javax.swing.BoxLayout;
import java.awt.Container;
import java.awt.event.*;
import java.awt.*;

public class FlightTracker {
	
	private static JFrame flightTrackerFrame;
//	private static Container ControlHost;
	
	private static JPanel card2;
	private static JScrollPane scrollPane;
	private static JTable flights;
	private static EditableTable l;
	
	final static String CREATEFLIGHTS = "Create Flights";
    final static String VIEWFLIGHTS = "View Flights";
    final static int extraWindowWidth = 100;
	
//	private static SpringLayout buttonPanelLayout;
//	private static SpringLayout flightPanelLayout;
//	private static SpringLayout frameLayout;
//	private static Container contentPane;
    
    private JLabel departureL;
    private JLabel arrivalL;
    private JLabel departureD;
    private JLabel departureT;
    private JLabel plane;
    
    private JTextField departureLoc;
    private JTextField arrivalLoc;
    private JTextField departureDat;
    private JTextField departureTim;
    private JTextField planeID;

	private JButton createButton;
	private static JButton showInfoButton;
	//private JButton clearButton;

	
	public FlightTracker() throws InvalidTimeException, Exception{
		Company delta = new Company("Delta");
		
		flightTrackerFrame = new JFrame("Flight Tracker");
		flightTrackerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        flightTrackerFrame.setBounds(100, 100, 450, 220);
        
//        ControlHost = flightTrackerFrame.getContentPane();
//        ControlHost.setLayout(new FlowLayout());
		
        addComponentsToPane(flightTrackerFrame.getContentPane());
		
        //SpringLayout layout = new SpringLayout();
        //setLayout(layout);
        
        flightTrackerFrame.pack();
        flightTrackerFrame.setVisible(true);
        
	}

		public void addComponentsToPane(Container pane) throws InvalidTimeException, InvalidDateException, Exception {
			
			JTabbedPane tabbedPane = new JTabbedPane();
	 
			JPanel leftcard1 = new JPanel() {
			//Make the panel wider than it really needs, so
			//the window's wide enough for the tabs to stay
			//in one row.
				public Dimension getPreferredSize() {
					Dimension size = super.getPreferredSize();
					size.width += extraWindowWidth;
					return size;
	        	}
			};
	        
	        JPanel rightcard1 = new JPanel(){
	            public Dimension getPreferredSize() {
	                Dimension size = super.getPreferredSize();
	                size.width += extraWindowWidth;
	                return size;
	            }
	        };
	        	        
	        leftcard1.setLayout(new BoxLayout(leftcard1, BoxLayout.Y_AXIS));
	        rightcard1.setLayout(new BoxLayout(rightcard1, BoxLayout.Y_AXIS));
	        
	        createButton = new JButton("Create");
	        showInfoButton = new JButton("Show More Info");
//	        clearButton = new JButton("Clear");
									
			CreateButtonListener createListener = new CreateButtonListener();			
			createButton.addActionListener(createListener);	
			
			ShowInfoButtonListener infoListener = new ShowInfoButtonListener();
			showInfoButton.addActionListener(infoListener);
			
//			ClearButtonListener clearListener = new ClearButtonListener();
//			clearButton.addActionListener(clearListener);
			
			int textSize = 15;
			
			departureL = new JLabel("Departure Location:");
			departureL.setFont(new Font("Serif", Font.PLAIN, textSize));
			arrivalL = new JLabel("Arrival Location:");
			arrivalL.setFont(new Font("Serif", Font.PLAIN, textSize));
		    departureD = new JLabel("Departure Date (mm/dd/yyyy):");
			departureD.setFont(new Font("Serif", Font.PLAIN, textSize));
			departureT = new JLabel("Departure Time (xx:xx military time):");
			departureT.setFont(new Font("Serif", Font.PLAIN, textSize));
		    plane = new JLabel("Plane ID (1-50):");
			plane.setFont(new Font("Serif", Font.PLAIN, textSize));
			
		    departureLoc = new JTextField(15);
		    arrivalLoc = new JTextField(15);	     	
		    departureDat = new JTextField(15);
		    departureTim = new JTextField(15);
		    planeID = new JTextField(15);
		        
		    JTextField[] rightitems = { departureLoc, arrivalLoc, departureDat, departureTim, planeID };
		        
		    for(int i = 0; i < rightitems.length; i++) {
		    	rightitems[i].setAlignmentX(Component.LEFT_ALIGNMENT);
		    	rightitems[i].setAlignmentY(Component.TOP_ALIGNMENT);
		        rightcard1.add(rightitems[i]);
		        rightcard1.add(Box.createRigidArea(new Dimension(0, 9)));
		    }
		        
		    JLabel[] leftitems = { departureL, arrivalL, departureD, departureT, plane };
		        
		    leftcard1.add(Box.createRigidArea(new Dimension(0,6)));
		    
		    for(int i = 0; i < leftitems.length; i++) {
		       	leftitems[i].setAlignmentX(Component.LEFT_ALIGNMENT);
		       	leftitems[i].setAlignmentY(Component.BOTTOM_ALIGNMENT);
		       	leftcard1.add(leftitems[i]);
		       	leftcard1.add(Box.createRigidArea(new Dimension(0, 18)));
		    }
	        
	        leftcard1.add(createButton);
//	        rightcard1.add(clearButton);
	        
//	        JButton invis = new JButton();  
//	        rightcard1.add(invis);
	        
	        rightcard1.add(Box.createRigidArea(new Dimension(0, 32)));
	        
//	        Company.getAllFlights().add(new Flight(1, "New york", "new York", "11/23/2024", "11:25", Company.getPlaneInfo(1)));
	 
	        l = new EditableTable(Company.getAllFlights());
	        flights = new JTable(l);
	        
	        card2 = new JPanel();
	        card2.setLayout(new BoxLayout(card2, BoxLayout.Y_AXIS));
	        
	        scrollPane = new JScrollPane(flights);
//	        flights.setFillsViewportHeight(true);
	        card2.add(scrollPane);
	        showInfoButton.setVerticalAlignment(SwingConstants.BOTTOM);
	        card2.add(showInfoButton);

	        JPanel createFlight = new JPanel();
	        createFlight.add(leftcard1);
	        createFlight.add(rightcard1);
	 
	        //JSeparator jsp = new JSeparator(JSeparator.VERTICAL);  
	        //createFlight.add(jsp);
	        
	        tabbedPane.addTab(CREATEFLIGHTS, createFlight);
	        tabbedPane.addTab(VIEWFLIGHTS, card2);
	 
	        pane.add(tabbedPane, BorderLayout.CENTER);
	    }
	
	private class CreateButtonListener implements ActionListener {										
									
		public void actionPerformed(ActionEvent e) {
			
			try {		
				
				String departureLocation = departureLoc.getText();
				String arrivalLocation = arrivalLoc.getText();
				
				departureLocation = Flight.tidyLoc(departureLocation);
				arrivalLocation = Flight.tidyLoc(arrivalLocation);
				
				if(departureLocation.equals(arrivalLocation))
					throw new Exception("Departure and arrival location cannot match");
				
				String departureDate = "";
				String departureTime = "";
				int planeid = Integer.parseInt(planeID.getText());
			
				if(Flight.isValidDate(departureDat.getText()))
					departureDate = departureDat.getText();
				else
					throw new InvalidDateException();

				if(Flight.isValidTime(departureTim.getText()))
					departureTime = departureTim.getText();
				else
					throw new InvalidTimeException();
			
				if(Company.getPlaneInfo(planeid).getAvailability() == false)
					throw new Exception("Plane is unavailable");
					
				if(departureLocation.equals("") || arrivalLocation.equals("") || departureDate.equals("") || departureTime.equals("") || planeID.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Please fill all fields before clicking \"create\"");
				else  {	
					Company.createFlight(departureLocation, arrivalLocation, departureDate, departureTime, Company.getPlaneInfo(planeid));
					ammendDoc(Company.getAllFlights().get(Company.getAllFlights().size()-1));
					updateTab();
					
					departureLoc.setText("");
					arrivalLoc.setText("");
					departureDat.setText("");
					departureTim.setText("");
					planeID.setText("");
				}
				
			} catch(InvalidTimeException it) {
				JOptionPane.showMessageDialog(null, "Please enter a valid time (xx:xx military)");
			} catch(InvalidDateException id) {
				JOptionPane.showMessageDialog(null, "Please enter a valid date (mm/dd/yyyy)");
			} catch(Exception ex) {
//				JOptionPane.showMessageDialog(null, "Please pick an available plane");
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}

			//JOptionPane.showMessageDialog(null, "e.getActionCommand() returns " +
			//		e.getActionCommand());
			//JOptionPane.showMessageDialog(null, "e.getSource() returns " +
			//		e.getSource());
											
		}	

	}
	
	private class ShowInfoButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			String allInfo = "";
			Flight f = 	l.getFlightList().get(flights.getSelectedRow());

			allInfo = "STAFF: " + Arrays.toString(f.getPlane().getStaff()) + "\nCAPACITY: " + f.getPlane().getCapacity();
			
			JOptionPane.showMessageDialog(null, allInfo);

			
		}
	
	}
	
	public static void ammendDoc(Flight f) {
				
		try {
			
			FileWriter fw = new FileWriter("Flights.txt", true);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println(f.toString());
			
			pw.close();
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void updateTab() {
		card2.removeAll();
		
		EditableTable l = new EditableTable(Company.getAllFlights());
        flights = new JTable(l);
        
        scrollPane = new JScrollPane(flights);
        card2.add(scrollPane);
        showInfoButton.setVerticalAlignment(SwingConstants.BOTTOM);
        card2.add(showInfoButton);
	}	
	
	public static void main(String[] args) throws InvalidTimeException, Exception {
		FlightTracker f = new FlightTracker();
	}
	
}

class EditableTable extends AbstractTableModel {
	
    private final ArrayList<Flight> flightList;
     
    private final String[] columnNames = new String[] { "Flight ID", "Departure Location", "Arrival Location", "Departure Date", "Departure Time", "Plane ID"};
    private final Class[] columnClass = new Class[] { Integer.class, String.class, String.class, String.class, String.class, Integer.class };
 
    public EditableTable(ArrayList<Flight> flightList){
        this.flightList = flightList;
    }
     
    public String getColumnName(int column) {
        return columnNames[column];
    }
 
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }
 
    public int getColumnCount() {
        return columnNames.length;
    }
 
    public int getRowCount() {
        return flightList.size();
    }
    
    public ArrayList<Flight> getFlightList(){
    	return flightList;
    }
 
    public Object getValueAt(int rowIndex, int columnIndex) {
        Flight row = flightList.get(rowIndex);
        if(0 == columnIndex) {
            return row.getFlightID();
        }
        else if(1 == columnIndex) {
            return row.getDeparture();
        }
        else if(2 == columnIndex) {
            return row.getArrival();
        }
        else if(3 == columnIndex) {
            return row.getDepartureDate();
        } else if(4 == columnIndex) {
        	return row.getDepartureTime();
        } else if(5 == columnIndex) {
        	return row.getPlaneID();
        }
        return null;
    }
    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Flight row = flightList.get(rowIndex);
        
        try {
        	if(0 == columnIndex) {
        	}
        	else if(1 == columnIndex) {
        		row.setDeparture((String) aValue);
        	}
        	else if(2 == columnIndex) {
        		row.setArrival((String) aValue);
        	}
        	else if(3 == columnIndex) {
        		row.setDepartureDate((String) aValue);
        	} else if(4 == columnIndex) {
        		row.setDepartureTime((String) aValue);
        	} else if(5 == columnIndex) {
        		row.setPlane(Company.getPlaneInfo((Integer)aValue));
        	}
        } catch(InvalidDateException d) {
			JOptionPane.showMessageDialog(null, "Please enter a valid date (mm/dd/yyyy)");
        } catch(InvalidTimeException t){
			JOptionPane.showMessageDialog(null, "Please enter a valid time (xx:xx military)");
        } catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
    
}

//private void buildFrame()
//{
//
//	createButton = new JButton("Create");
//	clearButton = new JButton("Clear");
//
//	//add an action listener to the button. 			
//	//The argument is an object of the action listener class	
//							
//	CreateButtonListener createListener = new CreateButtonListener();			
//	createButton.addActionListener(createListener);	
//	
//	ClearButtonListener clearListener = new ClearButtonListener();
//	clearButton.addActionListener(clearListener);
//	
////	frameLayout = new SpringLayout();
////	setLayout(frameLayout);
////	contentPane = getContentPane();
////	
////	buttonPanelLayout = new SpringLayout();
////	flightPanelLayout = new SpringLayout();
//
////	flightTracker = new JPanel();							//initialize different panels
////	flightTracker.setPreferredSize(new Dimension(100, 100));
//	
//	buttonPane = new JPanel();
//    buttonPane.setPreferredSize(new Dimension(100, 100));
//    
//    String[] labels = {"Departure Location: ", "Arrival Location: ", "Departure Time: ", "Arrival Time: ", "Plane ID: "};
//    
//	//Create and populate the panel.
////	for (int i = 0; i < labels.length; i++) {
////		JLabel label = new JLabel(labels[i], JLabel.TRAILING);
////		flightTracker.add(label);
////		JTextField textField = new JTextField(15);
////    	label.setLabelFor(textField);
////    	flightTracker.add(textField);
////	}	
//	
//	//Lay out the panel.
////	SpringUtilities.makeCompactGrid(flightTracker, labels.length, 2, 6, 6, 5, 5);
//
//    //Add the buttons.
//    buttonPane.add(clearButton);
//    buttonPane.add(createButton);
//    
////    add(flightTracker);
////    add(buttonPane);
//
//    //Lay out the buttons in one row and as many columns
//    //as necessary, with 6 pixels of padding all around.
////    SpringUtilities.makeCompactGrid(buttonPane, 1, buttonPane.getComponentCount(), 6, 6, 15, 15);
////	
////    //flightPanelLayout.putConstraint(SpringLayout.NORTH, contentPane,30,SpringLayout.NORTH, createButton);
////    //flightPanelLayout.putConstraint(SpringLayout.WEST, contentPane,30,SpringLayout.WEST , createButton);
////    buttonPanelLayout.putConstraint(SpringLayout.NORTH, contentPane,30,SpringLayout.NORTH, clearButton);
////    buttonPanelLayout.putConstraint(SpringLayout.WEST, contentPane,30,SpringLayout.WEST , createButton);
////	
////	SpringLayout.Constraints c = frameLayout.getConstraints(flightTracker);
////    	c.setWidth(Spring.constant(200));
////        c.setHeight(Spring.constant(100));
////        
////    frameLayout.putConstraint(SpringLayout.NORTH, flightTracker,30,SpringLayout.NORTH, contentPane);
////    frameLayout.putConstraint(SpringLayout.WEST, flightTracker,30,SpringLayout.WEST , contentPane);
//	
//}

//public class ClearButtonListener implements ActionListener {
//
//public void actionPerforemed(ActionEvent e) {
//	
//	departureLoc.setText("");
//	arrivalLoc.setText("");
//	departureTim.setText("");
//	arrivalTim.setText("");
//	planeID.setText("");
//	
//}
//
//}

