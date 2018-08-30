package model;

import java.sql.Date;

public class Customer {
	private int id ;
	private int aoaId ;
	private String compName ;
	private String address ;
	private String city ;
	private int zip ;
	private String contact ;
	private String phone ;
	private String make ;
	private String model ;
	private String serial ;
	private boolean maintenance ;
	private String installDate ;
	private String removeDate ;
	private boolean isActive ;
	private String supplyFile ;
	private String notes ;
	private boolean atRemote ;
	
	
	private String description = null ;

	public Customer(int id, int aoaId, String compName, String address, String city, int zip, String contact, String phone, String make, String model, String serial, boolean maintenance, String installDate, String removeDate, boolean isActive, String supplyFile, String notes, boolean atRemote){
		this.id = id ;
		this.aoaId = aoaId ;
		this.compName = compName ;
		this.address = address ;
		this.city = city ;
		this.zip = zip ;
		this.contact = contact ;
		this.phone = phone ;
		this.make = make ;
		this.model = model ;
		this.serial = serial ;
		this.maintenance = maintenance ;
		this.installDate = installDate ;
		this.removeDate = removeDate ;
		this.isActive = isActive ;
		this.supplyFile = supplyFile ;
		this.notes = notes ;
		this.atRemote = atRemote ;
	}

	public String toString(){
		description = aoaId + "," + compName + "," + address + "," + city + "," + zip + "," + contact + "," + phone + "," + make + "," + model + "," + serial + "," + maintenance + "," + installDate + "," + removeDate + "," + isActive + "," + supplyFile + "," + atRemote + "," + notes ;
		return description ;
	}

	public String getSupplyFile() {
		return supplyFile;
	}

	public void setSupplyFile(String supplyFile) {
		this.supplyFile = supplyFile;
	}

	public boolean isMaintenance() {
		return maintenance;
	}


	public void isMaintenance(boolean maintenance) {
		this.maintenance = maintenance;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCompName() {
		return compName;
	}


	public void setCompName(String compName) {
		this.compName = compName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public int getZip() {
		return zip;
	}


	public void setZip(int zip) {
		this.zip = zip;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getMake() {
		return make;
	}


	public void setMake(String make) {
		this.make = make;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getSerial() {
		return serial;
	}


	public void setSerial(String serial) {
		this.serial = serial;
	}


	public String getInstallDate() {
		if(installDate == null){
			return "" ;
		}else{
			return installDate;
		}	
	}


	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}


	public String getRemoveDate() {
		if(removeDate == null){
			return "" ;
		}else{
			return removeDate;
		}		
	}


	public void setRemoveDate(String removeDate) {
		this.removeDate = removeDate;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public int getAoaId() {
		return aoaId;
	}
	
	public boolean isAtRemote() {
		return atRemote;
	}
	
	public void setAtRemote(boolean atRemote) {
		this.atRemote = atRemote;
	}
	
	public void setAoaId(int idNumb) {
		this.aoaId = idNumb;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getCurrentLoc(){
		if(isActive){
			return "yes" ;
		}
		else{
			return "no" ;
		}
	}
	public String getMaint(){
		if(maintenance){
			return "yes" ;
		}
		else{
			return "no" ;
		}
	}
	
	
}
