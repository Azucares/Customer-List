package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import model.Link;

public class WebLinks extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Link> linksList= new ArrayList<Link>() ;;
	private JPanel panel;
	private JPanel namePanel;
	private JPanel descPanel;
	private JPanel addPanel;
	private JTextField nameField;
	private JTextField descriptionField;
	private JLabel nameLabel ;
	private JLabel descriptionLabel;
	private JLabel addLabel;
	private JTextField urlField;
	private JLabel urlLabel;
	private Boolean refresh ;
	private FormListener formListener ;
	private File file = new File("\\\\Aoa1\\g\\Customer List\\linksList.txt");
	private JPopupMenu popup ;
	private JMenuItem removeLink ;
	private String clickedLabel ;
	
	public WebLinks(JFrame parent){
		super(parent, "Reference Links") ;
		
		panel = new JPanel();
		namePanel = new JPanel();
		descPanel = new JPanel() ;
		addPanel = new JPanel();
		nameField = new JTextField(5);
		descriptionField = new JTextField(15); 
		nameLabel = new JLabel("Site name: ");
		descriptionLabel = new JLabel("Description: ");
		addLabel = new JLabel("ADD LINK");
		urlField = new JTextField(7);
		urlLabel = new JLabel("Web address: ") ;
		popup = new JPopupMenu() ;

		removeLink = new JMenuItem("Remove link") ;
		
		popup.add(removeLink) ;
		
		removeLink.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				for(int i = 0; i < linksList.size(); i++){
					if(linksList.get(i).getName() == clickedLabel){
						linksList.remove(i) ;
					}
				}
				namePanel.removeAll();
				descPanel.removeAll();
				namePanel.repaint();
				descPanel.repaint();
				writeLinks() ;
				drawLayout();
				
				
				FormEvent ev = new FormEvent(this) ;
				
				if(formListener != null){
					formListener.formEventOccurred(ev);
				}
			}
		});
		
		getLinks();
		drawLayout();
		
		add(panel) ;
		namePanel.setBackground(Color.WHITE);
		descPanel.setBackground(Color.WHITE);
		addPanel.setBackground(Color.WHITE);
		
		panel.setLayout(new BorderLayout());
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
		descPanel.setLayout(new BoxLayout(descPanel, BoxLayout.Y_AXIS));
		addPanel.setLayout(new FlowLayout());
		
		panel.add(namePanel, BorderLayout.CENTER) ;
		panel.add(descPanel, BorderLayout.EAST) ;
		panel.add(addPanel, BorderLayout.SOUTH) ;
		
		addPanel.add(nameLabel);
		addPanel.add(nameField);
		addPanel.add(urlLabel);
		addPanel.add(urlField);
		addPanel.add(descriptionLabel);
		addPanel.add(descriptionField);
		addPanel.add(addLabelPrep(addLabel));
		
		addPanel.setPreferredSize(new Dimension(60, 60));
		descPanel.setPreferredSize(new Dimension(400, 500));
		
		setSize(650, 650) ;
		setLocationRelativeTo(parent) ;
	}
	
	public void getLinks(){
		
		try{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			
			
			while((line = br.readLine()) != null){
				String[] items = line.split(",") ;
				
				linksList.add(new Link(items[0], items[1], items[2]));
			}
			
		}catch(FileNotFoundException e){
			System.out.println("File not found...");
		}catch(IOException e){
			System.out.println("Unable to read file...");
		}
	}
	
	public void writeLinks(){
		
		try {
			FileWriter fw = new FileWriter(file) ;
			BufferedWriter bw = new BufferedWriter(fw);
			
			String name ;
			String url ;
			String desc ;
			
			for(int i = 0; i < linksList.size(); i++){
				if(linksList.get(i).getName().length() < 1){
					name = " " ;
				}else{
					name = linksList.get(i).getName();
				}
				
				if(linksList.get(i).getUrl().length() < 1){
					url = " " ;
				}else{
					url = linksList.get(i).getUrl();
				}
				
				if(linksList.get(i).getDescription().length() < 1){
					desc = " " ;
				}else{
					desc = linksList.get(i).getDescription();
				}
				
				bw.write(name + "," + url + "," + desc);
				bw.newLine();
			}
			bw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to read file");
		}
		
	}
	
	public void drawLayout(){
		
		for(int i = 0; i < linksList.size(); i++){
			processLayout(linksList.get(i)) ;
		}
	}
	
	public void processLayout(Link link){
		namePanel.add(prepLink(new JLabel(), link)) ;
		if(link.getDescription().length() < 2){
			descPanel.add(new JLabel("no description given"));
		}
		else
		{
			descPanel.add(new JLabel(link.getDescription()));
		}
		
	}
	
	public JLabel addLabelPrep(JLabel label){
		label.setOpaque(true);
		label.setForeground(Color.BLUE);
		label.setBackground(Color.WHITE);
		
		label.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(nameField.getText().length() > 1){
					Link newLink = new Link(nameField.getText(), urlField.getText(), descriptionField.getText()) ;
					linksList.add(newLink);
					writeLinks() ;
				
					processLayout(newLink) ;
				
					nameField.setText("");
					urlField.setText("");
					descriptionField.setText("");
					nameField.grabFocus();
				
					FormEvent ev = new FormEvent(this) ;
				
					if(formListener != null){
						formListener.formEventOccurred(ev);
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				label.setBackground(Color.LIGHT_GRAY) ;
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				label.setBackground(Color.WHITE);
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
		return label ;
	}
	
	public JLabel prepLink(JLabel label, Link link){
		if(link.getName().length() < 2){
			label.setText("no name given");
		}
		else
		{
			label.setText(link.getName());
		}
		
		label.setAlignmentX(CENTER_ALIGNMENT);
		label.setBackground(Color.white);
		label.setForeground(Color.blue);
		label.setOpaque(true);
		label.setPreferredSize(new Dimension(50, 10));
		
		label.addMouseListener(new MouseListener(){
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getButton() == MouseEvent.BUTTON1){
					try {
						Desktop desktop = java.awt.Desktop.getDesktop();
						URI oURL = new URI(link.getUrl());
						desktop.browse(oURL);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if(arg0.getButton() == MouseEvent.BUTTON3){
					popup.show(label, arg0.getX(), arg0.getY());
					clickedLabel = label.getText() ;
					
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				label.setBackground(Color.CYAN);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				label.setBackground(Color.white);
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
	

	public Boolean getRefresh() {
		return refresh;
	}

	public void setRefresh(Boolean refresh) {
		this.refresh = refresh;
	}
	
}