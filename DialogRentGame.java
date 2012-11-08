package package1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**********************************************************************
DialogRentGame.java implements a pop-up dialog box in which the user
enters all the information about the item they are renting. The dialog
alerts users of empty fields and checks for proper date formatting
before the user is allowed to proceed.

@author Joe Gibson, Ryan Zondervan, Josh Edgcombe, Seth Culp
@version 10/31/2012
**********************************************************************/
@SuppressWarnings("serial")
public class DialogRentGame extends JDialog implements ActionListener {
	
	/** Main panel */
	JPanel panel = new JPanel();

	/** Customer name input */
	private JTextField customerName;
	
	/** Title input */
	private JTextField title;
	
	/** Purchase date input */
	private JTextField datePurchased;
	
	/** Due date label */
	private JLabel dateDue;
	
	/** Due date input */
	private JTextField dateDueT;
	
	/** Rental type input */
	private JComboBox rentalType;
	
	/** Console type */
	private JComboBox consoleType;

	/** 'OK' Button */
	private JButton okButton;
	
	/** 'Cancel' Button */
	private JButton cancelButton;

	/** Status of the dialog box upon close */
	private int closeStatus;

	/** OK close status */
	public static final int OK = 0;
	
	/** Cancel close status */
	public static final int CANCEL = 1;
	
	/** Error close status */
	public static final int ERROR = -1;

	/** The console type as a string */
	private String console;
	
	/** The amount of days the rental is for (1, 3, or 7) */
	private int rentalTime;

	/** The date the item was purchased on */
	private Calendar calendarP;
	
	/** The date the item is due back on */
	private Calendar calendarD;

	/** The actual unit */
	private Game unit;

