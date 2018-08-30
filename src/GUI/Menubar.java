package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import model.Customer;
import controller.Controller;

public class Menubar implements ActionListener {
	private JMenu fileMenu ;
	private JMenu editMenu ;
	private JMenu entryMenu ;
	private JMenuItem newItem ;
	private JMenuItem updateItem ;
	private JMenuItem exitItem ;
	private JMenuItem serverItem ;
	private JMenuItem newDbItem ;
	private JMenuItem exportItem ;
	private JMenuItem removeItem ;
	private JMenuItem detailsItem ;
	private JMenuItem undoItem ;
	private JMenuItem printItem ;
	
	private FormListener formListener ;
	private String menuItem ;
	
	protected JMenuBar createMenuBar(){
		menuItem = null ;
		JMenuBar menuBar = new JMenuBar() ;
		
		fileMenu = new JMenu("File") ;
		editMenu = new JMenu("Edit") ;
		entryMenu = new JMenu("Entry") ;
		
		exitItem = new JMenuItem("Exit") ;
		newItem = new JMenuItem("New Entry...") ;
		updateItem = new JMenuItem("Update Entry...") ;
		serverItem = new JMenuItem("Server Info...") ;
		newDbItem = new JMenuItem("Create Database") ;
		exportItem = new JMenuItem("Export Search...") ;
		removeItem = new JMenuItem("Delete Entry") ;
		detailsItem = new JMenuItem("View Details...") ;
		undoItem = new JMenuItem("Undo Delete") ;
		printItem = new JMenuItem("Print") ;
		
		menuBar.add(fileMenu) ;
		menuBar.add(editMenu) ;
		menuBar.add(entryMenu) ;
		fileMenu.add(printItem) ;
		fileMenu.addSeparator();
		fileMenu.add(exportItem) ;
		fileMenu.add(newDbItem) ;
		fileMenu.addSeparator();
		fileMenu.add(exitItem) ;
		editMenu.add(undoItem) ;
		editMenu.add(serverItem) ;
		entryMenu.add(newItem) ;
		entryMenu.add(updateItem) ;
		entryMenu.add(detailsItem) ;
		entryMenu.addSeparator();
		entryMenu.add(removeItem) ;
		 
		
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		editMenu.setMnemonic(KeyEvent.VK_E);
		entryMenu.setMnemonic(KeyEvent.VK_N);
		
		
		serverItem.addActionListener(this);
		updateItem.addActionListener(this) ;
		newItem.addActionListener(this);
		exitItem.addActionListener(this);
		newDbItem.addActionListener(this);
		exportItem.addActionListener(this);
		removeItem.addActionListener(this);
		detailsItem.addActionListener(this);
		undoItem.addActionListener(this);
		printItem.addActionListener(this);
		
		undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		return menuBar ;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem clicked = (JMenuItem)e.getSource() ;
		if(clicked == newItem){
			menuItem = "new" ;
		}
		if(clicked == updateItem){
			menuItem = "update" ;
		}
		if(clicked == serverItem){
			menuItem = "server" ;
		}
		if(clicked == newDbItem){
			menuItem = "newDB" ;
		}
		if(clicked == exportItem){
			menuItem = "export" ;
		}
		if(clicked == removeItem){
			menuItem = "remove" ;
		}
		if(clicked == detailsItem){
			menuItem = "details" ;
		}
		if(clicked == undoItem){
			menuItem = "undo" ;
		}
		if(clicked == printItem){
			menuItem = "print" ;
		}
		if(clicked == exitItem){
			System.exit(0) ;
		}
		
		FormEvent ev = new FormEvent(this) ;
		
		if(formListener != null){
			formListener.formEventOccurred(ev);
		}
	}
	
	public String getMenuItem() {
		return menuItem;
	}

	public void setFormListener(FormListener formListener) {
		this.formListener = formListener ;
	}

	
}
