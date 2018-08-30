package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Customer;

public class PrintUIWindow extends JFrame{
	private JPanel formPanel ;
	private JPanel topPanel ;
	private JPanel middlePanel ;
	private JPanel lowerPanel ;
	private JLabel idLabel ;
	private JLabel serialLabel ;
	private JLabel makeLabel ;
	private JLabel modelLabel ;
	private JLabel maintLabel ;
	private JLabel activeLabel ;
	private JLabel customerLabel ;
	private JLabel addressLabel ;
	private JLabel cityLabel ;
	private JLabel zipLabel ;
	private JLabel contactLabel ;
	private JLabel phoneLabel ;
	private JLabel installLabel ;
	private JLabel removedLabel ;
	private JLabel notesLabel ;
	private JLabel notesField ;
	private Image img ;
	private JLabel logoLabel ;
	
	public PrintUIWindow(JFrame parent){
		//super(parent) ;
		
		formPanel = new JPanel() ;
		topPanel = new JPanel() ;
		middlePanel = new JPanel() ;
		lowerPanel = new JPanel() ;
		topPanel.setPreferredSize(new Dimension(375, 70));
		formPanel.setLayout(new BorderLayout());
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		idLabel = new JLabel("Id: ") ;
		makeLabel = new JLabel("Make: ") ;
		modelLabel = new JLabel("Model: ") ;
		serialLabel = new JLabel("Serial Number:  ") ;
		maintLabel = new JLabel("Under maintenance:  ") ;
		activeLabel = new JLabel("Currently at this Location:  ") ;
		customerLabel = new JLabel("Customer:  ") ;
		addressLabel = new JLabel("Address:  ") ;
		cityLabel = new JLabel("City:  ") ;
		contactLabel = new JLabel("Contact:  ") ;
		phoneLabel = new JLabel("Phone:  ") ;
		installLabel = new JLabel("Installed on:  ") ;
		removedLabel = new JLabel("Removed on:  ") ;
		notesLabel = new JLabel("Additional notes:") ;
		notesField = new JLabel() ;
		
		logoLabel = new JLabel("Advanced Office Automation") ;
		logoLabel.setFont(new Font("ITC Zapf Chancery", Font.BOLD, 24));
		logoLabel.setForeground(Color.BLUE);
			
		
		componentSetup(idLabel) ;
		componentSetup(makeLabel) ;
		componentSetup(modelLabel) ;
		componentSetup(serialLabel) ;
		componentSetup(maintLabel) ;
		componentSetup(activeLabel) ;
		componentSetup(customerLabel) ;
		componentSetup(addressLabel) ;
		componentSetup(cityLabel) ;
		componentSetup(contactLabel) ;
		componentSetup(phoneLabel) ;
		componentSetup(installLabel) ;
		componentSetup(removedLabel) ;
		componentSetup(notesLabel) ;
		componentSetup(notesField) ;
		
		add(formPanel) ;
		formPanel.add(topPanel, BorderLayout.NORTH);
		formPanel.add(middlePanel, BorderLayout.CENTER) ;
		formPanel.add(lowerPanel, BorderLayout.SOUTH) ;
		
		formPanel.setBackground(Color.WHITE);
		topPanel.setBackground(Color.WHITE);
		middlePanel.setBackground(Color.WHITE);
		lowerPanel.setBackground(Color.WHITE);
		
		
		topPanel.add(logoLabel) ;
		middlePanel.add(idLabel) ;	
		middlePanel.add(makeLabel);
		middlePanel.add(modelLabel) ;
		middlePanel.add(serialLabel) ;
		middlePanel.add(new JLabel(" ")) ;
		middlePanel.add(maintLabel) ;
		middlePanel.add(activeLabel) ;
		middlePanel.add(new JLabel(" ")) ;
		middlePanel.add(customerLabel) ;
		middlePanel.add(addressLabel) ;
		middlePanel.add(cityLabel) ;
		middlePanel.add(contactLabel) ;
		middlePanel.add(phoneLabel) ;
		middlePanel.add(new JLabel(" ")) ;
		middlePanel.add(installLabel) ;
		middlePanel.add(removedLabel) ;
		middlePanel.add(new JLabel(" ")) ;
		middlePanel.add(notesLabel) ;
		middlePanel.add(new JLabel(" ")) ;
		middlePanel.add(notesField) ;
		
		
		setSize(375, 550) ;
		setResizable(false) ;
		setLocationRelativeTo(parent) ;
		setUndecorated(true) ;						
		
	}
	
	public void setCustInfo(Customer customer){
		idLabel.setText("ID:  " + Integer.toString(customer.getAoaId()));
		makeLabel.setText("Make:  " + customer.getMake());
		modelLabel.setText("Model:  " + customer.getModel());
		serialLabel.setText("Serial Number:  " + customer.getSerial());
		maintLabel.setText("Under maintenance:  " + customer.getMaint());
		activeLabel.setText("Currently at this Location:  " + customer.getCurrentLoc());
		customerLabel.setText("<html>Customer:  " + customer.getCompName() + "</html>");
		addressLabel.setText("Address:  " + customer.getAddress());
		cityLabel.setText("City:  " + customer.getCity());
		contactLabel.setText("Contact:  " + customer.getContact());
		phoneLabel.setText("Phone:  " + customer.getPhone());
		installLabel.setText("Installed on:  " + customer.getInstallDate());
		removedLabel.setText("Removed on: " + customer.getRemoveDate());
		notesField.setText("<html>" + customer.getNotes() + "</html>");		
	}
	
	public void componentSetup(JLabel label){
		label.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		
	}

}