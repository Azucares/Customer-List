package GUI;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import controller.Controller;
import model.Customer;

public class Mainframe extends JFrame {
	private TextPanel output ;
	private SearchForm search ;
	private TablePanel table ;
	private Controller controller ;
	private DatabaseDialog dbPrefs ;
	private Preferences prefs ;
	private ArrayList<Customer> custList ;
	private Menubar menubar ;
	private DetailsDialog detailsDialog ;
	private JFileChooser exportWindow ;
	private Customer wasDeleted ;
	private ArrayList<Customer> deletedEntries ;
	private PrintUIWindow printWindow ;
	private WebLinks webLinks;
	
	public Mainframe(){
		super("Customer Data Interface") ;
		
		setFont("Times", Font.PLAIN, 15);	
		output = new TextPanel() ;
		search = new SearchForm() ;
		table = new TablePanel() ;
		controller = new Controller() ;
		dbPrefs = new DatabaseDialog(this) ;
		
		menubar = new Menubar() ;
		detailsDialog = new DetailsDialog(this, "Submit a new entry") ;
		exportWindow = new JFileChooser() ;
		wasDeleted = null ;
		webLinks = new WebLinks(this) ;
		
		deletedEntries = new ArrayList<Customer>() ;
		
		printWindow = new PrintUIWindow(this) ;
		
		getRootPane().setDefaultButton(search.getDefaultButton());
		
	//Processes actions from the search window
				
		search.setFormListener(new FormListener(){
			public void formEventOccurred(FormEvent e){
				
				if(search.getStartLinks()){
					webLinks.setVisible(true);
					search.setStartLinks(false);
				}
				else{
					try {
					custList = controller.searchList(e.getId(), e.getCompany(), e.getCity(), e.getMake(), e.getModel(), e.getSerial(), e.isMaint(), e.getInstalledFrom(), e.getInstalledTo(), e.isActive(), e.getAtRemoteSearch(), prefs.get("host", ""), prefs.get("db", ""), prefs.getInt("port", 3306), getUser(), getPassword()) ;
					table.setData(custList);
					table.refresh() ;
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Failed to process the search", "Could not perform search", JOptionPane.ERROR_MESSAGE);
				}
				}
				
			}
		}) ;
		
	//Processes actions from the links window
		
		webLinks.setFormListener(new FormListener(){

			@Override
			public void formEventOccurred(FormEvent event) {
				webLinks.revalidate();
			}
		});
		
	//Processes actions from the results table
		
		table.setFormListener(new FormListener(){
			@Override
			public void formEventOccurred(FormEvent e) {
				if(e.getTableFunction().equals("update")){
					detailsDialog.setTitle("Update entry") ;
					detailsDialog.setDialogFunction("update") ;
					detailsDialog.updateCalled(custList.get(e.getKey())) ;
					detailsDialog.clearDetails();
					detailsDialog.setVisible(true) ;
				}
				if(e.getTableFunction().equals("remove")){
					try{
						deletedEntries.add(custList.get(table.getRow())) ;
						controller.removeEntry(custList.get(table.getRow()).getId(), prefs.get("host", ""), prefs.get("db", ""), prefs.getInt("port", 3306), getUser(), getPassword());
						custList = controller.searchList(prefs.get("host", ""), prefs.get("db", ""), prefs.getInt("port", 3306), getUser(), getPassword()) ;
						table.setData(custList);
						table.refresh() ;
					}catch(Exception f){
						JOptionPane.showMessageDialog(null, "Failed to remove the entry", "Could not delete entry", JOptionPane.ERROR_MESSAGE);
					}					
				}
				if(e.getTableFunction().equals("details")){
					try{
						detailsDialog.detailsView();
						detailsDialog.setTitle("View Details");
						detailsDialog.setDialogFunction("details");
						detailsDialog.updateCalled(custList.get(table.getRow()));
						detailsDialog.setVisible(true);
					}catch(Exception f){
						JOptionPane.showMessageDialog(null, "Could not open details window", "Could not open details window", JOptionPane.ERROR_MESSAGE);
					}					
				}
				if(e.getTableFunction().equals("copySerial")){
					try{
						StringSelection stringSelection = new StringSelection(custList.get(table.getRow()).getSerial());
						Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
						clpbrd.setContents(stringSelection, null);
					}catch(Exception f){
						JOptionPane.showMessageDialog(null, "Could not access clipboard", "Could not copy entry", JOptionPane.ERROR_MESSAGE);
					}	
				}
			}			
		});
		
	//Processes actions from the detailed information dialog
		
		detailsDialog.setFormListener(new FormListener(){
			public void formEventOccurred(FormEvent e){
				if(detailsDialog.isPrint()){
					printWindow.setVisible(true);
					printWindow.setCustInfo(detailsDialog.getCustBeingDisplayed());
					printWindow.setOpacity(0);
					PrinterJob pjob = PrinterJob.getPrinterJob();
					PageFormat preformat = pjob.defaultPage();
					preformat.setOrientation(PageFormat.PORTRAIT);
					PageFormat postformat = pjob.pageDialog(preformat);
					//If user does not hit cancel then print.
					if (preformat != postformat) {
					   //Set print component
					    pjob.setPrintable(new Printer(printWindow), postformat);
					    if (pjob.printDialog()) {
					        try {
								pjob.print();
							} catch (PrinterException e1) {
								e1.printStackTrace();
							}
					    }
					}
					printWindow.setVisible(false);
					detailsDialog.setPrint(false);
				}
				if(e.getDialogFunction() == null){
					return ;
				}
				if(e.getDialogFunction().equals("new")){
					try{
						controller.addEntry(e.getId(), e.getCompany(), e.getAddress(), e.getCity(), e.getZip(), e.getContact(), e.getPhone(), e.getMake(), e.getModel(), e.getSerial(), e.isMaint(), e.getInstall(), e.getRemoved(), e.isActive(), e.getSupplyFile(), e.getNotes(), e.isAtRemoteUpdate(), prefs.get("host", ""), prefs.get("db", ""), prefs.getInt("port", 3306), getUser(), getPassword()) ;
						detailsDialog.setVisible(false) ;
					}catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e.toString(), "Could not add new entry", JOptionPane.ERROR_MESSAGE);
					}
				}
				if(e.getDialogFunction().equals("update")){
					
					try{
						controller.updateEntry(e.getId(), e.getCompany(), e.getAddress(), e.getCity(), e.getZip(), e.getContact(), e.getPhone(), e.getMake(), e.getModel(), e.getSerial(), e.isMaint(), e.getInstall(), e.getRemoved(), e.isActive(), e.getSupplyFile(), e.getNotes(), e.isAtRemoteUpdate(), custList.get(table.getRow()).getId(), prefs.get("host", ""), prefs.get("db", ""), prefs.getInt("port", 3306), getUser(), getPassword()) ;
						custList = controller.searchList(prefs.get("host", ""), prefs.get("db", ""), prefs.getInt("port", 3306), getUser(), getPassword()) ;
						table.setData(custList);
						table.refresh() ;
						detailsDialog.setVisible(false) ;
					}catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Failed to update entry", "Could not update the entry", JOptionPane.ERROR_MESSAGE);
					}
				}
				if(e.getDialogFunction().equals("details")){
					detailsDialog.detailsView();
				}
			}
		});
		
	//Processes actions from the menu bar
		
		menubar.setFormListener(new FormListener(){
			@Override
			public void formEventOccurred(FormEvent e) {
				
				String menuItem = menubar.getMenuItem() ;
				
				if(menuItem == "update"){
					if(custList.size() > 0){
						detailsDialog.clearDetails() ;
						detailsDialog.setTitle("Update entry");
						detailsDialog.setDialogFunction("update");
						detailsDialog.updateCalled(custList.get(table.getRow()));
						detailsDialog.setVisible(true);
					}
				}
				if(menuItem == "new"){
					detailsDialog.clearDetails() ;
					detailsDialog.setTitle("Add new entry");
					detailsDialog.setDialogFunction("new");
					detailsDialog.clearFields();
					detailsDialog.setVisible(true) ;
				}
				if(menuItem == "server"){
					dbPrefs.setVisible(true);
				}
				if(menuItem == "newDB"){
					try {
						controller.newDatabase(prefs.get("host", ""), prefs.get("db", ""), prefs.getInt("port", 3306), getUser(), getPassword());
					} catch (Exception e1) {
						e1.printStackTrace();
					}	
				}
				if(menuItem == "export"){
					exportWindow.showSaveDialog(Mainframe.this) ;

					File file = exportWindow.getSelectedFile() ;
					try(BufferedWriter br = new BufferedWriter(new FileWriter(file))){
						String[] titles = {"aoaID", "Customer", "Address", "City", "Zip Code", "Contact", "Phone", "Make", "Model", "Serial Number", "Maintenance", "Installed", "Removed", "Active", "Supply file", "atRemote", "Notes"} ;
						for(int i = 0; i < titles.length; i++){
							br.write(titles[i] + ",");
						}
						br.newLine();
						for(int i = 0; i < custList.size(); i++){
							br.write(custList.get(i).toString());
							br.newLine();
						}
						
					}catch(IOException writeError){
						JOptionPane.showMessageDialog(null, "Could not access file", "Could not access file", JOptionPane.ERROR_MESSAGE);
					}					
				}
				if(menuItem == "details"){
					if(custList.size() > 0){
						detailsDialog.detailsView();
						detailsDialog.setTitle("View Details");
						detailsDialog.setDialogFunction("details");
						detailsDialog.updateCalled(custList.get(table.getRow()));
						detailsDialog.setVisible(true);	
					}					
				}
				if(menuItem == "remove"){
					try{
						deletedEntries.add(custList.get(table.getRow())) ;
						controller.removeEntry(custList.get(table.getRow()).getId(), prefs.get("host", ""), prefs.get("db", ""), prefs.getInt("port", 3306), getUser(), getPassword());
						custList = controller.searchList(prefs.get("host", ""), prefs.get("db", ""), prefs.getInt("port", 3306), getUser(), getPassword()) ;
						table.setData(custList);
						table.refresh() ;
					}catch(Exception f){
						JOptionPane.showMessageDialog(null, "Failed to remove the entry", "Could not delete entry", JOptionPane.ERROR_MESSAGE);
					}	
				}
				if(menuItem == "undo"){
					if(deletedEntries.size() > 0){
						try{
							wasDeleted = deletedEntries.get(deletedEntries.size() - 1) ;
							deletedEntries.remove(deletedEntries.size() - 1) ;
							controller.addEntry(wasDeleted.getAoaId(), wasDeleted.getCompName(), wasDeleted.getAddress(), wasDeleted.getCity(), wasDeleted.getZip(), wasDeleted.getContact(), wasDeleted.getPhone(), wasDeleted.getMake(), wasDeleted.getModel(), wasDeleted.getSerial(), wasDeleted.isMaintenance(), wasDeleted.getInstallDate(), wasDeleted.getRemoveDate(), wasDeleted.isActive(), wasDeleted.getSupplyFile(), wasDeleted.getNotes(), wasDeleted.isAtRemote(), prefs.get("host", ""), prefs.get("db", ""), prefs.getInt("port", 3306), getUser(), getPassword()) ;
							custList = controller.searchList(prefs.get("host", ""), prefs.get("db", ""), prefs.getInt("port", 3306), getUser(), getPassword()) ;
							table.setData(custList);
							table.refresh() ;
							
							wasDeleted = null ;
						}catch(Exception undoError){
							JOptionPane.showMessageDialog(null, "Undo failed", "Could not recreate entry", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				if(menuItem == "print"){
					try {
						table.printTable() ;
					} catch (PrinterException e1) {
						System.out.println("could not be printed");
					}
				}
			}			
		});
		
		prefs = Preferences.userRoot().node("custList") ;
		
		//sets a listener to save changes to the database server info to preferences
		dbPrefs.setprefsListener(new PrefsListener(){
			@Override
			public void preferencesSet(String serverName, String user, String password, String dbName, Integer port) {
				prefs.put("host", serverName);
				prefs.put("user", user);
				prefs.put("password", password);
				prefs.put("db", dbName);
				prefs.putInt("port", port);				
			}			
		}) ;
				
		setDefaultPrefs() ;				
		setLayout(new BorderLayout()) ;
		
		setJMenuBar(menubar.createMenuBar()) ;
		
		add(table, BorderLayout.CENTER) ;
		add(search, BorderLayout.WEST) ;
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setSize(1000, 700) ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		pack() ;
		setVisible(true) ;
	}
	
	//sets the preferences dialog to reflect the default values
	public void setDefaultPrefs(){
		String hostName = prefs.get("host", "") ;
		String userName = prefs.get("user", "") ;
		String password = prefs.get("password", "") ;
		String dbName = prefs.get("db", "") ;
		int port = prefs.getInt("port", 3306) ;
		dbPrefs.setDefaults(hostName, userName, password, dbName, port);
	}
	
	
	public void setFont(String fontName, int fontStyle, int fontSize){
		//set font defaults to increase font size
		try{
			Font font = new Font(fontName, fontStyle, fontSize) ;
			
			//put this font in the defaults table for every ui font resource key
			Hashtable defaults = UIManager.getDefaults() ;
			Enumeration keys = defaults.keys() ;
			while(keys.hasMoreElements()){
				Object key = keys.nextElement() ;
				if((key instanceof String) && (((String)key).endsWith(".font"))){
					defaults.put(key, font) ;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getUser(){
		return prefs.get("user", "") ;
	}
	public String getPassword(){
		return  prefs.get("password", "");
	}
}

