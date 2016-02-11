package com.lumagaizen.minecraftshop.model;

import com.lumagaizen.minecraftshop.STATIC;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Taylor
 */
public class ShopUser implements Serializable
{

	// Information we control.
	private int userId = 0;
	private Timestamp lastUpdated = null;
	private int tokens = 100000;
	private boolean isAdmin = false;
	private String username = "unique";
	private String ipv4 = null;
	private String uuid = null;
	private long lastLogin = 0;
	private long firstLogin = 0;

	// Information that requires user input at some point.
	private String displayName = null;
	private String salt = null;
	private String passwordHash = null;

	public boolean isAdmin()
	{
		return isAdmin;
	}
	
	public void setIsAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}
	
	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public long getFirstLogin()
	{
		return firstLogin;
	}

	public void setFirstLogin(long firstLogin)
	{
		this.firstLogin = firstLogin;
	}

	public Timestamp getLastUpdated()
	{
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}

	public int getTokens()
	{
		return tokens;
	}

	public void setTokens(int tokens)
	{
		this.tokens = tokens;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getIpv4()
	{
		return ipv4;
	}

	public void setIpv4(String ipv4)
	{
		this.ipv4 = ipv4;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public long getLastLogin()
	{
		return lastLogin;
	}

	public void setLastLogin(long lastLogin)
	{
		this.lastLogin = lastLogin;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	/**
	 * If salt is null, a new salt will be generated.
	 *
	 * @return
	 */
	public String getSalt()
	{
		if (salt == null)
		{
			salt = generateFreshSalt();
		}
		return salt;
	}

	public void setSalt(String salt)
	{
		this.salt = salt;
	}

	public String getPasswordHash()
	{
		return passwordHash;
	}

	public String generateFreshSalt()
	{
		return STATIC.md5("" + System.currentTimeMillis());
	}

	/**
	 * Returns null if an exception occurs. Otherwise returns a SHA-256 hash of
	 * (rawPassword + salt) Makes NO changes to the current ShopUser object.
	 *
	 * @param rawPassword
	 * @return
	 */
	public String generatePasswordHash(String rawPassword)
	{
		return STATIC.Sha256(rawPassword);
	}

	/**
	 * Assumes the input is already the correct hash of the raw password. Sets
	 * the password to be exactly equal to the input parameter.
	 *
	 * @param passwordHash
	 */
	public void setPasswordHash(String passwordHash)
	{
		this.passwordHash = passwordHash;
	}

	/**
	 * Generates the hash of the raw password, then sets the passwordHash to
	 * equal the result. Returns false if an exception occurred.
	 *
	 * @param rawPassword
	 * @return
	 */
	public boolean setPasswordByRawValue(String rawPassword)
	{
		String hash = generatePasswordHash(rawPassword);
		if (hash == null)
		{
			return false;
		}
		else
		{
			this.passwordHash = hash;
			return true;
		}
	}

}
