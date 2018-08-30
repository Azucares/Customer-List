package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import model.Customer;

public class DetailsDialog extends JDialog{
	private JPanel machinePanel ;
	private JPanel customerPanel ;
	private JPanel infoPanel ;
	private JPanel infoPanelTop ;
	private JPanel infoPanelMiddle ;
	private JPanel infoPanelBottom ;
	private JPanel machinePanelMiddle ;
	private JPanel machinePanelBottom ;
	
	private JPanel formPanel ;
	private JLabel IDLabel ;
	private JTextArea IDField ;
	private JLabel companyLabel ;
	private JTextArea companyField ;
	private JLabel addressLabel ;
	private JTextArea addressField ;
	private JLabel cityLabel ;
	private JTextArea cityField ;
	private JLabel zipLabel ;
	private JTextArea zipField ;
	private JLabel contactLabel ;
	private JTextArea contactField ;
	private JLabel phoneLabel ;
	private JTextArea phoneField ;
	private JLabel makeLabel ;
	private JTextArea makeField ;
	private JLabel modelLabel ;
	private JTextArea modelField ;
	private JLabel serialLabel ;
	private JTextArea serialField ;
	private JLabel maintLabel ;
	private JCheckBox maintField ;
	private JLabel installLabel ;
	private JTextArea installField ;
	private JLabel removedLabel ;
	private JTextArea removedField ;
	private JLabel activeLabel ;
	private JCheckBox activeField ;
	private JLabel notesLabel ;
	private JTextArea notesField ;
	private JLabel supplyFileLabel ;
	private JTextArea supplyFileField ;
	private JButton openSupplies ;
	private JCheckBox atRemoteField ;
	private JLabel atRemoteLabel ;
	
	private JButton okButton ;
	private JButton cancelButton ;
	private JButton printButton ;
	
	private FormListener formListener ;
	private String dialogFunction ;
	private boolean print ;
	private String supplyFileName ;
	private Customer customerBeingDisplayed ;
	AbstractAction transferFocus ;
	
