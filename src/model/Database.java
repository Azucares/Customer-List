package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import GUI.FormEvent;
import GUI.FormListener;

public class Database {
	private ArrayList<Customer> custList;
	private Connection con;
	private String checkSql;
	private ResultSet result;
	private FormListener formListener;

	public Database() {
		custList = new ArrayList<Customer>();
		result = null;
	}

	public void addCust(Customer cust) {
		custList.add(cust);
	}

	public ArrayList<Customer> getCust() {
		return custList;
	}

	// creates a new CustomerList database if one isn't present on the server

	public void newDatabase(String host, String dbName, int port, String user, String password) throws Exception {
		connect2(host, port, user, password);
		createStatement(
				"if not exists (select name from master.dbo.sysdatabases where name = N'CustomerList')	create database [CustomerList];");
		createStatement("use CustomerList");
		createStatement(
				"if not exists (select name from sys.tables where name='custTable') create table custTable(ID int IDENTITY(1,1) PRIMARY KEY, aoaID int, company varchar(255), address varchar(255), city varchar(255), zip int, contactName varchar(255), phone varchar(255), make varchar(255), model varchar(255), serialNumb varchar(255), maintenance bit, install datetime, removal datetime, isActive bit, supplyFile varchar(25), notes varchar(255), atRemote bit)");
		disconnect();
	}

	// Performs a repeat of the last search

	public ArrayList<Customer> search(String hostName, String dbName, int port, String user, String password)
			throws Exception {
		ResultSet results;
		PreparedStatement checkStmt;
		custList.clear();
		connect(hostName, dbName, port, user, password);
		createStatement("use CustomerList");
		checkStmt = con.prepareStatement(checkSql);
		results = checkStmt.executeQuery();

		while (results.next()) {
			custList.add(new Customer(results.getInt(1), results.getInt(2), results.getString(3), results.getString(4),
					results.getString(5), results.getInt(6), results.getString(7), results.getString(8),
					results.getString(9), results.getString(10), results.getString(11), results.getBoolean(12),
					results.getString(13), results.getString(14), results.getBoolean(15), results.getString(16),
					results.getString(17), results.getBoolean(18)));
		}

		checkStmt.close();
		disconnect();

		return custList;
	}

	// Searches the database

