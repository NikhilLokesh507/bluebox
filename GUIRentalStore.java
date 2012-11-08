package package1;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**********************************************************************
GUIRentalStore.java implements a 'RedBox'-like program in which a user
can rent a DVD or Game for a certain amount of time and then return it
to either pay the rental fee or an overage fee if kept beyond the due
date.

@author Joe Gibson, Ryan Zondervan, Josh Edgcombe, Seth Culp
@version 10/31/2012
 *********************************************************************/
public class GUIRentalStore extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	/** The main panel */
	private ImagePanel panel;

	/** The main menu bar */
	private JMenuBar menuBar;

	/** The file menu */
	private JMenu fileMenu;

	/** The action menu */
	private JMenu actionMenu;

	/** The open menu */
	private JMenu open;

	/** The save menu */
	private JMenu save;

	/** The rent menu */
	private JMenu rent;

	/** The serializable open menu item */
	private JMenuItem openSerial;

	/** The serializable save menu item */
	private JMenuItem saveSerial;

	/** The text file open menu item */
	private JMenuItem openText;

	/** The text file save menu item */
	private JMenuItem saveText;

	/** The quit menu item */
	private JMenuItem quit;

	/** The rent DVD menu item */
	private JMenuItem rentDVD;

	/** The rent Game menu item */
	private JMenuItem rentGame;

	/** The return DVD/Game menu item */
	private JMenuItem returnItem;

	/** The search menu item */
	private JMenuItem searchItem;
	
	/** The late menu item */
	private JMenuItem lateItem;
	
	/** The sort menu item */
	private JMenuItem sortDate;

	/** The main list presented to the user */
	private JList list;

	/** The brains behind the list */
	private ListEngine engine;
	
	/** File chooser */
	private JFileChooser fc;


	/******************************************************************
	Constructs a GUIRentalStore by initializing all GUI components and
	assigning key-stroke shorcuts to all menu items.
	@param none
	******************************************************************/
	public GUIRentalStore() {

		panel = new ImagePanel(new ImageIcon("bg.png").getImage());
		panel.setPreferredSize(new Dimension(1280, 800));

		list = new JList();

		engine = new ListEngine();

		list = new JList(engine);

		//RoundedLineBorder border = new RoundedLineBorder(Color.BLACK, 10);
		//ImagePanel listPanel = new ImagePanel(new ImageIcon("bg.png").getImage());
		//listPanel.setPreferredSize(new Dimension(1280, 800));
		//listPanel.setBorder(border);
		//listPanel.setPreferredSize(new Dimension(600, 400));
		list.setPreferredSize(new Dimension(600, 400));
		//list.setBorder(border);
		list.setFont(new Font("Century Gothic", Font.PLAIN, 16));

		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		actionMenu = new JMenu("Action");
		open = new JMenu("Open");
		save = new JMenu("Save");
		sortDate = new JMenuItem("Sort");
		openSerial = new JMenuItem("Serial");
		saveSerial = new JMenuItem("Serial");
		openText = new JMenuItem("Text File");
		saveText = new JMenuItem("Text File");
		quit = new JMenuItem("Quit");
		rent = new JMenu("Rent");
		rentDVD = new JMenuItem("DVD");
		rentGame = new JMenuItem("Game");
		returnItem = new JMenuItem("Return");
		searchItem = new JMenuItem("Search");
		lateItem = new JMenuItem("Late Titles");

		openSerial.addActionListener(this);
		saveSerial.addActionListener(this);
		openText.addActionListener(this);
		saveText.addActionListener(this);
		sortDate.addActionListener(this);
		quit.addActionListener(this);
		rentDVD.addActionListener(this);
		rentGame.addActionListener(this);
		returnItem.addActionListener(this);
		searchItem.addActionListener(this);
		lateItem.addActionListener(this);

		menuBar.add(fileMenu);
		menuBar.add(actionMenu);
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(quit);
		fileMenu.add(sortDate);
		actionMenu.add(rent);
		actionMenu.add(returnItem);
		actionMenu.add(searchItem);
		actionMenu.add(lateItem);
		open.add(openSerial);
		open.add(openText);
		save.add(saveSerial);
		save.add(saveText);
		rent.add(rentDVD);
		rent.add(rentGame);

		//Create and assign shortcuts to menus
		KeyStroke openSerialSC = KeyStroke.getKeyStroke(KeyEvent.VK_O,
				KeyEvent.CTRL_DOWN_MASK);
		KeyStroke saveSerialSC = KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.CTRL_DOWN_MASK);
		KeyStroke openTextSC = KeyStroke.getKeyStroke(KeyEvent.VK_O, 
				KeyEvent.SHIFT_DOWN_MASK);
		KeyStroke saveTextSC = KeyStroke.getKeyStroke(KeyEvent.VK_S, 
				KeyEvent.SHIFT_DOWN_MASK);
		KeyStroke quitSC = KeyStroke.getKeyStroke(KeyEvent.VK_Q, 
				KeyEvent.CTRL_DOWN_MASK);
		KeyStroke rentDVDSC = KeyStroke.getKeyStroke(KeyEvent.VK_D, 
				KeyEvent.CTRL_DOWN_MASK);
		KeyStroke rentGameSC = KeyStroke.getKeyStroke(KeyEvent.VK_G, 
				KeyEvent.CTRL_DOWN_MASK);
		KeyStroke returnItemSC = KeyStroke.getKeyStroke(KeyEvent.VK_R, 
				KeyEvent.CTRL_DOWN_MASK);

		openSerial.setAccelerator(openSerialSC);
		saveSerial.setAccelerator(saveSerialSC);
		openText.setAccelerator(openTextSC);
		saveText.setAccelerator(saveTextSC);
		quit.setAccelerator(quitSC);
		rentDVD.setAccelerator(rentDVDSC);
		rentGame.setAccelerator(rentGameSC);
		returnItem.setAccelerator(returnItemSC);

		//Set the Frame Title to "BlueBox"
		this.setTitle("BlueBox");
		this.setJMenuBar(menuBar);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.setOpaque(true);
		panel.setLayout(new BorderLayout());

		ImageIcon northByNorthwestIcon = new ImageIcon("northByNorthwest.jpeg");
		Image img = northByNorthwestIcon.getImage() ;  
		Image newimg = img.getScaledInstance( 140, 210,  java.awt.Image.SCALE_SMOOTH ) ;  
		northByNorthwestIcon = new ImageIcon(newimg);
		JLabel northByNorthwest = new JLabel(northByNorthwestIcon);

		ImageIcon rearWindowIcon = new ImageIcon("rearWindow.jpeg");
		img = rearWindowIcon.getImage() ;  
		newimg = img.getScaledInstance( 140, 210,  java.awt.Image.SCALE_SMOOTH ) ;  
		rearWindowIcon = new ImageIcon(newimg);
		JLabel rearWindow = new JLabel(rearWindowIcon);

		ImageIcon drStrangeloveIcon = new ImageIcon("drStrangelove.jpeg");
		img = drStrangeloveIcon.getImage() ;  
		newimg = img.getScaledInstance( 140, 210,  java.awt.Image.SCALE_SMOOTH ) ;  
		drStrangeloveIcon = new ImageIcon(newimg);
		JLabel drStrangelove = new JLabel(drStrangeloveIcon);

		ImageIcon theGodfatherIcon = new ImageIcon("theGodfather.jpeg");
		img = theGodfatherIcon.getImage() ;  
		newimg = img.getScaledInstance( 140, 210,  java.awt.Image.SCALE_SMOOTH ) ;  
		theGodfatherIcon = new ImageIcon(newimg);
		JLabel theGodfather = new JLabel(theGodfatherIcon);

		ImageIcon fightClubIcon = new ImageIcon("fightClub.jpeg");
		img = fightClubIcon.getImage() ;  
		newimg = img.getScaledInstance( 140, 210,  java.awt.Image.SCALE_SMOOTH ) ;  
		fightClubIcon = new ImageIcon(newimg);
		JLabel fightClub = new JLabel(fightClubIcon);

		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(1280, 80));
		topPanel.setOpaque(false);

		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(200, 800));
		leftPanel.setOpaque(false);

		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(200, 800));
		rightPanel.setOpaque(false);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(1280, 320));
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

		JButton leftButton = new JButton(new ImageIcon("leftButton.png"));
		leftButton.setPreferredSize(new Dimension(80, 80));

		JButton rightButton = new JButton(new ImageIcon("rightButton.png"));
		rightButton.setPreferredSize(new Dimension(80, 80));

		bottomPanel.add(Box.createRigidArea(new Dimension(40,0)));
		bottomPanel.add(leftButton);
		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(rearWindow);
		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(northByNorthwest);
		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(drStrangelove);
		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(theGodfather);
		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(fightClub);
		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(rightButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(40,0)));

		panel.add(topPanel, BorderLayout.PAGE_START);
		panel.add(bottomPanel, BorderLayout.PAGE_END);
		panel.add(leftPanel, BorderLayout.LINE_START);
		panel.add(rightPanel, BorderLayout.LINE_END);

		JScrollPane scrollPane = new JScrollPane(list);
		panel.add(scrollPane, BorderLayout.CENTER);

		this.getContentPane().add(panel);
		setVisible(true);
		setResizable(false);
		setSize(1280, 800);
	}


	/******************************************************************
	The main program method. Creates a new GUIRentalStore and sets the
	menu bar to be displayed using the OS menu bar.
	@param Array of string arguments
	@return none
	******************************************************************/
	public static void main(String[] args) {
		//Use the OS X menu bar in place of the default Java menu bar
		System.setProperty("apple.laf.useScreenMenuBar", "true");

		//Set the application name to "Blue-Box"
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", 
				"Blue-Box");

		@SuppressWarnings("unused")
		GUIRentalStore grs = new GUIRentalStore();
	}

	/******************************************************************
	Overrides the actionPerfom method to act on menu item selections.
	@param The action event
	@return none
	******************************************************************/
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == quit) {
			System.exit(0);
		}
		
		if (event.getSource() == sortDate) {
			engine.sortDateDue();
		}

		// save elements
		if (event.getSource() == saveSerial || 
				event.getSource() == saveText) {
			try {
				fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = fc.showSaveDialog(null);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, 
							"No File Chosen!");
				} else {
					if (event.getSource() == saveText) {
						String file = fc.getSelectedFile().getPath();
						engine.saveString(file);
					} else {
						String file = fc.getSelectedFile().getPath();
						engine.saveSerial(file);
					}
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}

		// load elements
		if (event.getSource() == openSerial || 
				event.getSource() == openText) {
			engine.clear();
			try {
				fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = fc.showOpenDialog(null);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, 
							"No File Chosen!");
				} else {
					if (event.getSource() == openSerial) {
						String file = fc.getSelectedFile().getPath();
						engine.loadSerial(file);
					} else {
						String file = fc.getSelectedFile().getPath();
						engine.loadString(file);
					}
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}

		if(event.getSource() == rentDVD) {
			DVD dvd = new DVD();
			DialogRentDVD dvdDialog = new DialogRentDVD(this, dvd);

			if(dvdDialog.getCloseStatus() == DialogRentDVD.OK)
				engine.add(dvd);
		}

		if(event.getSource() == rentGame) {
			Game game = new Game();
			DialogRentGame gameDialog = new DialogRentGame(this, game);

			if(gameDialog.getCloseStatus() == DialogRentGame.OK)
				engine.add(game);
		}

		if(event.getSource() == returnItem) {

			//Get the index of the selected item
			int index = list.getSelectedIndex();

			//Check for out of bounds indexes
			if(index < 0)

				//Alert the user of an out of bounds index
				JOptionPane.showMessageDialog(null, 
						"Please select an item from the list");

			else {

				//Initialize a calendar for todays date to compare 
				//to the due date
				Calendar today = new GregorianCalendar();

				Calendar dueDate = new GregorianCalendar();

				dueDate.setTime(engine.getDVD(index).getDateDue().getTime());

				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

				try {
					today.setTime(dateFormat.parse(DateFormat.
							getDateInstance(DateFormat.SHORT).
							format(today.getTime())));
				} catch (ParseException e) {

				}

				double cost = 0.0;
				double lateFee = 0.0;

				if(!engine.getDVD(index).isGame()) {
					cost = 1.2;
					lateFee = 0.0;

					//If the title is late
					if(today.after(dueDate))
						lateFee = 2.0;
				}
				else {
					cost = 5.0;
					lateFee = 0.0;

					//If the title is late
					if(today.after(dueDate))
						lateFee = 10.0;
				}

				String thankYouString = "Thank you, " + 
				engine.getDVD(index).getCustomerName()
						+ ", for renting " + engine.
						getDVD(index).getTitle() + ".\n";

				DecimalFormat df = new DecimalFormat("#0.00");

				String costString = "Rental cost: $" + 
						df.format(cost) + "\n";
				String lateFeeString = "Late fee: $" + 
						df.format(lateFee) + "\n";
				String totalCostString = "Total cost: $" + 
						df.format(cost + lateFee) + "\n";

				JOptionPane.showMessageDialog(null, thankYouString +
						costString + lateFeeString + totalCostString);

				//Remove the unit from the database
				engine.remove(index);
			}
		}

		if(event.getSource() == searchItem) {
			@SuppressWarnings("unused")
			SearchDialog sd = new SearchDialog(engine.getList());
		}
		
		if(event.getSource() == lateItem) {
			@SuppressWarnings("unused")
			LateDialog ld = new LateDialog(engine.getList());
		}
	}

}

@SuppressWarnings("serial")
class ImagePanel extends JPanel {

	private Image img;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), 
				img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
		repaint();
	}

}