	public DetailsDialog(JFrame parent, String title){
		super(parent, title) ;
		formPanel = new JPanel() ;
		customerPanel = new JPanel() ;
		machinePanel = new JPanel() ;
		infoPanel = new JPanel() ;
		infoPanelTop = new JPanel() ;
		infoPanelMiddle = new JPanel() ;
		infoPanelBottom = new JPanel() ;
		machinePanelMiddle = new JPanel() ;
		machinePanelBottom = new JPanel() ;
		
		transferFocus = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				((Component) e.getSource()).transferFocus();
			}
		};
		
		IDLabel = new JLabel("AOA ID: ", SwingConstants.RIGHT) ;
		IDField = new JTextArea(3, 15) ;
		textArea(IDField) ;
		companyLabel = new JLabel("Customer: ", SwingConstants.RIGHT) ;
		companyField = new JTextArea(3, 15) ;
		textArea(companyField) ;
		addressLabel = new JLabel("Address: ", SwingConstants.RIGHT) ;
		addressField = new JTextArea(3, 15) ;
		textArea(addressField) ;
		cityLabel = new JLabel("City: ", SwingConstants.RIGHT) ;
		cityField = new JTextArea(3, 15) ;
		textArea(cityField) ;
		zipLabel = new JLabel("Zip Code: ", SwingConstants.RIGHT) ;
		zipField = new JTextArea(3, 15) ;
		textArea(zipField) ;
		contactLabel = new JLabel("Contact Name: ", SwingConstants.RIGHT) ;
		contactField = new JTextArea(3, 15) ;
		textArea(contactField) ;
		phoneLabel = new JLabel("Phone Number: ", SwingConstants.RIGHT) ;
		phoneField = new JTextArea(3, 15) ;
		textArea(phoneField) ;
		makeLabel = new JLabel("Make: ", SwingConstants.RIGHT) ;
		makeField = new JTextArea(3, 15) ;
		textArea(makeField) ;
		modelLabel = new JLabel("Model: ", SwingConstants.RIGHT) ;
		modelField = new JTextArea(3, 15) ;
		textArea(modelField) ;
		serialLabel = new JLabel("Serial Number: ", SwingConstants.RIGHT) ;
		serialField = new JTextArea(3, 15) ;
		textArea(serialField) ;
		maintField = new JCheckBox("Maintenence") ;
		maintField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "transferFocus");
		maintField.getActionMap().put("transferFocus", transferFocus);
		installLabel = new JLabel("Date Installed: ", SwingConstants.RIGHT) ;
		installField = new JTextArea(3, 15) ;
		textArea(installField) ;
		removedLabel = new JLabel("Date Removed: ", SwingConstants.RIGHT) ;
		removedField = new JTextArea(3, 15) ;
		textArea(removedField) ;
		activeField = new JCheckBox("Current location") ;
		activeField.setSelected(true);
		activeField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "transferFocus");
		activeField.getActionMap().put("transferFocus", transferFocus);
		notesLabel = new JLabel("Additional Notes") ;
		notesField = new JTextArea(3, 15) ;
		textArea(notesField) ;
		atRemoteField = new JCheckBox("@Remote enabled") ;
		atRemoteField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "transferFocus");
		atRemoteField.getActionMap().put("transferFocus", transferFocus);
		supplyFileLabel = new JLabel("Supply File: ", SwingConstants.RIGHT) ;
		supplyFileField = new JTextArea(3, 15) ;
		textArea(supplyFileField) ;
		
		openSupplies = new JButton("Supplies") ;
		okButton = new JButton("OK") ;
		cancelButton = new JButton("Cancel") ;
		printButton = new JButton("Print") ;
		dialogFunction = null ;
				
		
		
		okButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = 0 ;
				String company = "" ;
				String address = "" ;
				String city = "" ;
				int zip = 0 ;
				String contact = "" ;
				String phone = "" ;
				String make = "" ;
				String model = "" ;
				String serial = "" ;
				boolean maint = false ;
				String installed = "" ;
				String removed = "" ;
				boolean isActive = true ;
				String supplyFile = "" ;
				String notes = "" ;
				boolean atRemote = false ;
				
				try{
					if(IDField.getText().length() > 0)
						id = Integer.parseInt(IDField.getText()) ;
					if(companyField.getText().length() > 0)
						company = companyField.getText() ;
					if(addressField.getText().length() > 0)
						address = addressField.getText() ;
					if(cityField.getText().length() > 0)
						city = cityField.getText() ;
					if(zipField.getText().length() > 0)
						zip = Integer.parseInt(zipField.getText()) ;
					if(contactField.getText().length() > 0)
						contact = contactField.getText() ;
					if(phoneField.getText().length() > 0)
						phone = phoneField.getText();
					if(makeField.getText().length() > 0)
						make = makeField.getText() ;
					if(modelField.getText().length() > 0)
						model = modelField.getText() ;
					if(serialField.getText().length() > 0)
						serial = serialField.getText() ;
					maint = maintField.isSelected() ;
					if(installField.getText().length() > 0)
						installed = installField.getText() ;
					if(removedField.getText().length() > 0)
						removed = removedField.getText() ;
					isActive = activeField.isSelected() ;
					if(supplyFileField.getText().length() > 0){
						supplyFile = supplyFileField.getText() ;
					}
					if(notesField.getText().length() > 0)
						notes = notesField.getText() ;
					atRemote = atRemoteField.isSelected() ;
					
					FormEvent ev = new FormEvent(this, dialogFunction, id, company, address, city, zip, contact, phone, make, model, serial, maint, installed, removed, isActive, supplyFile, notes, atRemote) ;
				
					if(formListener != null){
						formListener.formEventOccurred(ev);
					}
					}catch(Exception error){
					}
			}			
		}) ;
		
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false) ;
			}			
		});
		
		openSupplies.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				     if (Desktop.isDesktopSupported()) {
				    	String fileName = "\\\\AOA1\\Meter\\" + supplyFileName + ".123" ;
				    	File file = new File(fileName) ;				    	 
				    	Desktop.getDesktop().open(new File(fileName));
				    					       
				     }
				   } catch (Exception ioe) {
					   JOptionPane.showMessageDialog(null, "Supply file not found on server", "Could not open file", JOptionPane.ERROR_MESSAGE);
				  }
			}			
		});
		
		printButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				print = true ;
				
				FormEvent ev = new FormEvent(this, dialogFunction, 1) ;
				
				if(formListener != null){
					formListener.formEventOccurred(ev);
				}
			}			
		});
		setLayout(new BorderLayout()) ;
		add(machinePanel, BorderLayout.NORTH) ;
		add(customerPanel, BorderLayout.CENTER) ;
		add(infoPanel, BorderLayout.SOUTH) ;
		
		Dimension machineSize = new Dimension() ;
		Dimension customerSize = new Dimension() ;
		Dimension infoSize = new Dimension() ;
		Dimension dateSize = new Dimension() ;
		machineSize.height = 150 ;
		
		
		Border innerBorder = BorderFactory.createTitledBorder("Machine Information") ;
		Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10) ;
		Border combinedBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder) ;
		machinePanel.setBorder(BorderFactory.createCompoundBorder(combinedBorder, outerBorder));
		machinePanel.setPreferredSize(machineSize);
		
		machinePanel.setLayout(new BorderLayout()) ;
		machinePanel.add(machinePanelMiddle, BorderLayout.CENTER) ;
		machinePanel.add(machinePanelBottom, BorderLayout.SOUTH) ;
		
		machinePanelMiddle.setLayout(new GridLayout(2, 2, 10, 10));
		machinePanelMiddle.add(IDLabel) ;
		machinePanelMiddle.add(IDField) ;
		machinePanelMiddle.add(makeLabel) ;
		machinePanelMiddle.add(makeField) ;
		machinePanelMiddle.add(serialLabel) ;
		machinePanelMiddle.add(serialField) ;
		machinePanelMiddle.add(modelLabel) ;
		machinePanelMiddle.add(modelField) ;
		
		machinePanelBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));
		
		machinePanelBottom.add(atRemoteField);
		machinePanelBottom.add(new JLabel()) ;
		machinePanelBottom.add(maintField) ;
		machinePanelBottom.add(new JLabel()) ;
		machinePanelBottom.add(activeField) ;
		
		
		innerBorder = BorderFactory.createTitledBorder("Customer Information") ;
		outerBorder = BorderFactory.createEmptyBorder(5, 10, 0, 10) ;
		combinedBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder) ;
		customerPanel.setBorder(BorderFactory.createCompoundBorder(combinedBorder, outerBorder));
		customerPanel.setLayout(new GridLayout(3, 2, 10, 10));		
		customerPanel.add(companyLabel) ;
		customerPanel.add(companyField) ;		
		customerPanel.add(addressLabel) ;
		customerPanel.add(addressField) ;
		customerPanel.add(cityLabel) ;
		customerPanel.add(cityField) ;
		customerPanel.add(zipLabel) ;
		customerPanel.add(zipField) ;
		customerPanel.add(contactLabel) ;
		customerPanel.add(contactField) ;
		customerPanel.add(phoneLabel) ;
		customerPanel.add(phoneField) ;
		
		innerBorder = BorderFactory.createTitledBorder("Other Information") ;
		outerBorder = BorderFactory.createEmptyBorder(5, 10, 0, 10) ;
		combinedBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder) ;
		infoPanel.setBorder(BorderFactory.createCompoundBorder(combinedBorder, outerBorder));
		infoSize.height = 250 ;
		dateSize.height = 20 ;
		infoPanel.setPreferredSize(infoSize);
		infoPanelTop.setPreferredSize(dateSize) ;
		
		infoPanel.setLayout(new BorderLayout());
		infoPanelTop.setLayout(new GridLayout(1, 2, 10, 10)) ;
		infoPanelMiddle.setLayout(new BoxLayout(infoPanelMiddle, BoxLayout.Y_AXIS)) ;
		infoPanelBottom.setLayout(new FlowLayout(FlowLayout.TRAILING));
		infoPanel.add(infoPanelTop, BorderLayout.NORTH);
		infoPanel.add(infoPanelMiddle, BorderLayout.CENTER);
		infoPanel.add(infoPanelBottom, BorderLayout.SOUTH) ;
		infoPanelTop.add(installLabel) ;
		infoPanelTop.add(installField) ;
		infoPanelTop.add(removedLabel) ;
		infoPanelTop.add(removedField) ;
		infoPanelTop.add(supplyFileLabel) ;
		infoPanelTop.add(supplyFileField) ;
		infoPanelMiddle.add(notesLabel) ;
		infoPanelMiddle.add(notesField) ;
		infoPanelBottom.add(printButton);
		infoPanelBottom.add(cancelButton) ;
		infoPanelBottom.add(okButton) ;		
		
		setResizable(false) ;
		setSize(800, 600) ;
		setLocationRelativeTo(parent) ;

	}
	
	public void setDialogFunction(String dialogFunction) {
		this.dialogFunction = dialogFunction;
	}


	private void textArea(JTextArea textArea){
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "transferFocus");
		textArea.getActionMap().put("transferFocus", transferFocus);

	}
	public void setFormListener(FormListener formListener) {
		this.formListener = formListener ;
	}
	
	public void clearFields(){
		IDField.setText("");
		companyField.setText("");
		addressField.setText("");
		cityField.setText("");
		zipField.setText("");
		contactField.setText("");
		phoneField.setText("");
		makeField.setText("");
		modelField.setText("");
		serialField.setText("");
		maintField.setSelected(false);
		installField.setText("");
		removedField.setText("");
		activeField.setSelected(false);
		supplyFileField.setText("");
		notesField.setText("");
		atRemoteField.setSelected(false);
	}
	
	public void detailsView(){
		IDField.setEditable(false);
		companyField.setEditable(false);
		addressField.setEditable(false);
		cityField.setEditable(false);
		zipField.setEditable(false);
		contactField.setEditable(false);
		phoneField.setEditable(false);
		makeField.setEditable(false);
		modelField.setEditable(false);
		serialField.setEditable(false);
		maintField.setEnabled(false);
		installField.setEditable(false);
		removedField.setEditable(false);
		activeField.setEnabled(false);
		atRemoteField.setEnabled(false);
		supplyFileField.setEditable(false);
		notesField.setEditable(false);
		infoPanelTop.remove(supplyFileLabel) ;
		infoPanelTop.remove(supplyFileField) ;
		infoPanelTop.add(openSupplies) ;
	}
	
	public void clearDetails(){
		IDField.setEditable(true);
		companyField.setEditable(true);
		addressField.setEditable(true);
		cityField.setEditable(true);
		zipField.setEditable(true);
		contactField.setEditable(true);
		phoneField.setEditable(true);
		makeField.setEditable(true);
		modelField.setEditable(true);
		serialField.setEditable(true);
		maintField.setEnabled(true);
		installField.setEditable(true);
		removedField.setEditable(true);
		activeField.setEnabled(true);
		atRemoteField.setEnabled(true);
		supplyFileField.setEditable(true);
		notesField.setEditable(true);
		infoPanelTop.add(supplyFileLabel) ;
		infoPanelTop.add(supplyFileField) ;
		infoPanelTop.remove(openSupplies) ;
	}
	
	public void updateCalled(Customer customer) {
		customerBeingDisplayed = customer ;
		IDField.setText (Integer.toString(customer.getAoaId()));
		companyField.setText(customer.getCompName());
		addressField.setText(customer.getAddress());
		cityField.setText(customer.getCity());
		zipField.setText(Integer.toString(customer.getZip()));
		contactField.setText(customer.getContact());
		phoneField.setText(customer.getPhone());
		makeField.setText(customer.getMake()) ;
		modelField.setText(customer.getModel());
		serialField.setText(customer.getSerial());
		maintField.setSelected(customer.isMaintenance());
		installField.setText(customer.getInstallDate());
		removedField.setText(customer.getRemoveDate());
		activeField.setSelected(customer.isActive());
		supplyFileField.setText(customer.getSupplyFile());
		supplyFileName = customer.getSupplyFile() ;
		notesField.setText(customer.getNotes());
		atRemoteField.setSelected(customer.isAtRemote());
	}

	public boolean isPrint() {
		return print;
	}

	public Customer getCustBeingDisplayed(){
		return customerBeingDisplayed ;
	}
	
	public void setPrint(boolean print) {
		this.print = print;
	}
	
}
