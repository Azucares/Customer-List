package GUI;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.Customer;
public class CustomerTableModel extends AbstractTableModel {
	private ArrayList<Customer> custList = new ArrayList<Customer>() ;
	private String[] colNames = {"Id", "Customer", "Address", "City", "Contact", "Phone", "Make", "Model", "Maint.", "Installed", "Removed", "Current Location", "Notes"} ;
	
	public void setData(ArrayList<Customer> custList){
		//this.custList.clear();
		this.custList = custList ;
	}
	
	public String getColumnName(int column){
		return colNames[column] ;
	}
	
	@Override
	public int getColumnCount() {
		return 13;
	}

	@Override
	public int getRowCount() {
		return custList.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Customer customer = custList.get(row) ;
		
		switch(col){
		case 0:
			return customer.getAoaId() ;
		case 1:
			return customer.getCompName() ;
		case 2: 	
			return customer.getAddress() ;
		case 3:
			return customer.getCity() ;
		case 4:
			return customer.getContact() ;
		case 5:
			return customer.getPhone() ;
		case 6:
			return customer.getMake() ;
		case 7:
			return customer.getModel() ;
		case 8:
			return customer.getMaint() ;
		case 9:
			return customer.getInstallDate() ;
		case 10:
			return customer.getRemoveDate() ;
		case 11:
			return customer.getCurrentLoc() ;
		case 12:
			return customer.getNotes() ;	
		}
		
		
		
		return null;
	}

}
