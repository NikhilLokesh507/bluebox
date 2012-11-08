package package1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**********************************************************************
SearchDialog.java implements a search dialog box in which the user can
search through the list elements by either title, due date, or console
type (for games).  The output updates on each key press, resulting in a
seamless, quick display of results.

@author Joe Gibson, Ryan Zondervan, Josh Edgcombe, Seth Culp
@version 10/31/2012
 *********************************************************************/
@SuppressWarnings("serial")
public class SearchDialog extends JDialog implements ActionListener, KeyListener {

	/** The main panel */
	private JPanel panel;
	
	/** The ok button */
	private JButton okButton;
	
	/** The cancel button */
	private JButton cancelButton;
	
	/** The output area */
	private JTextArea output;
	
	/** The input area */
	private JTextField input;

	/** The dvd list */
	private ArrayList<DVD> list;

	
	/******************************************************************
	Constructs a search dialog given the dvd list. Configures all
	GUI components.
	@param the dvd list
	******************************************************************/
	public SearchDialog(ArrayList<DVD> list) {
		
		//Configure the dvd list
		this.list = list;

		//Configure the JPanel
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(600, 400));

		//Configure the buttons
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		//Configure the JLabel
		JLabel inputLabel = new JLabel("Search: ");

		//Configure the input area
		input = new JTextField(null, 30);
		input.setFocusable(true);

		//Configure the output area
		output = new JTextArea();
		output.setEditable(false);
		output.setPreferredSize(new Dimension(500, 300));

		//Add the label and input area to the panel
		panel.add(inputLabel);
		panel.add(input);
		
		//Add the main panel to the dialog
		getContentPane().add(panel, BorderLayout.CENTER);

		//Create a button panel
		JPanel buttonPanel = new JPanel();

		//Add the ok and cancel buttons
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		//Add the button panel to the panel
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		//Add action listeners to the buttons and combo boxes
		input.addKeyListener(this);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		//Create a scrollpane for the output area
		JScrollPane scrollPane = new JScrollPane(output);
		panel.add(scrollPane);
		
		//Set the size and make it visible
		setSize(600,400);
		setVisible(true);
		
		//Display the initial search results
		updateSearchResults(true);
	}

	/******************************************************************
	If a key is pressed...
	@param the key event
	@return none
	******************************************************************/
	@Override
	public void keyPressed(KeyEvent event) {

	}

	
	/******************************************************************
	If a key is released... Update the search results
	@param the key event
	@return none
	******************************************************************/
	@Override
	public void keyReleased(KeyEvent event) {
			updateSearchResults(false);
	}

	
	/******************************************************************
	If a key is pressed and released...
	@param the key event
	@return none
	******************************************************************/
	@Override
	public void keyTyped(KeyEvent event) {
		
	}

	
	/******************************************************************
	Action performed method for the ok and cancel buttons. Close the
	dialog in either case.
	@param the action event
	@return none
	******************************************************************/
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == okButton)
			dispose();

		if(event.getSource() == cancelButton)
			dispose();
	}

	
	/******************************************************************
	A private helper method to update the search results
	@param if this is the first run
	@return none
	******************************************************************/
	private void updateSearchResults(boolean onStart) {

		//Get the input string
		String inputString = input.getText();

		//Clear the output
		output.setText(null);

		//Create an list of strings representing the date and title
		ArrayList<String> dates = new ArrayList<String>();
		ArrayList<String> titles = new ArrayList<String>();

		//Fill the date and title string for each unit
		for(DVD dvd : list) {
			
			dates.add(DateFormat.getDateInstance(DateFormat.SHORT).
					format(dvd.getDateDue().getTime()));

			//Also add the console type in brackets for games
			if(dvd.isGame()) {
				Game g = (Game)dvd;
				titles.add(g.getTitle() + " [" + String.
						valueOf(g.getConsole()) + "]");
			}
			else
				titles.add(dvd.getTitle());
		}

		for( int i = 0; i < titles.size(); i++) {
			String title = titles.get(i);
			String date = dates.get(i);
			
			if(onStart)
				output.append("[" + date + "]\t" + title + "\n");
			
			//If the search is a match, display the matching data
			else
				if(title.toLowerCase().contains(inputString.toLowerCase()) ||
						date.contains(inputString))
					output.append("[" + date + "]\t" + title + "\n");
		}
	}
}
