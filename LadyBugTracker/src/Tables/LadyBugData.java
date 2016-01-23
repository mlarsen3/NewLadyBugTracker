/**
 * 
 * Author Sophy Yang
 * Date January 22, 2016
 *
 */
package Tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;

public class LadyBugData {
	private Connection con = null;
	private Statement st = null;
	private ResultSet rs = null;
	private final String userSqlStr = "SELECT * FROM user";
	private final String dropdownItemStr = "SELECT * FROM dropdownitems";
	private final String itemsSqlStr = "SELECT dropdownitems.ID AS ID, " + "dropdownitems.Description AS Description, "
			+ "dropdownlist.Description AS iClass, " + "dropdownitems.iOrder AS iOrder, "
			+ "dropdownitems.dropdownListID AS dropdownListID " + " FROM " + "(dropdownitems  JOIN dropdownlist ON "
			+ "((dropdownitems.DropdownListID = dropdownlist.DropdownListID))) " + " WHERE "
			+ " (dropdownitems.DropdownListID  ";

	ArrayList<ItemList> arrayList = new ArrayList<ItemList>();

	public LadyBugData() throws SQLException {
		// dropdownItemStr
		ArrayList<dropdownitems> arrayList = new ArrayList<dropdownitems>();

		// try {
		makeConnection();
		String q = dropdownItemStr;
		st = con.createStatement();
		rs = st.executeQuery(q);
//		System.out.println("start....");
//		while (rs.next()) {
//			Tables.dropdownitems items = new Tables.dropdownitems();
//			items.setID(rs.getInt("ID"));
//			items.setDropdownListID(rs.getInt("DropdownListID"));
//			items.setDescription(rs.getString("Description"));
//			items.setiOrder(rs.getInt("iOrder"));
//			Timestamp times = rs.getTimestamp("DateCreated");
//			items.setCreatedDate(times);
//			times = rs.getTimestamp("LastModified");
//			items.setLastModified(times);
//			arrayList.add(items);
//		}
//		System.out.println(arrayList);

		if (rs != null) {
			rs.close();
		}
		if (st != null) {
			st.close();
		}
		if (con != null) {
			con.close();
		}

		// } catch (SQLException e) {
		// System.out.println("Error with table or data");
		// }
	}

	public void makeConnection() {
		String url = "jdbc:mysql://localhost/ladybugtracker";
		String password = "syang11";
		String user = "root";

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection made");

		} catch (Exception ex) {
			Logger lgr = Logger.getLogger(LadyBugData.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.out.println("Sql Exception");

		}

	}

	public ArrayList<user> LadyBugUser() {
		ArrayList<user> arrayList = new ArrayList<user>();
		try {
			makeConnection();
			String q = userSqlStr;
			st = con.createStatement();
			rs = st.executeQuery(q);
			while (rs.next()) {
				Tables.user u = new Tables.user();
				u.setUserID(rs.getInt(1));
				u.setFirstName(rs.getString(2));
				u.setLastName(rs.getString(3));
				u.seteMailAdd(rs.getString(4));
				u.setRoleID(rs.getInt(5)); // rs.getInt(5)
				Timestamp times = rs.getTimestamp(6);
				u.setCreatedDate(times);
				times = rs.getTimestamp(7);
				u.setLastModified(times);
				u.getRoleDescription();
				// u.getRoleDescription(rs.getInt(5));
				arrayList.add(u);
			}

			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}

		} catch (SQLException e) {
			System.out.println("Error with table or data");
		}

		return arrayList;
	}

	public ArrayList<ItemList> LadyBugItems() throws SQLException {
		return GetItemsList(" >= 0 ");
	}

	public ArrayList<ItemList> LadyBugItems(int inputType) throws SQLException {
		return GetItemsList(" = " + inputType);
	}

	public ArrayList<ItemList> LadyBugItems(String inputType) throws SQLException {
		int results = 2;
		switch (inputType.toUpperCase()) {
		case "STATUS":
			results = 1;
			break;
		case "ROLE":
			results = 2;
			break;
		case "PRIORITY":
			results = 3;
			break;

		}
		return GetItemsList(" = " + results);
	}

	public ArrayList<ItemList> GetItemsList(String inputType) {
		ArrayList<ItemList> arrayList = new ArrayList<ItemList>();
		try {
			makeConnection();
			String q = itemsSqlStr + inputType + ") ";
			st = con.createStatement();
			rs = st.executeQuery(q);
			while (rs.next()) {
				Tables.ItemList items = new Tables.ItemList();
				items.setID(rs.getInt("ID"));
				items.setDescription(rs.getString("Description"));
				items.setiClass(rs.getString("iClass"));
				items.setiOrder(rs.getInt("iOrder"));
				items.setDropdownListID(rs.getInt("dropdownListID"));
				arrayList.add(items);
			}

			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}

		} catch (SQLException e) {
			System.out.println("Error with table or data");
		}

		return arrayList;
	}

	public ArrayList<dropdownitems> LadyBugDropDownList() {
		ArrayList<dropdownitems> arrayList = new ArrayList<dropdownitems>();
		try {
			makeConnection();
			String q = dropdownItemStr;
			st = con.createStatement();
			rs = st.executeQuery(q);
 			while (rs.next()) {
				Tables.dropdownitems items = new Tables.dropdownitems();
				items.setID(rs.getInt("ID"));
				items.setDropdownListID(rs.getInt("DropdownListID"));
				items.setDescription(rs.getString("Description"));
				items.setiOrder(rs.getInt("iOrder"));
				Timestamp times = rs.getTimestamp("DateCreated");
				items.setCreatedDate(times);
				times = rs.getTimestamp("LastModified");
				items.setLastModified(times);
				arrayList.add(items);
			}

			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}

		} catch (SQLException e) {
			System.out.println("Error with table or data");
		}

		return arrayList;
	}

}