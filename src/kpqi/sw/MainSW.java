package kpqi.sw;

import kpqi.sw.config.CustomConfig;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MainSW extends JavaPlugin {
	
	public static Plugin plugin;
	
	public void onEnable() {
		plugin = this;
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new SWEvent(), this);
		CustomConfig.reloadCustomConfig();
		getLogger().info("!--Storage Wrench--!");
		getLogger().info("Made by ¡×aKpqi5858");
		getLogger().info("Kpqi5858ÀÌ ¸¸µë."); //Korean
	}
	
	public void onDisable() {
		
	}
}