	/******************************************************************
	Constructs a dialog box given the unit that it will change
	and the parent frame who called it so it can be modal.
	@param Parent frame
	@param Unit that will be updated with the input
	******************************************************************/
	public DialogRentGame(JFrame parent, Game game) {
		
		//Create a modal dialog box by not allowing selection of
		//the parent frame
		super(parent, true);
		
		//Don't exit on close
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		//Set the title
		setTitle("Add Game");
		
		//Set the default close status
		closeStatus = CANCEL;

		//Initialize the unit to the input unit
		unit = game;

		//Set the calendars to today's date
		calendarP = Calendar.getInstance();
		calendarD = Calendar.getInstance();
		
		//Add one day for the due date calendar
		calendarD.add(Calendar.DAY_OF_YEAR, 1);
		
		//Create a grid layout for the text inputs
		panel.setLayout(new GridLayout(8, 2));

		//Add the customer name section
		panel.add(new JLabel("  Customer Name:"));
		customerName = new JTextField(null, 30);
		panel.add(customerName);

		//Add the title section
		panel.add(new JLabel("  Game Title:"));
		title = new JTextField(null, 30);
		panel.add(title);
		
		//Add the console type combo box
		panel.add(new JLabel("  Console Type:"));
		String[] consoleString = {"Xbox 360", "Xbox 720",
				"PS3", "PS2", "GameCube"};
		consoleType = new JComboBox(consoleString);
		panel.add(consoleType);

		//Add the date purchased section
		panel.add(new JLabel("  Date Purchased:"));
		//Initialize to today's date
		datePurchased = new JTextField(DateFormat.getDateInstance(
				DateFormat.SHORT).format(calendarP.getTime()), 30);
		panel.add(datePurchased);

		//Add the rental type combo box
		panel.add(new JLabel("  Rental Type:"));
		String[] rentalString = {"1 Day", "3 Day", "7 Day", "Custom"};
		rentalType = new JComboBox(rentalString);
		panel.add(rentalType);

		//Add the due date label and text field
		panel.add(new JLabel("  Date Due:"));
		//Initialize to tomorrow's date
		dateDue = new JLabel(DateFormat.getDateInstance(
				DateFormat.SHORT).format(calendarD.getTime()));
		dateDueT = new JTextField(DateFormat.getDateInstance(
				DateFormat.SHORT).format(calendarD.getTime()));
		panel.add(dateDue);

		//Add the main panel to the dialog
		getContentPane().add(panel, BorderLayout.CENTER);

		//Initialize the ok and cancel buttons
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		//Create a button panel
		JPanel buttonPanel = new JPanel();

		//Add the ok and cancel buttons
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		//Add the button panel to the panel
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		//Declare a mouse listener
		MouseListener mouseListener = mouseListener();

		//Add action listeners to the buttons and combo boxes
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		consoleType.addActionListener(this);
		rentalType.addActionListener(this);
		
		//Add mouse listeners to the text fields
		datePurchased.addMouseListener(mouseListener);
		customerName.addMouseListener(mouseListener);
		title.addMouseListener(mouseListener);

		//Default values
		rentalTime = 1;
		console = "Xbox 360";

		//Set the size and make it visible
		setSize(300,300);
		setVisible(true);
	}
	
	
	/******************************************************************
	Overrides the actionPerfomed method to act on button presses and
	comboBox selections.
	@param The action event
	@return none
	******************************************************************/
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == okButton) {
			
			//Initialize the close status to OK
			closeStatus = OK;
			
			//Declare two dates for the purchased date and due date
			Date dateP, dateD;

			//Attempt to Parse the input data
			try{
				
				//Check for a valid customer name
				if(customerName.getText().length() > 0)
					unit.setCustomerName(customerName.getText());
				else {
					//Alert the error of bad name input and don't close.
					closeStatus = ERROR;
					JOptionPane.showMessageDialog(null, 
							"Please fill in all information");
					
					//Set the background to pink so the user 
					//knows to fill it in
					customerName.setBackground(Color.PINK);
				}

				//Check for a valid title
				if(title.getText().length() > 0)
					unit.setTitle(title.getText());
				else {
					//Alert the error of bad title input and don't close.
					if(closeStatus != ERROR) {
						closeStatus = ERROR;
						JOptionPane.showMessageDialog(null, 
								"Please fill in all information");
					}
					
					//Set the background to pink so the user knows to fill it in
					title.setBackground(Color.PINK);
				}

				if(closeStatus == OK) {
					
					//Create a date format
					SimpleDateFormat dateFormat = new 
							SimpleDateFormat("MM/dd/yy");
					
					//Parse the purchase date
					dateP = dateFormat.parse(datePurchased.getText());
					GregorianCalendar datePG = new GregorianCalendar();
					datePG.setTime(dateP);
					unit.setDatePurchased(datePG);
					
					//Update the due date based on the purchase date
					updateDueDate();

					//Parse the due date from either JLabel or custom input
					if(rentalTime > 0)
						dateD = dateFormat.parse(dateDue.getText());
					else {
						try {
							dateD = dateFormat.parse(dateDueT.getText());
						} catch(ParseException e) {
							
							//Set the close status to error so the dialog doesn't close
							closeStatus = ERROR;
							
							//Alert user of bad date input and reset to last date
							JOptionPane.showMessageDialog(null, 
									"Please enter in a valid date: mm/dd/yyyy");
							dateDueT.setText(DateFormat.getDateInstance(
									DateFormat.SHORT).format(calendarP.getTime()));
							dateD = new Date();
							
							//Set the background to pink so the user
							//knows that the date is incorrect
							dateDueT.setBackground(Color.PINK);
						}
					}
					
					GregorianCalendar dateDG = new GregorianCalendar();
					dateDG.setTime(dateD);
					
					if(dateDG.before(datePG) || dateDG.equals(datePG)) {
						closeStatus = ERROR;
						
						JOptionPane.showMessageDialog(null, 
								"Due date must be after purchase date!");
						
						dateDueT.setText(DateFormat.getDateInstance(
								DateFormat.SHORT).format(calendarP.getTime()));
						
						dateDueT.setBackground(Color.PINK);
					}
					
					else
						unit.setDateDue(dateDG);

					//Parse the console type
					if(!console.equals(null)) {
						if(console.equals("Xbox 360"))
							unit.setConsole(Console.Xbox360);

						if(console.equals("Xbox 720"))
							unit.setConsole(Console.Xbox720);

						if(console.equals("PS3"))
							unit.setConsole(Console.PS3);

						if(console.equals("PS2"))
							unit.setConsole(Console.PS2);

						if(console.equals("GameCube"))
							unit.setConsole(Console.GameCube);
					}
				}

			} catch(ParseException e) {
				
				//Set the close status to error so the dialog doesn't close
				closeStatus = ERROR;
				
				if(rentalTime > 0)
					//Update the due date
					updateDueDate();
				
				//Alert user of bad date input and reset to last date
				JOptionPane.showMessageDialog(null, 
						"Please enter in a valid date: mm/dd/yyyy");
				datePurchased.setText(DateFormat.getDateInstance(
						DateFormat.SHORT).format(calendarP.getTime()));
				
				//Set the background to pink so the user
				//knows that the date is incorrect
				datePurchased.setBackground(Color.PINK);
			}

			//Dispose of the dialog if appropriate
			if(closeStatus == OK)
				dispose();
		}

		if(event.getSource() == cancelButton) {
			
			//Set the close status to cancel and dispose of the dialog
			closeStatus = CANCEL;
			dispose();
		}

		if(event.getSource() == consoleType) {
			
			//Parse the console type string
			console = (String)consoleType.getSelectedItem();
		}

		if(event.getSource() == rentalType) {
			
			//Parse the rental type string
			String rentalString = (String)rentalType.getSelectedItem();

			//Set the rental time based on the rental string
			if(rentalString.equals("1 Day"))
				rentalTime = 1;
			else if(rentalString.equals("3 Day"))
				rentalTime = 3;
			else if(rentalString.equals("7 Day"))
				rentalTime = 7;
			
			if(rentalString.equals("Custom")) {
				rentalTime = -1;
				panel.remove(dateDue);
				panel.add(dateDueT);
				panel.repaint();
				panel.revalidate();
			}
			
			else if(rentalTime > 0) {
				
				//Update the due date
				updateDueDate();
				
				//Add the JLabel
				panel.remove(dateDueT);
				panel.add(dateDue);
				panel.repaint();
				panel.revalidate();
			}
		}
	}
	
	/******************************************************************
	The listener for mouse events. Updates the status of the text
	fields to represent incorrect or empty input, and updates the due
	date field to correctly display when the date purchased is changed.
	@param The action event
	@return none
	******************************************************************/
	public MouseListener mouseListener() {
		MouseListener m = new MouseListener() {
			
			//Mouse clicked
			public void mouseClicked(MouseEvent event) {
				
				//Change to white if selected
				if(event.getSource() == customerName) {
					customerName.setBackground(Color.WHITE);
				}
				
				//Change to white if selected
				if(event.getSource() == title) {
					title.setBackground(Color.WHITE);
				}
		    }

			//Mouse pressed
		    public void mousePressed(MouseEvent event) {

		    }

		    //Mouse released
		    public void mouseReleased(MouseEvent event) {
		    	
		    	//Update the due date
		    	updateDueDate();
				
		    	//Pink if empty, otherwise white
				if(customerName.getText().length() <= 0)
					customerName.setBackground(Color.PINK);
				else
					customerName.setBackground(Color.WHITE);
				
				//Pink if empty, otherwise white
				if(title.getText().length() <= 0)
					title.setBackground(Color.PINK);
				else
					title.setBackground(Color.WHITE);
		    }

		    //Mouse entered
		    public void mouseEntered(MouseEvent event) {
		    	
		    }

		    //Mouse exited
		    public void mouseExited(MouseEvent event) {
		    	
		    }
		};
		
		//Return the MouseListener
		return m;
	}

	
	/******************************************************************
	Returns the close status so the GUI will know which button was
	clicked prior to closing the dialog.
	@param none
	@return The close status
	******************************************************************/
	public int getCloseStatus() {
		return closeStatus;
	}
	
	
	/******************************************************************
	A private helper method to confine the code needed to update the
	dueDate field when necessary.
	@param none
	@return none
	******************************************************************/
	private void updateDueDate() {
		
		//Create the date format
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
		
		//Attempt to parse the purchase date string
		try {
			calendarD.setTime(dateFormat.parse(datePurchased.getText()));
			
			if(rentalTime > 0)
				calendarD.add(Calendar.DAY_OF_YEAR, rentalTime);
			
		} catch (ParseException e) {
			
			//Alert user of bad date input and reset to last date
			JOptionPane.showMessageDialog(null, 
					"Please enter in a valid date: mm/dd/yyyy");
			datePurchased.setText(DateFormat.getDateInstance(
					DateFormat.SHORT).format(calendarP.getTime()));
			datePurchased.setBackground(Color.PINK);
		}

		//Set the due date text
		dateDue.setText(DateFormat.getDateInstance(DateFormat.SHORT).
				format(calendarD.getTime()));
	}
}
