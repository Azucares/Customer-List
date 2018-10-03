package GUI;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import model.Customer;


public class TablePanel extends JPanel implements ActionListener {
	private JTable table ;
	private CustomerTableModel tableModel ;
	private JPopupMenu popup ;
	private FormListener formListener ;
	private int row ;
	private String tableFunction ;
	
	private JMenuItem copySerialItem ;
	private JMenuItem updateItem ;
	private JMenuItem detailsItem ;
	private JMenuItem removeItem ;
	private TableColumnModel columnModel ;
	
	public TablePanel(){		
		tableModel = new CustomerTableModel() ;
		table = new JTable(tableModel) ;
		columnModel = table.getColumnModel() ;
		popup = new JPopupMenu() ;
		row = 0 ;
		
		copySerialItem = new JMenuItem("Copy serial number") ;
		updateItem = new JMenuItem("Update entry") ;
		detailsItem = new JMenuItem("View details") ;
		removeItem = new JMenuItem("Delete entry") ;
		
		columnModel.getColumn(0).setPreferredWidth(40);
		columnModel.getColumn(1).setPreferredWidth(300);
		columnModel.getColumn(2).setPreferredWidth(200);
		columnModel.getColumn(3).setPreferredWidth(100);
		columnModel.getColumn(4).setPreferredWidth(100);
		columnModel.getColumn(5).setPreferredWidth(100);
		columnModel.getColumn(6).setPreferredWidth(75);
		columnModel.getColumn(7).setPreferredWidth(75);
		columnModel.getColumn(8).setPreferredWidth(60);
		columnModel.getColumn(9).setPreferredWidth(100);
		columnModel.getColumn(10).setPreferredWidth(100);
		columnModel.getColumn(11).setPreferredWidth(75);
		columnModel.getColumn(12).setPreferredWidth(500);
		
		popup.add(copySerialItem) ;
		popup.addSeparator();
		popup.add(updateItem) ;
		popup.add(detailsItem) ;
		popup.addSeparator();
		popup.add(removeItem) ;
		
		copySerialItem.addActionListener(this);
		updateItem.addActionListener(this);
		detailsItem.addActionListener(this);
		removeItem.addActionListener(this);
		
		table.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				row = table.rowAtPoint(e.getPoint()) ;
				table.getSelectionModel().setSelectionInterval(row, row);
				if(e.getButton() == MouseEvent.BUTTON3){
					popup.show(table, e.getX(), e.getY());
				}
				if(e.getClickCount() == 2){
					detailsItem.doClick();		
				}
			}			
		});
		
		setLayout(new BorderLayout()) ;
		add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER) ;
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem clicked = (JMenuItem)e.getSource() ;
		
		if(clicked == updateItem){
			tableFunction = "update" ;
			row = table.getSelectedRow() ;
		}
		if(clicked == detailsItem){
			tableFunction = "details" ;
			row = table.getSelectedRow() ;
		}
		if(clicked == removeItem){
			tableFunction = "remove" ;
			row = table.getSelectedRow() ;
		}
		if(clicked == copySerialItem){
			tableFunction = "copySerial" ;
			row = table.getSelectedRow() ;
		}
		
		FormEvent ev = new FormEvent(this, tableFunction, getRow()) ;
				
		if(formListener != null){
			formListener.formEventOccurred(ev);
		}
	}
	
	public void setData(ArrayList<Customer> custList){
		tableModel.setData(custList) ;
	}
	public void refresh(){
		tableModel.fireTableDataChanged();
		table.requestFocus();
		table.changeSelection(0,0,false, false);
	}
	public void setFormListener(FormListener formListener) {
		this.formListener = formListener ;
	}
	public int getRow() {
		return row;
	}
	public void printTable() throws PrinterException{
		table.print() ;
	}
	
}
