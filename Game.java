package package1;

import java.text.ParseException;

/**********************************************************************
Game.java implements a game unit having parameters such as title,
customer name, console type, as well as the date when it was 
purchased and the date when it is due.

@author Joe Gibson, Ryan Zondervan, Josh Edgcombe, Seth Culp
@version 10/31/2012
**********************************************************************/
public class Game extends DVD {

	private static final long serialVersionUID = 1L;
	
	/** The type of console */
	private Console consoleType;

	/******************************************************************
	Constructs a blank Game
	@param none
	******************************************************************/
	public Game() {
		super();
		consoleType = null;
	}
	
	/******************************************************************
	Constructs a Game with Strings as parameters.
	@throws ParseException 
	@param Date purchased
	@param Date due
	@param Title of unit
	@param Customer's name
	@param Console type
	******************************************************************/
	public Game(String datePurchased, String dateDue, String title, 
			String customerName, String console) throws ParseException {
		super(datePurchased, dateDue, title, customerName);
		this.consoleType = Console.valueOf(console);
	}
	
	/******************************************************************
	Returns the Game's console type.
	@param none
	@return Game's console type
	******************************************************************/
	public Console getConsole() {
		return consoleType;
	}

	
	/******************************************************************
	Sets the Game's console type.
	@param Game's console type
	@return none
	******************************************************************/
	public void setConsole(Console consoleType) {
		this.consoleType = consoleType;
	}
	
	
	/******************************************************************
	Returns whether or not the DVD is a Game
	@param none
	@return If the DVD is a game (true)
	******************************************************************/
	public boolean isGame() {
		return true;
	}
}
