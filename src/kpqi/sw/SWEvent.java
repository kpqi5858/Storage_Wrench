package kpqi.sw;

import java.util.ArrayList;
import java.util.List;

import kpqi.sw.config.CustomConfig;
import kpqi.sw.utill.SID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SWEvent implements Listener {
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (isStorage(e.getItemInHand().getType())) {
			ItemStack is = e.getItemInHand();
			
			if (is.getItemMeta().hasLore()) {
				if (is.getItemMeta().getLore().get(0).contains("Wrenched")) {
					SID s = new SID(ChatColor.stripColor(is.getItemMeta().getLore().get(1)));
					ItemStack[] it = CustomConfig.getWrenched(s);
					InventoryHolder ih = (InventoryHolder) e.getBlockPlaced().getState();
					ih.getInventory().setContents(it);
				}
			}
		} else return;
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if (e.getPlayer().isSneaking() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			try {
				
			if (e.getClickedBlock().getState() instanceof InventoryHolder && !(e.getClickedBlock().getState() instanceof Furnace) && !(e.getClickedBlock().getState() instanceof BrewingStand)) {
				Player p = e.getPlayer();
				if (p.hasPermission(SWPermissions.WRENCH_STORAGE)) {
					
					if (e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
					InventoryHolder i = (InventoryHolder) e.getClickedBlock().getState();
					SID s = SID.random();
					CustomConfig.setupWrenched(i.getInventory().getContents(), s);
					
					ItemStack it = new ItemStack(e.getClickedBlock().getType(), 1);
					ItemMeta im = it.getItemMeta();
					
					List<String> l = new ArrayList<String>();
					
					l.add("」r」aWrenched");
					l.add("」r」0" + s.toString());
					
					im.setLore(l);
					
					it.setItemMeta(im);
					
					i.getInventory().clear();
					e.getClickedBlock().setType(Material.AIR);
					
					e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), it);
					} else return;
				} else return;
			} else return;
			
			
			} catch (Exception ex) {
				return;
			}
		} else return;
	}
	
	private boolean isStorage(Material m) { 
		switch (m) {
		case CHEST: return true;
		case HOPPER: return true;
		case TRAPPED_CHEST: return true;
		case DISPENSER: return true;
		case DROPPER: return true;
		//case BREWING_STAND: return true;
		
		default: return false;
		
		}
	}
}
