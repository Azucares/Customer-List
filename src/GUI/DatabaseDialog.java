package GUI;

import static java.awt.FlowLayout.CENTER;
import static java.awt.FlowLayout.RIGHT;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import javafx.geometry.Insets;


public class DatabaseDialog extends JDialog {
	private JButton okButton ;
	private JButton cancelButton ;
	private JLabel portLabel ;
	private JSpinner portSpinner ;
	private SpinnerNumberModel spinnerModel ;
	private JLabel hostLabel ;
	private JTextField hostField ;
	private JLabel dbLabel ;
	private JTextField dbField ;
	private JLabel userLabel ;
	private JTextField userField ;
	private JLabel passLabel ;
	private JPasswordField passField ;
	private JButton testButton ;
	private JLabel testLabel ;
	private PrefsListener prefsListener ;
	
	
	public DatabaseDialog(JFrame parent){
		super(parent, "Database Preferences") ;
		
		okButton = new JButton("OK") ;
		cancelButton = new JButton("Cancel") ;
		portLabel = new JLabel("port: ", SwingConstants.RIGHT) ;
		spinnerModel = new SpinnerNumberModel(3306, 0, 99999, 1) ;
		portSpinner = new JSpinner(spinnerModel) ;
		hostLabel = new JLabel("Server: ", SwingConstants.RIGHT) ;
		hostField = new JTextField(10) ;
		
		
		
		dbLabel = new JLabel("Database name: ", SwingConstants.RIGHT) ;
		dbField = new JTextField(10) ;
		userLabel = new JLabel("User name: ", SwingConstants.RIGHT) ;
		userField = new JTextField(10) ;
		passLabel = new JLabel("Password: ", SwingConstants.RIGHT) ;
		passField = new JPasswordField("*", 10) ;
		testButton = new JButton("Test connection") ;
		testLabel = new JLabel("") ;
		
		JPanel topPanel = new JPanel() ;
		JPanel midPanel = new JPanel() ;
		JPanel botPanel = new JPanel() ;
		JPanel midLeft = new JPanel() ;
		JPanel midRight = new JPanel() ;
		
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				
				if(prefsListener != null){
					prefsListener.preferencesSet(hostField.getText(), userField.getText(), passField.getText(), dbField.getText(), (Integer)portSpinner.getValue()) ;
				}
				setVisible(false) ;
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false) ;
			}			
		});
				
		setLayout(new BorderLayout()) ;
		topPanel.setLayout(new FlowLayout(CENTER));
		botPanel.setLayout(new FlowLayout(RIGHT));

		
		midPanel.setLayout(new GridLayout(5, 2, 8, 8));
		
		add(topPanel, BorderLayout.NORTH) ;
		add(midPanel, BorderLayout.CENTER) ;
		add(botPanel, BorderLayout.SOUTH) ;
		
		midPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 75));
		
		midPanel.add(hostLabel) ;
		midPanel.add(hostField) ;
		midPanel.add(userLabel) ;
		midPanel.add(userField);
		midPanel.add(passLabel) ;
		midPanel.add(passField);
		midPanel.add(dbLabel) ;
		midPanel.add(dbField) ;
		midPanel.add(portLabel) ;
		midPanel.add(portSpinner) ;
		
		botPanel.add(cancelButton);
		botPanel.add(okButton);
		
		setSize(350, 300) ;
		setLocationRelativeTo(parent) ;
	}

	public void setDefaults(String server, String user, String password, String db, Integer port){
		hostField.setText(server);
		userField.setText(user);
		passField.setText(password);
		dbField.setText(db);
		portSpinner.setValue(port);
	}

	public void setprefsListener(PrefsListener prefsListener) {
		this.prefsListener = prefsListener ;
	}

}
