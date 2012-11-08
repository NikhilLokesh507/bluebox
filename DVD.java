package package1;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**********************************************************************
DVD.java implements a DVD unit having parameters such as title,
customer name, as well as the date when it was purchased and the
date when it is due.

@author Joe Gibson, Ryan Zondervan, Josh Edgcombe, Seth Culp
@version 10/31/2012
**********************************************************************/
public class DVD implements Serializable, Comparable<DVD>{
	
	private static final long serialVersionUID = 1L;
	
	/** The date the DVD was rented */
	protected GregorianCalendar datePurchased;
	
	/** The date the DVD is due back */
	protected GregorianCalendar dateDue;
	
	/** The title of the DVD */
	protected String title;
	
	/** The name of the person who is renting the DVD */
	protected String customerName; 

	/******************************************************************
	Constructs a blank DVD
	@param none
	******************************************************************/
	public DVD() {
		datePurchased = new GregorianCalendar();
		dateDue = new GregorianCalendar();
		title = "";
		customerName = "";
	}
	
	/******************************************************************
	Constructs a DVD class given each of the dvd's parameters
	@throws ParseException 
	@param Date purchased
	@param Date due
	@param Title of unit
	@param Customer's name
	******************************************************************/
	public DVD(String datePurchased, String dateDue, String title, 
			String customerName) throws ParseException {
		this.datePurchased = new GregorianCalendar();
		this.dateDue = new GregorianCalendar();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
		
		this.datePurchased.setTime(dateFormat.parse(datePurchased));
		this.dateDue.setTime(dateFormat.parse(dateDue));
		
		this.title = title;
		this.customerName = customerName;
	}

	
	/******************************************************************
	Returns the date the DVD was purchased.
	@param none
	@return Date on which the DVD was purchased
	******************************************************************/
	public GregorianCalendar getDatePurchased() {
		return datePurchased;
	}
	

	/******************************************************************
	Sets the date the DVD was purchased.
	@param The date on which the DVD was purchased
	@return none
	******************************************************************/
	public void setDatePurchased(GregorianCalendar datePurchased) {
		this.datePurchased = datePurchased;
	}
	

	/******************************************************************
	Returns the date the DVD is due.
	@param none
	@return Date on which the DVD is due
	******************************************************************/
	public GregorianCalendar getDateDue() {
		return dateDue;
	}
	

	/******************************************************************
	Sets the date the DVD is due.
	@param The date on which the DVD is due
	@return none
	******************************************************************/
	public void setDateDue(GregorianCalendar dateDue) {
		this.dateDue = dateDue;
	}

	
	/******************************************************************
	Returns the DVD title.
	@param none
	@return DVD's title
	******************************************************************/
	public String getTitle() {
		return title;
	}
	

	/******************************************************************
	Sets the DVD title.
	@param DVD's title
	@return none
	******************************************************************/
	public void setTitle(String title) {
		this.title = title;
	}

	
	/******************************************************************
	Returns the customer's name.
	@param none
	@return Customer's name
	******************************************************************/
	public String getCustomerName() {
		return customerName;
	}

	
	/******************************************************************
	Sets the customer's name.
	@param Customer's name
	@return none
	******************************************************************/
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	/******************************************************************
	Returns whether or not the DVD is a Game
	@param none
	@return If the DVD is a game (false)
	******************************************************************/
	public boolean isGame() {
		return false;
	}

	/******************************************************************
	CompareTo method for the date due, used in sorting the ArrayList
	@param none
	@return result of the comparison of the dates of two objects
	******************************************************************/
	public int compareTo(DVD dvd) {
		return dateDue.compareTo(dvd.getDateDue());
	}	
}