	public ArrayList<Customer> search(int aoaId, String compName, String city, String make, String model, String serial,
			boolean maintenance, String installFrom, String installTo, boolean isActive, String atRemote,
			String hostName, String dbName, int port, String user, String password) throws Exception {
		ArrayList<String> statements = new ArrayList<String>();
		custList.clear();
		checkSql = null;
		ResultSet results;
		PreparedStatement checkStmt;

		int statementCount = 0;
		if (aoaId > 0) {
			statements.add("aoaId like '%" + aoaId + "%'");
			statementCount++;
		}
		if (compName.length() > 0) {
			statements.add(extractSearchParams("company", compName));
			statementCount++;
		}
		if (city.length() > 0) {
			statements.add(extractSearchParams("city", city));
			statementCount++;
		}
		if (make.length() > 0) {
			statements.add(extractSearchParams("make", make));
			statementCount++;
		}
		if (model.length() > 0) {
			statements.add(extractSearchParams("model", model));
			statementCount++;
		}
		if (serial.length() > 0) {
			statements.add("serialNumb like '%" + serial + "%'");
			statementCount++;
		}
		if (installFrom.length() > 0 && installTo.length() > 0) {
			statements.add("install between '" + installFrom + "' and '" + installTo + "'");
			statementCount++;
		}
		if (!maintenance) {
			statements.add("maintenance=1");
			statementCount++;
		}
		if (!isActive) {
			statements.add("isActive=1");
			statementCount++;
		}
		if (atRemote == "enabled") {
			statements.add("atRemote=1");
			statementCount++;
		} else if (atRemote == "disabled") {
			statements.add("atRemote=0");
			statementCount++;
		}

		switch (statementCount) {
		case 11:
			checkSql = "select ID, aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, convert(varchar, install, 106), convert(varchar, removal, 106), isActive, supplyFile, notes, atRemote from custTable WHERE "
					+ statements.get(0) + " AND " + statements.get(1) + " AND " + statements.get(2) + " AND "
					+ statements.get(3) + " AND " + statements.get(4) + " AND " + statements.get(5) + " AND "
					+ statements.get(6) + " AND " + statements.get(7) + " AND " + statements.get(8) + " AND "
					+ statements.get(9) + " AND " + statements.get(10);
			break;
		case 10:
			checkSql = "select ID, aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, convert(varchar, install, 106), convert(varchar, removal, 106), isActive, supplyFile, notes, atRemote from custTable WHERE "
					+ statements.get(0) + " AND " + statements.get(1) + " AND " + statements.get(2) + " AND "
					+ statements.get(3) + " AND " + statements.get(4) + " AND " + statements.get(5) + " AND "
					+ statements.get(6) + " AND " + statements.get(7) + " AND " + statements.get(8) + " AND "
					+ statements.get(9);
			break;
		case 9:
			checkSql = "select ID, aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, convert(varchar, install, 106), convert(varchar, removal, 106), isActive, supplyFile, notes, atRemote from custTable WHERE "
					+ statements.get(0) + " AND " + statements.get(1) + " AND " + statements.get(2) + " AND "
					+ statements.get(3) + " AND " + statements.get(4) + " AND " + statements.get(5) + " AND "
					+ statements.get(6) + " AND " + statements.get(7) + " AND " + statements.get(8);
			break;
		case 8:
			checkSql = "select ID, aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, convert(varchar, install, 106), convert(varchar, removal, 106), isActive, supplyFile, notes, atRemote from custTable WHERE "
					+ statements.get(0) + " AND " + statements.get(1) + " AND " + statements.get(2) + " AND "
					+ statements.get(3) + " AND " + statements.get(4) + " AND " + statements.get(5) + " AND "
					+ statements.get(6) + " AND " + statements.get(7);
			break;
		case 7:
			checkSql = "select ID, aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, convert(varchar, install, 106), convert(varchar, removal, 106), isActive, supplyFile, notes, atRemote from custTable WHERE "
					+ statements.get(0) + " AND " + statements.get(1) + " AND " + statements.get(2) + " AND "
					+ statements.get(3) + " AND " + statements.get(4) + " AND " + statements.get(5) + " AND "
					+ statements.get(6);
			break;
		case 6:
			checkSql = "select ID, aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, convert(varchar, install, 106), convert(varchar, removal, 106), isActive, supplyFile, notes, atRemote from custTable WHERE "
					+ statements.get(0) + " AND " + statements.get(1) + " AND " + statements.get(2) + " AND "
					+ statements.get(3) + " AND " + statements.get(4) + " AND " + statements.get(5);
			break;
		case 5:
			checkSql = "select ID, aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, convert(varchar, install, 106), convert(varchar, removal, 106), isActive, supplyFile, notes, atRemote from custTable WHERE "
					+ statements.get(0) + " AND " + statements.get(1) + " AND " + statements.get(2) + " AND "
					+ statements.get(3) + " AND " + statements.get(4);
			break;
		case 4:
			checkSql = "select ID, aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, convert(varchar, install, 106), convert(varchar, removal, 106), isActive, supplyFile, notes, atRemote from custTable WHERE "
					+ statements.get(0) + " AND " + statements.get(1) + " AND " + statements.get(2) + " AND "
					+ statements.get(3);
			break;
		case 3:
			checkSql = "select ID, aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, convert(varchar, install, 106), convert(varchar, removal, 106), isActive, supplyFile, notes, atRemote from custTable WHERE "
					+ statements.get(0) + " AND " + statements.get(1) + " AND " + statements.get(2);
			break;
		case 2:
			checkSql = "select ID, aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, convert(varchar, install, 106), convert(varchar, removal, 106), isActive, supplyFile, notes, atRemote from custTable WHERE "
					+ statements.get(0) + " AND " + statements.get(1);
			break;
		case 1:
			checkSql = "SELECT ID, aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, convert(varchar, install, 106), convert(varchar, removal, 106), isActive, supplyFile, notes, atRemote from custTable WHERE "
					+ statements.get(0);
			break;
		case 0:
			checkSql = "SELECT ID, aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, convert(varchar, install, 106), convert(varchar, removal, 106), isActive, supplyFile, notes, atRemote from custTable";
			break;
		}
		try {
			connect(hostName, dbName, port, user, password);
		} catch (Exception connectError) {
			JOptionPane.showMessageDialog(null, connectError.toString(), "Could not connect",
					JOptionPane.ERROR_MESSAGE);
		}

		createStatement("use CustomerList");

		checkStmt = con.prepareStatement(checkSql);
		results = checkStmt.executeQuery();
		while (results.next()) {
			custList.add(new Customer(results.getInt(1), results.getInt(2), results.getString(3), results.getString(4),
					results.getString(5), results.getInt(6), results.getString(7), results.getString(8),
					results.getString(9), results.getString(10), results.getString(11), results.getBoolean(12),
					results.getString(13), results.getString(14), results.getBoolean(15), results.getString(16),
					results.getString(17), results.getBoolean(18)));
		}
		checkStmt.close();
		disconnect();
		return custList;
	}

	// Adds a new entry to the database

