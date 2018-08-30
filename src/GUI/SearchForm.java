package GUI;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class SearchForm extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -548322329948804196L;
	private JLabel idLabel ;
	private JTextField idField ;
	private JLabel custLabel ;
	private JTextField custField ;
	private JLabel cityLabel ;
	private JTextField cityField ;
	private JLabel makeLabel ;
	private JTextField makeField ;
	private JLabel modelLabel ;
	private JTextField modelField ;
	private JTextField serialField ;
	private JLabel serialLabel ;
	private JLabel installLabel ;
	private JTextField installField ;
	private JLabel installLabelTwo ;
	private JTextField installFieldTwo ;
	private JCheckBox activeBox ;
	private JCheckBox maintBox ;
	private JButton searchButton ;
	private FormListener formListener ;
	private JLabel linksLabel;
	private WebLinks webLinks;
	private Boolean startLinks ;
	private JCheckBox atRemoteBox ;
	private JRadioButton atRemoteEnabled ;
	private JRadioButton atRemoteDisabled ;
	private JRadioButton atRemoteShowAll ;
	private ButtonGroup atRemoteGroup ;
	
	public SearchForm(){
		idLabel = new JLabel("ID:    ") ;
		idField = new JTextField(3) ;
		custLabel = new JLabel("Company:    ") ;
		custField = new JTextField(10) ;
		cityLabel = new JLabel("City:    ") ;
		cityField = new JTextField(10) ;
		makeLabel = new JLabel("Make:    ") ;
		makeField = new JTextField(10) ;
		modelLabel = new JLabel("Model:    ") ;
		modelField = new JTextField(10) ;
		serialField = new JTextField(10) ;
		serialLabel = new JLabel("Serial Number:    ") ;
		installLabel = new JLabel("Installed from    ") ;
		installField = new JTextField(6) ;
		installLabelTwo = new JLabel(" to    ") ;
		installFieldTwo = new JTextField(6) ;
		activeBox = new JCheckBox() ;
		maintBox = new JCheckBox() ;
		atRemoteBox = new JCheckBox() ;
		searchButton = new JButton("Search") ;
		linksLabel = new JLabel("AOA Common Links");
		atRemoteEnabled = new JRadioButton("Show Active") ;
		atRemoteDisabled = new JRadioButton("Show Inactive") ;
		atRemoteShowAll = new JRadioButton("Show all") ;
		atRemoteGroup = new ButtonGroup() ;
		
		startLinks = false ;
		
		atRemoteGroup.add(atRemoteEnabled);
		atRemoteGroup.add(atRemoteDisabled);
		atRemoteGroup.add(atRemoteShowAll);
		atRemoteShowAll.setSelected(true);
		
		atRemoteEnabled.setActionCommand("enabled");
		atRemoteDisabled.setActionCommand("disabled");
		atRemoteShowAll.setActionCommand("all");
		
		Border innerBorder = BorderFactory.createTitledBorder("Enter customer data to find") ;
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5) ;
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder)) ;
		
		searchButton.setMnemonic(KeyEvent.VK_ENTER);
		
		searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int aoaId = 0 ;
				try{
					aoaId = Integer.parseInt(idField.getText()) ;					
				}catch(Exception e){
					
				}
				String compName = custField.getText() ;
				String city = cityField.getText() ;
				String make = makeField.getText() ;
				String model = modelField.getText() ;
				String serial = serialField.getText() ;
				String installedFrom = installField.getText() ;
				String installedTo = installFieldTwo.getText() ;
				Boolean isMaint = maintBox.isSelected() ;
				Boolean isActive = activeBox.isSelected() ;
				String atRemote = atRemoteGroup.getSelection().getActionCommand() ;
				
				FormEvent ev = new FormEvent(this, aoaId, compName, city, make, model, serial, installedFrom, installedTo, isMaint, isActive, atRemote) ;
				
				if(formListener != null){
					formListener.formEventOccurred(ev);
				}
			}			
		});
		
		setLayout(new GridBagLayout()) ;
		GridBagConstraints gc = new GridBagConstraints() ;
		
		gc.gridx = 0 ;
		gc.gridy = 0 ;
		gc.weightx = 0 ;
		gc.weighty = 0 ;
		gc.insets = new Insets(20, 0, 5, 0) ;
		gc.fill = GridBagConstraints.NONE ;
		gc.anchor = GridBagConstraints.LINE_END ;
		
		add(idLabel, gc) ;		
		gc.anchor = GridBagConstraints.LINE_START ;
		gc.gridx = 1 ;
		add(idField, gc) ;
		
		gc.insets = new Insets(0, 0, 5, 0) ;
		gc.anchor = GridBagConstraints.LINE_END ;
		gc.gridx = 0 ;
		gc.gridy++ ;		
		
		add(custLabel, gc) ;
		gc.anchor = GridBagConstraints.LINE_START ;
		gc.gridx = 1 ;
		add(custField, gc) ;
		
		gc.anchor = GridBagConstraints.LINE_END ;
		gc.gridx = 0 ;
		gc.gridy++ ;
				
		add(cityLabel, gc) ;
		gc.anchor = GridBagConstraints.LINE_START ;
		gc.gridx = 1 ;
		add(cityField, gc) ;
		
		gc.anchor = GridBagConstraints.LINE_END ;
		gc.gridx = 0 ;
		gc.gridy++ ;		
		
		add(makeLabel, gc) ;
		gc.anchor = GridBagConstraints.LINE_START ;
		gc.gridx = 1 ;
		add(makeField, gc) ;
		
		gc.anchor = GridBagConstraints.LINE_END ;
		gc.gridx = 0 ;
		gc.gridy++ ;		
		
		add(modelLabel, gc) ;
		gc.anchor = GridBagConstraints.LINE_START ;
		gc.gridx = 1 ;
		add(modelField, gc) ;
		
		gc.anchor = GridBagConstraints.LINE_END ;
		gc.gridx = 0 ;
		gc.gridy++ ;		
		
		add(serialLabel, gc) ;
		gc.anchor = GridBagConstraints.LINE_START ;
		gc.gridx = 1 ;
		add(serialField, gc) ;
		
		gc.anchor = GridBagConstraints.LINE_END ;
		gc.gridx = 0 ;
		gc.gridy++ ;		
		
		gc.insets = new Insets(15, 0, 5, 0) ;
		add(installLabel, gc) ;
		
		gc.anchor = GridBagConstraints.LINE_START ;
		gc.gridx = 1 ;
		add(installField, gc) ;
		
		gc.gridy++ ;
		gc.gridx = 0 ;
		gc.anchor = GridBagConstraints.LINE_END ;
		gc.insets = new Insets(0, 0, 3, 0) ;
				
		add(installLabelTwo, gc) ;
		gc.anchor = GridBagConstraints.LINE_START ;
		gc.gridx = 1 ;
		add(installFieldTwo, gc) ;
		gc.gridy++ ;
		
		gc.gridx = 0 ;		
		gc.anchor = GridBagConstraints.LINE_END ;
		add(new JLabel("Show non-maintenance"), gc) ;
		gc.gridx++ ;
		gc.anchor = GridBagConstraints.LINE_START ;
		add(maintBox, gc) ;
		gc.gridy++ ;
		
		gc.gridx = 0 ;		
		gc.anchor = GridBagConstraints.LINE_END ;
		add(new JLabel("Show old locations"), gc) ;
		gc.gridx++ ;
		gc.anchor = GridBagConstraints.LINE_START ;
		add(activeBox, gc) ;
		
		
		gc.insets = new Insets(20, 0, 0, 0) ;
		gc.weighty = .02 ;
		gc.gridx = 0 ;
		gc.gridy++ ;
		
		gc.anchor = GridBagConstraints.LINE_END ;
		add(new JLabel("@Remote: "), gc) ;
		gc.gridy++ ;
		gc.gridx++ ;
		gc.insets = new Insets(0,0,0,0) ;
		gc.anchor = GridBagConstraints.LINE_START ;
		add(atRemoteShowAll, gc) ;
		
		gc.gridy++ ;
		add(atRemoteEnabled, gc) ;
		gc.gridy++ ;
		add(atRemoteDisabled, gc) ;
		
		
		gc.anchor = GridBagConstraints.FIRST_LINE_END ;
		gc.weighty = .5 ;
		gc.gridy++ ;
		gc.insets = new Insets(7, 0, 0, 0) ;
		
		add(searchButton, gc) ;
		
		gc.weighty = 2;
		gc.gridx = 1;
		gc.gridy++;
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		gc.insets = new Insets(10, 10, 10, 10) ;
		add(linksSetup(linksLabel), gc);
		
	}
	
	public JLabel linksSetup(JLabel label){
		label.setForeground(Color.BLUE);
		label.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				startLinks = true ;
				
				FormEvent ev = new FormEvent(this) ;
				
				if(formListener != null){
					formListener.formEventOccurred(ev);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				linksLabel.setForeground(Color.gray);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				linksLabel.setForeground(Color.BLUE);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		return label;
	}
	
	public void setFormListener(FormListener formListener) {
		this.formListener = formListener ;
	}
	
	public JButton getDefaultButton(){
		return searchButton ;
	}

	public Boolean getStartLinks() {
		return startLinks;
	}

	public void setStartLinks(Boolean startLinks) {
		this.startLinks = startLinks;
	}
	
}
