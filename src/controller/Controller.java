package controller;

import java.util.ArrayList;
import java.util.List;

import GUI.FormEvent;
import GUI.FormListener;
import model.Customer;
import model.Database;

public class Controller {
	private Database db ;
	private ArrayList<Customer> custList ;
	private String status ;
	private FormListener formListener ;

	public Controller(){
		db = new Database() ;
	}
	
	public ArrayList<Customer> searchList(String hostName, String dbName, int port, String user, String password) throws Exception{
			db.search(hostName, dbName, port, user, password) ;
			return custList ;
	}
	
	public ArrayList<Customer> searchList(int aoaId, String compName, String city, String make, String model, String serial, Boolean maintenance, String installedFrom, String installedTo, Boolean isActive, String atRemote, String hostName, String dbName, int port, String user, String password) throws Exception{
		custList = db.search(aoaId, compName, city, make, model, serial, maintenance, installedFrom, installedTo, isActive, atRemote, hostName, dbName, port, user, password) ;
		return custList;		
	}
	
	public void newDatabase(String host, String dbName, int port, String user, String password) throws Exception{
		db.newDatabase(host, dbName, port, user, password);
	}
	
	public List<Customer> getCustList(){
		return db.getCust() ;		
	}

	public void addEntry(int id, String company, String address, String city, int zip, String contact, String phone, String make, String model, String serial, boolean maint, String installed, String removed, boolean active, String supplyFile, String notes, boolean atRemote, String hostName, String dbName, int port, String user, String password) throws Exception {
		db.add(id, company, address, city, zip, contact, phone, make, model, serial, maint, installed, removed, active, supplyFile, notes, atRemote, hostName, dbName, port, user, password);
	}	
	
	public void updateEntry(int id, String company, String address, String city, int zip, String contact, String phone, String make, String model, String serial, boolean maint, String installed, String removed, boolean active, String supplyFile, String notes, boolean atRemote, int key, String hostName, String dbName, int port, String user, String password) throws Exception{
		db.update(id, company, address, city, zip, contact, phone, make, model, serial, maint, installed, removed, active, supplyFile, notes, atRemote, key, hostName, dbName, port, user, password);	
	}
	
	public void removeEntry(int key, String hostName, String dbName, int port, String user, String password) throws Exception{
		db.remove(key, hostName, dbName, port, user, password);
	}

}