	public void add(int id, String company, String address, String city, int zip, String contact, String phone,
			String make, String model, String serial, boolean maint, String installed, String removed, boolean active,
			String supplyFile, String notes, boolean atRemote, String hostName, String dbName, int port, String user,
			String password) throws Exception {

		String install = null;
		String removal = null;

		if (installed != null) {
			if (installed.length() > 0) {
				install = installed;
			}
		}
		if (removed != null) {
			if (removed.length() > 0) {
				removal = removed;
			}
		}

		String insertSql = "insert into custTable (aoaID, company, address, city, zip, contactName, phone, make, model, serialNumb, maintenance, install, removal, isActive, supplyFile, notes, atRemote) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		connect(hostName, dbName, port, user, password);
		PreparedStatement insertStatement = con.prepareStatement(insertSql);

		int col = 1;
		insertStatement.setInt(col++, id);
		insertStatement.setString(col++, company);
		insertStatement.setString(col++, address);
		insertStatement.setString(col++, city);
		insertStatement.setInt(col++, zip);
		insertStatement.setString(col++, contact);
		insertStatement.setString(col++, phone);
		insertStatement.setString(col++, make);
		insertStatement.setString(col++, model);
		insertStatement.setString(col++, serial);
		insertStatement.setBoolean(col++, maint);
		insertStatement.setString(col++, install);
		insertStatement.setString(col++, removal);
		insertStatement.setBoolean(col++, active);
		insertStatement.setString(col++, supplyFile);
		insertStatement.setString(col++, notes);
		insertStatement.setBoolean(col++, atRemote);

		createStatement("use CustomerList");
		insertStatement.executeUpdate();

		insertStatement.close();
		disconnect();
	}

	public void remove(int key, String hostName, String dbName, int port, String user, String password)
			throws Exception {
		String deleteSql = "delete from custTable where ID=?";

		connect(hostName, dbName, port, user, password);
		PreparedStatement deleteStatement = con.prepareStatement(deleteSql);
		deleteStatement.setInt(1, key);
		createStatement("use CustomerList");
		deleteStatement.executeUpdate();

		deleteStatement.close();
		disconnect();
	}

	// Updates the chosen entry

	public void update(int id, String company, String address, String city, int zip, String contact, String phone,
			String make, String model, String serial, boolean maint, String installed, String removed, boolean active,
			String supplyFile, String notes, boolean atRemote, int key, String hostName, String dbName, int port,
			String user, String password) throws Exception {
		String updateSql = "update custTable set aoaID=?, company=?, address=?, city=?, zip=?, contactName=?, phone=?, make=?, model=?, serialNumb=?, maintenance=?, install=?, removal=?, isActive=?, supplyFile=?, notes=?, atRemote=? where ID=?";
		String install = null;
		String removal = null;

		if (installed.length() > 0) {
			install = installed;
		}
		if (removed.length() > 0) {
			removal = removed;
		}

		connect(hostName, dbName, port, user, password);
		PreparedStatement updateStatement = con.prepareStatement(updateSql);
		int col = 1;
		updateStatement.setInt(col++, id);
		updateStatement.setString(col++, company);
		updateStatement.setString(col++, address);
		updateStatement.setString(col++, city);
		updateStatement.setInt(col++, zip);
		updateStatement.setString(col++, contact);
		updateStatement.setString(col++, phone);
		updateStatement.setString(col++, make);
		updateStatement.setString(col++, model);
		updateStatement.setString(col++, serial);
		updateStatement.setBoolean(col++, maint);
		updateStatement.setString(col++, install);
		updateStatement.setString(col++, removal);
		updateStatement.setBoolean(col++, active);
		updateStatement.setString(col++, supplyFile);
		updateStatement.setString(col++, notes);
		updateStatement.setBoolean(col++, atRemote);
		updateStatement.setInt(col++, key);

		createStatement("use CustomerList");
		updateStatement.executeUpdate();

		updateStatement.close();
		disconnect();
	}

	public void connect(String host, String dbName, int port, String user, String password) throws Exception {
		String url = null;
		if (user.length() > 1) {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.toString(), "Driver not found", JOptionPane.ERROR_MESSAGE);
				// throw new Exception("Driver not found");
			}
			url = "jdbc:sqlserver://" + host + "\\SQLEXPRESS" + ":"
					+ port /* + ";" + "databaseName=" + dbName */ + ";user=" + user + ";password=" + password
					+ ";integratedSecurity=false";
		} else {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.toString(), "Driver not found", JOptionPane.ERROR_MESSAGE);
				// throw new Exception("Driver not found");
			}
			url = "jdbc:sqlserver://" + host + "\\SQLEXPRESS" + ":"
					+ port /* + ";" + "databaseName=" + dbName */ + ";integratedSecurity=true";
		}

		con = DriverManager.getConnection(url);

	}

	public void connect2(String host, int port, String user, String password) throws Exception {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Driver not found", JOptionPane.ERROR_MESSAGE);
			// throw new Exception("Driver not found");
		}
		String url = "jdbc:sqlserver://" + host + "\\SQLEXPRESS" + ":" + port + ";integratedSecurity=true";
		con = DriverManager.getConnection(url);
	}

	public void disconnect() throws SQLException {
		con.close();
	}

	public void createStatement(String stmt) throws SQLException {
		PreparedStatement createStmt = con.prepareStatement(stmt);
		createStmt.execute();
	}

	public void setFormListener(FormListener formListener) {
		this.formListener = formListener;
	}

	// handles inputs with multiple search parameters
	public String extractSearchParams(String column, String params) {
		String[] searchParams = params.split(", ");

		String search = "(";

		for (int i = 0; i < searchParams.length; i++) {
			search = search + column + " LIKE '%" + searchParams[i] + "%'";
			if (i != (searchParams.length - 1)) {
				search = search + " OR ";
			}
		}
		search = search + ")";
		return search;
	}
}