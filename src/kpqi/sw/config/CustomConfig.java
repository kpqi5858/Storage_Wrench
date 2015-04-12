package kpqi.sw.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import kpqi.sw.MainSW;
import kpqi.sw.utill.SID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 * http://wiki.bukkit.org/Configuration_API_Reference + My Code
 * 
 * @author kpqi5858
 */
public class CustomConfig {
	
	private static FileConfiguration customConfig = null;
	public static File customConfigFile = null;
	@SuppressWarnings("unused")
	private static CustomConfig inst = new CustomConfig();
	
	public static void reloadCustomConfig() {
	    if (customConfigFile == null) {
	    customConfigFile = new File(MainSW.plugin.getDataFolder(), "StorageData.yml");
	    }
	    File dir = MainSW.plugin.getDataFolder();
	    if (!dir.exists()) {
	    	dir.mkdir();
	    }
	    
	    if(!customConfigFile.exists()) {
    		try {
				customConfigFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	 
	    // Look for defaults in the jar
	    Reader defConfigStream = null;
		try {
			try {
				defConfigStream = new InputStreamReader(new FileInputStream(customConfigFile), "UTF8");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        customConfig.setDefaults(defConfig);
	    }
	}
	
	public static void saveCustomConfig() {
	    if (customConfig == null || customConfigFile == null) {
	        return;
	    }
	    try {
	        gcc().save(customConfigFile);
	    } catch (IOException ex) {
	        MainSW.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
	
	public static FileConfiguration gcc() {
	    if (customConfig == null) {
	        reloadCustomConfig();
	    }
	    return customConfig;
	}
	
	public static void setupWrenched(ItemStack[] i, SID c) {
		gcc().options().copyDefaults(true);
		for (int a = 0; a < i.length; a++) {
			gcc().addDefault("sw." + c.toString() + "." + a, i[a]);
		}
		gcc().addDefault("sw." + c.toString() + ".length", i.length);
		saveCustomConfig();
		reloadCustomConfig();
	}
	
	public static ItemStack[] getWrenched(SID c) {
		int length = gcc().getInt("sw." + c.toString() + ".length");
		
		ItemStack[] it = new ItemStack[length];
		
		for (int a = 0; a < length; a++) {
			it[a] = gcc().getItemStack("sw." + c.toString() + "." + a);
		}
		
		return it;
	}
}