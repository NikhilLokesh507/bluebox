package package1;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.AbstractListModel;

/**********************************************************************
ListEngine.java implements the abstract list model for the GUI's JList.
This includes printing to the list, adding, removing, and sorting the
list. All manipulation is done through the list engine.

@author Joe Gibson, Ryan Zondervan, Josh Edgcombe, Seth Culp
@version 10/31/2012
 *********************************************************************/
@SuppressWarnings("serial")
public class ListEngine extends AbstractListModel {
	
	/** The list of dvds/games */
	private ArrayList<DVD> listDVDs;

	
	/******************************************************************
	Constructs a blank ListEngine and initializes the dvd list.
	@param none
	******************************************************************/
	public ListEngine() {
		listDVDs = new ArrayList<DVD>();
	}
	
	/******************************************************************
	Returns the unit at the specified index.
	@param index of unit
	@return unit at specified index
	******************************************************************/
	public DVD getDVD(int index) {
		return listDVDs.get(index);
	}

	
	/******************************************************************
	Returns the element at the specified index. Used by the 
	fireInterval...() methods.
	@param index of element
	@return element at specified index.
	******************************************************************/
	public Object getElementAt(int index) {
		
		//Given a valid index...
		if (index >= 0 && index < listDVDs.size()) {
			
			//Parse the purchase date into a string
			String dateP = DateFormat.getDateInstance(DateFormat.SHORT).format(
					listDVDs.get(index).getDatePurchased().getTime());
			
			//Parse the due date into a string
			String dateD = DateFormat.getDateInstance(DateFormat.SHORT).format(
					listDVDs.get(index).getDateDue().getTime());

			//Creates the output string for a DVD
			String s = "Name: " + listDVDs.get(index).getCustomerName()
					+ " Title: " + listDVDs.get(index).getTitle()
					+ " Date Purchased: " + dateP + " Date Due: " + dateD;

			//Parses the console type for game and output the string
			if (listDVDs.get(index).isGame()) {
				Game game = (Game) listDVDs.get(index);
				String consoleString = "";

				if (game.getConsole() == Console.Xbox360)
					consoleString = "Xbox 360";

				if (game.getConsole() == Console.Xbox720)
					consoleString = "Xbox 720";

				if (game.getConsole() == Console.PS3)
					consoleString = "PS3";

				if (game.getConsole() == Console.PS2)
					consoleString = "PS2";

				if (game.getConsole() == Console.GameCube)
					consoleString = "GameCube";

				return s + " Console: " + consoleString;
			}

			//Just return the dvd string
			else
				return s;
		} 
		
		//Dont return anything if the index is out of bounds
		else {
			return null;
		}
	}

	
	/******************************************************************
	Returns the size of the dvd list
	@param none
	@return size of the dvd list
	******************************************************************/
	public int getSize() {
		return listDVDs.size();
	}

	
	/******************************************************************
	Adds a new unit to the list
	@param new unit
	@return completion status
	******************************************************************/
	public boolean add(DVD dvd) {
		listDVDs.add(dvd);
		fireIntervalAdded(this, 0, listDVDs.size()-1);
		return true;
	}

	
	/******************************************************************
	Removes the unit at the specified index
	@param index of unit
	@return completion status
	******************************************************************/
	public boolean remove(int index) {
		listDVDs.remove(index);
		fireIntervalRemoved(this, 0, listDVDs.size()-1);
		return true;
	}

	
	/******************************************************************
	Updates the list
	@param unit
	@return completion status
	******************************************************************/
	public boolean update(DVD dvd) {
		fireContentsChanged(this, 0, listDVDs.size()-1);
		return true;
	}

	
	/******************************************************************
	Saves the serializable object to the file at the given filepath
	@param path of file
	@return completion status
	******************************************************************/
	public boolean saveSerial(String filepath) {
		
		//Attempt to Save the file
		try {
			FileOutputStream fos = new FileOutputStream(filepath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			//Write each object to the file
			for (DVD dvd : listDVDs) {
				oos.writeObject(dvd);
			}
			
			//Close the object output stream
			oos.close();
		}
		
		catch(Exception e)
		{
			return false;
		}
		
		//Completed successfully
		return true;
	}
	
	
	/******************************************************************
	Loads the serializable object from the file at the given filepath
	@param path of file
	@return completion status
	******************************************************************/
	public boolean loadSerial(String filepath) {
		
		//Create a new DVD
		DVD dvd;

		//Attempt to load the file
		try {
			FileInputStream fis = new FileInputStream(filepath);
			ObjectInputStream ois = new ObjectInputStream(fis);

			dvd = (DVD) ois.readObject();
			
			//Read in all units
			while (dvd != null) {
				add(dvd);
				dvd = (DVD) ois.readObject();
			}

			//Close the object input stream
			ois.close();
			
		} catch (EOFException e) {
			
			//Completed successfully
			return true;
		}

		catch (Exception e) {
			return false;
		}

		// This should never be reached...
		return false;
	}
	
	
	/******************************************************************
	Saves the text object to the file at the given filepath
	@param path of file
	@return completion status
	******************************************************************/
	public boolean saveString(String filepath) {
		File savefile = new File(filepath);
		FileWriter savefilewriter;
		
		//Attempt to create the file writer
		try {
			savefilewriter = new FileWriter(savefile);
		} catch (IOException e1) {
			return false;
		}
		
		String retval = "";
		
		//Print the unit data
		for(DVD dvd1 : listDVDs) {
			
			//For a game
			if(dvd1.isGame()) {
				retval += DateFormat.getDateInstance(DateFormat.SHORT).
						format(dvd1.getDatePurchased().getTime()) + ",";
				retval += DateFormat.getDateInstance(DateFormat.SHORT).
						format(dvd1.getDateDue().getTime()) + ",";
				retval += dvd1.getTitle() + ",";
				retval += dvd1.getCustomerName() + ",";
				retval += ((Game) dvd1).getConsole();		
			}
			
			//For a dvd
			else
			{
				retval += DateFormat.getDateInstance(DateFormat.SHORT).
						format(dvd1.getDatePurchased().getTime()) + ",";
				retval += DateFormat.getDateInstance(DateFormat.SHORT).
						format(dvd1.getDateDue().getTime()) + ",";
				retval += dvd1.getTitle() + ",";
				retval += dvd1.getCustomerName();
			}
			
			//Add the new line character
			retval += "\n";
			
		}
		
		//Attempt to write to the file and close it
		try {
			savefilewriter.write(retval);
			savefilewriter.close();
		}
		catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	
	/******************************************************************
	Loads the text object from the file at the given filepath
	@param path of file
	@return completion status
	******************************************************************/
	public boolean loadString(String filepath) {
		File loadfile;
		Scanner filein;
		
		//Attempt to create the scanner
		try {
			loadfile = new File(filepath);
			filein = new Scanner(loadfile);
		}
		catch(Exception e) {
			return false;
		}
		
		String line;
		String properties[];

		//If there is another entry...
		while(filein.hasNextLine()) {
			
			//Read the line
			line = filein.nextLine();
			
			//Delimit the line
			properties = line.split(",");

			//Parse and create a dvd
			if(properties.length == 4) {
				try {
					add(new DVD(properties[0],properties[1],
							properties[2],properties[3]));
				}
				catch(Exception e)
				{
					return false;
				}
			}
			
			//Parse and create a game
			else if(properties.length == 5) {
				try {
					add(new Game(properties[0],properties[1],
							properties[2],properties[3],properties[4]));
				}
				catch(Exception e)
				{
					return false;
				}
			}
		}
		
		//Close the file
		filein.close();
		
		//Completed successfully
		return true;
	}
	
	
	/******************************************************************
	Returns the dvd list
	@param none
	@return the dvd list
	******************************************************************/
	public ArrayList<DVD> getList() {
		return listDVDs;
	}
	
	
	/******************************************************************
	Clears the dvd list
	@param none
	@return none
	******************************************************************/
	public void clear() {
		listDVDs.clear();
	}

	
	/******************************************************************
	Sorts the dvd list and displays the sorted list
	@param none
	@return none
	******************************************************************/
	public void sortDateDue() {
		fireContentsChanged(this,0,listDVDs.size() - 1);
		Collections.sort(listDVDs);
	}
}