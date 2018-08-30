package GUI;

import java.util.EventObject;

public class FormEvent extends EventObject{
	private int id ;
	private String company ;
	private String address ;
	private String city ;
	private int zip ;
	private String contact ;
	private String phone ;
	private String make ;
	private String model ;
	private String serial ;
	private boolean isMaint ;
	private String installedFrom ;
	private String installedTo ;
	private String install ;
	private String removed ;
	private boolean isActive ;
	private String supplyFile ;
	private String notes ;
	private String atRemoteSearch ;
	private String dialogFunction ;
	private int key ;
	private String tableFunction ;
	private boolean atRemoteUpdate ;

	public FormEvent(Object source){
		super(source) ;
	}
	
	public FormEvent(Object source, String tableFunction, int key){
		super(source) ;
		this.key = key ;
		this.tableFunction = tableFunction ;
	}
	
	public FormEvent(Object source, int id, String company, String city, String make, String model, String serial, String installedFrom, String installedTo, Boolean isMaint, Boolean isActive, String atRemoteSearch) {
		super(source);
		this.id = id ;
		this.company = company ;
		this.city = city ;
		this.make = make ;
		this.model = model ;
		this.serial = serial ;
		this.installedFrom = installedFrom ;
		this.installedTo = installedTo ;
		this.isActive = isActive ;
		this.isMaint = isMaint ;
		this.atRemoteSearch = atRemoteSearch ;
	}
	
	public FormEvent(Object source, String dialogFunction, int id, String company, String address, String city, int zip, String contact, String phone, String make, String model, String serial, boolean maint, String install, String removed, boolean active, String supplyFile, String notes, boolean atRemoteUpdate){
		super(source) ;
		this.id = id ;
		this.company = company ;
		this.address = address ;
		this.city = city ;
		this.zip = zip ;
		this.contact = contact ;
		this.phone = phone ;
		this.make = make ;
		this.model = model ;
		this.serial = serial ;
		this.isMaint = maint ;
		this.install = install ;
		this.removed = removed ;
		this.isActive = active ;
		this.supplyFile = supplyFile ;
		this.notes = notes ;
		this.atRemoteUpdate = atRemoteUpdate ;
		this.dialogFunction = dialogFunction ;
	}

	public String getTableFunction() {
		return tableFunction;
	}

	public void setTableFunction(String tableFunction) {
		this.tableFunction = tableFunction;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getDialogFunction() {
		return dialogFunction;
	}

	public void setDialogFunction(String dialogFunction) {
		this.dialogFunction = dialogFunction;
	}

	public String getSupplyFile() {
		return supplyFile;
	}

	public void setSupplyFile(String supplyFile) {
		this.supplyFile = supplyFile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMake() {
		return make;
	}

	public String getAtRemoteSearch() {
		return atRemoteSearch;
	}

	public void setAtRemoteSearch(String atRemoteSearch) {
		this.atRemoteSearch = atRemoteSearch;
	}

	public boolean isAtRemoteUpdate() {
		return atRemoteUpdate;
	}

	public void setAtRemoteUpdate(boolean atRemoteUpdate) {
		this.atRemoteUpdate = atRemoteUpdate;
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

	public String getInstalledFrom() {
		return installedFrom;
	}

	public void setInstalledFrom(String installedFrom) {
		this.installedFrom = installedFrom;
	}

	public String getInstalledTo() {
		return installedTo;
	}

	public void setInstalledTo(String installedTo) {
		this.installedTo = installedTo;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isMaint() {
		return isMaint;
	}

	public void setMaint(boolean isMaint) {
		this.isMaint = isMaint;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getInstall() {
		return install;
	}

	public void setInstalled(String install) {
		this.install = install;
	}

	public String getRemoved() {
		return removed;
	}

	public void setRemoved(String removed) {
		this.removed = removed;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
