package com.lumagaizen.minecraftshop.manager;

import com.lumagaizen.minecraftshop.SQL;
import com.lumagaizen.minecraftshop.model.ShopUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Taylor
 */
public class UserManager {

	private String userUpdateQStr = "UPDATE `" + SQL.TABLE_USERS() + "` SET `user_id` = ?, `username` = ?, `uuid` = ?, `isAdmin` = ?, `ipv4` = ?, `tokens` = ?, `display_name` = ?, `first_login` = ?, `last_login` = ?, `pw_hash` = ?, `salt` = ? ";
	
	//<editor-fold defaultstate="collapsed" desc="Insert">
    /**
     * Inserts a new user object into the database. UserID does not need to be
     * set for this to work. 
     * @param user
     * @return 
     */
	public boolean insertShopUser(ShopUser user) {
		String q = "INSERT INTO `" + SQL.TABLE_USERS() + "` ("
				+ "`username`, `uuid`, `isAdmin`, `ipv4`,"
				+ " `tokens`, `display_name`, `first_login`,"
				+ " `last_login`, `pw_hash`, `salt`) VALUES "
				+ "( ? , ?, ? , ? , ? , ? , ? , ? , ?, ? );";

		Connection conn = SQL.getConnection();
		try {
			if (conn == null) {
				return false;
			}
			PreparedStatement statement = conn.prepareStatement(q);
			int i = 1;
			statement.setString(i++, user.getUsername());
			statement.setString(i++, user.getUuid());
			statement.setBoolean(i++, user.isAdmin());
			statement.setString(i++, user.getIpv4());
			statement.setInt(i++, user.getTokens());
			statement.setString(i++, user.getDisplayName());
			statement.setLong(i++, user.getFirstLogin());
			statement.setLong(i++, user.getLastLogin());
			statement.setString(i++, user.getPasswordHash());
			statement.setString(i++, user.getSalt());
			int numRowsAffected = statement.executeUpdate();
			return numRowsAffected > 0;
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return false;
	}
	//</editor-fold>
	
	public ArrayList<ShopUser> getAllUsers()
	{
		ArrayList<ShopUser> users = new ArrayList<>();
		String q = "SELECT * FROM `" + SQL.TABLE_USERS() + "`";
		Connection conn = SQL.getConnection();
		try
		{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(q);
			while (rs.next())
			{
				ShopUser user = readShopUser(rs);
				if ( user != null )
				{
					users.add(user);
				}
			}
		}
		catch (SQLException ex)
		{
			Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (Exception ex)
		{
			Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return users;
	}
	

	//<editor-fold defaultstate="collapsed" desc="Retrieve">
	//<editor-fold defaultstate="collapsed" desc="getShopUserByUserId">
     /** Retrieve a user by their userId in the database. This is generally
     * considered to be the fastest method of retrieval due to how the 
     * indexing works.
     * @param userId
     * @return 
     */
	public ShopUser getShopUserByUserId(int userId) {
		Connection conn = SQL.getConnection();
		ShopUser us = null;
		try {
			String q = "SELECT * FROM `" + SQL.TABLE_USERS() + "` WHERE `user_id` = " + userId;
			PreparedStatement statement = conn.prepareStatement(q);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				us = readShopUser(rs);
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return us;
	}
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="getShopUserByUsername">
    /**
     * Retrieve a user by their username in the database. Users can change their
     * names in minecraft, so this is not considered an entirely reliable method
     * of retrieval. Still, if a user is currently logged into the server and
     * their username is what you search for, that will work. 
     * indexing works.
     * @param username
     * @return 
     */
	public ShopUser getShopUserByUsername(String username) {
		Connection conn = null;
		try {
			conn = SQL.getConnection();
			if (conn == null) {
				return null;
			}
			String q = "SELECT * FROM `" + SQL.TABLE_USERS() + "` WHERE `username` = ?";
			PreparedStatement statement = conn.prepareStatement(q);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			SQL.getInstance().returnCon(conn);
			if (rs.next()) {
				return readShopUser(rs);
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
			SQL.getInstance().returnCon(conn);
		}
		return null;
	}
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="getShopUserByUUID">
    /**
     * Retrieve a user by the string representation of their UUID. UUIDs in 
     * minecraft are persistent and will never change for a user unless their
     * UUID is being updated into the DB for the first time. Otherwise, consider
     * it to be a reliable method of user identification. 
     * @param uuidStr
     * @return 
     */
	public ShopUser getShopUserByUUID(String uuidStr) {
		Connection conn = null;
		try {
			conn = SQL.getConnection();
			if (conn == null) {
				return null;
			}
			String q = "SELECT * FROM `" + SQL.TABLE_USERS() + "` WHERE `uuid` = ?";
			PreparedStatement statement = conn.prepareStatement(q);
			statement.setString(1, uuidStr);
			ResultSet rs = statement.executeQuery();
			SQL.getInstance().returnCon(conn);
			if (rs.next()) {
				return readShopUser(rs);
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
			SQL.getInstance().returnCon(conn);
		}
		return null;
	}
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="getShopUsersByIpv4">
    /**
     * Fetches zero to many ShopUsers by a matching Ipv4 address. This method
     * can be used to find alternate accounts. 
     * @param ipv4
     * @return 
     */
	public ArrayList<ShopUser> getShopUsersByIpv4(String ipv4) {
		ArrayList<ShopUser> output = new ArrayList<ShopUser>();
		Connection conn = SQL.getConnection();
		try {
			if (conn == null) {
				return null;
			}
			String q = "SELECT * FROM `" + SQL.TABLE_USERS() + "` WHERE `ipv4` = ?";
			PreparedStatement statement = conn.prepareStatement(q);
			statement.setString(1, ipv4);
			ResultSet rs = statement.executeQuery();
			SQL.getInstance().returnCon(conn);
			while (rs.next()) {
				ShopUser su = readShopUser(rs);
				if (su != null) {
					output.add(su);
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
			SQL.getInstance().returnCon(conn);
		}
		return output;
	}
	//</editor-fold>
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="Update">
	//<editor-fold defaultstate="collapsed" desc="updateShopUserByUserId">
	/**
	 * Overwrites every part of the user in the DB using the ShopUser given.
	 * The key is used to find any user with a matching value as that key.
	 * If a match is found, that match's info will be updated in the DB. 
	 * True is returned if the number of DB rows modified is greater than zero.
	 * @param su
	 * @param key
	 * @return 
	 */
	public boolean updateShopUserByUserId(ShopUser su, int key) {
		boolean output = false;
		Connection conn = SQL.getConnection();
		try {
			if (conn != null) {
				PreparedStatement statement = conn.prepareStatement(userUpdateQStr + " WHERE `user_id` = " + key);
				int i = 1;
				statement.setInt(i++, su.getUserId());
				statement.setString(i++, su.getUsername());
				statement.setString(i++, su.getUuid());
				statement.setBoolean(i++, su.isAdmin());
				statement.setString(i++, su.getIpv4());
				statement.setInt(i++, su.getTokens());
				statement.setString(i++, su.getDisplayName());
				statement.setLong(i++, su.getLastLogin());
				statement.setLong(i++, su.getFirstLogin());
				statement.setString(i++, su.getPasswordHash());
				statement.setString(i++, su.getSalt());
				output = statement.executeUpdate() > 0;
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return output;
	}
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="updateShopUserByUsername">
    /**
	 * Overwrites every part of the user in the DB using the ShopUser given.
	 * The key is used to find any user with a matching value as that key.
	 * If a match is found, that match's info will be updated in the DB. 
	 * True is returned if the number of DB rows modified is greater than zero.
	 * @param su
	 * @param key
	 * @return 
	 */
	public boolean updateShopUserByUsername(ShopUser su, String key) {
		boolean output = false;
		Connection conn = SQL.getConnection();
		try {
			if (conn != null) {
				PreparedStatement statement = conn.prepareStatement(userUpdateQStr + " WHERE `username` = ?");
				int i = 1;
				statement.setInt(i++, su.getUserId());
				statement.setString(i++, su.getUsername());
				statement.setString(i++, su.getUuid());
				statement.setBoolean(i++, su.isAdmin());
				statement.setString(i++, su.getIpv4());
				statement.setInt(i++, su.getTokens());
				statement.setString(i++, su.getDisplayName());
				statement.setLong(i++, su.getLastLogin());
				statement.setLong(i++, su.getFirstLogin());
				statement.setString(i++, su.getPasswordHash());
				statement.setString(i++, su.getSalt());

				// Key to find
				statement.setString(i++, key);

				output = statement.executeUpdate() > 0;
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return output;
	}
	//</editor-fold>
	
	
	//<editor-fold defaultstate="collapsed" desc="updateShopUserByUUID">
	/**
	 * Overwrites every part of the user in the DB using the ShopUser given.
	 * The key is used to find any user with a matching value as that key.
	 * If a match is found, that match's info will be updated in the DB. 
	 * True is returned if the number of DB rows modified is greater than zero.
	 * @param su
	 * @param key
	 * @return 
	 */
	public boolean updateShopUserByUUID(ShopUser su, String key) {
		boolean output = false;
		Connection conn = SQL.getConnection();
		try {
			if (conn != null) {
				PreparedStatement statement = conn.prepareStatement(userUpdateQStr + " WHERE `uuid` = ?");
				int i = 1;
				statement.setInt(i++, su.getUserId());
				statement.setString(i++, su.getUsername());
				statement.setString(i++, su.getUuid());
				statement.setBoolean(i++, su.isAdmin());
				statement.setString(i++, su.getIpv4());
				statement.setInt(i++, su.getTokens());
				statement.setString(i++, su.getDisplayName());
				statement.setLong(i++, su.getLastLogin());
				statement.setLong(i++, su.getFirstLogin());
				statement.setString(i++, su.getPasswordHash());
				statement.setString(i++, su.getSalt());

				// Key to find
				statement.setString(i++, key);

				output = statement.executeUpdate() > 0;
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return output;
	}
	//</editor-fold>
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="Delete">
	/**
	 * Deletes users that have matching specified key. Returns TRUE if 
	 * more than 0 rows are updated. 
	 * @param uuidStr
	 * @return 
	 */
	public boolean deleteShopUserByUUID(String uuidStr) {
		boolean output = false;
		Connection conn = SQL.getConnection();
		try {
			if (conn != null) {

				String q = "DELETE FROM `" + SQL.TABLE_USERS() + "` WHERE  `uuid`=?";
				PreparedStatement statement = conn.prepareStatement(q);
				statement.setString(1, uuidStr);
				output = statement.executeUpdate() > 0;
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return output;
	}

    /**
	 * Deletes users that have matching specified key. Returns TRUE if 
	 * more than 0 rows are updated. 
	 */
	public boolean deleteShopUserByUsername(String username) {
		boolean output = false;
		SQL sql = SQL.getInstance();
		Connection conn = sql.getCon();
		try {
			if (conn != null) {

				String q = "DELETE FROM `" + SQL.TABLE_USERS() + "` WHERE  `username`=?";
				PreparedStatement statement = conn.prepareStatement(q);
				statement.setString(1, username);
				output = statement.executeUpdate() > 0;
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		sql.returnCon(conn);
		return output;
	}

    /**
	 * Deletes users that have matching specified key. Returns TRUE if 
	 * more than 0 rows are updated. 
	 * 
	 * @param userId
	 * @return 
	 */
	public boolean deleteShopUserByUserId(int userId) {
		boolean output = false;
		SQL sql = SQL.getInstance();
		Connection conn = sql.getCon();
		try {
			if (conn != null) {

				String q = "DELETE FROM `" + SQL.TABLE_USERS() + "` WHERE  `user_id`=?";
				PreparedStatement statement = conn.prepareStatement(q);
				statement.setInt(1, userId);
				output = statement.executeUpdate() > 0;
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		sql.returnCon(conn);
		return output;
	}
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="Read from DB">
	/**
	 * Expects that the result set has been primed with rs.next() before this
	 * method is called. This method then reads in all the values of the RS
	 * object to a new ShopUser object. Returns null on failure.
	 *
	 * @param rs
	 * @return
	 */
	protected ShopUser readShopUser(ResultSet rs) {
		try {
			ShopUser su = new ShopUser();
			su.setUserId(rs.getInt("user_id"));
			su.setUsername(rs.getString("username"));
			su.setUuid(rs.getString("uuid"));
			su.setIsAdmin(rs.getInt("isAdmin") == 1);
			su.setDisplayName(rs.getString("display_name"));
			su.setFirstLogin(rs.getLong("first_login"));
			su.setLastLogin(rs.getLong("last_login"));
			su.setIpv4(rs.getString("ipv4"));
			su.setPasswordHash(rs.getString("pw_hash"));
			su.setSalt(rs.getString("salt"));
			su.setTokens(rs.getInt("tokens"));
			return su;
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	//</editor-fold>
}
