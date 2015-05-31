package kpqi.sw;

import java.util.ArrayList;
import java.util.List;

import kpqi.sw.config.CustomConfig;
import kpqi.sw.utill.SID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
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
					SID s = new SID(ChatColor.stripColor(is.getItemMeta().getLore().get(3)));
					if (CustomConfig.isCorrectPlayer(s, e.getPlayer().getUniqueId().toString())) {
					ItemStack[] it = CustomConfig.getWrenched(s);
					if (getDoubleChest(e.getBlockPlaced().getLocation())[1] != null) {
						Chest c = (Chest) e.getBlockPlaced().getState();
						c.getBlockInventory().setContents(it);
						return;
					}
					InventoryHolder ih = (InventoryHolder) e.getBlockPlaced().getState();
					ih.getInventory().setContents(it);
					CustomConfig.delete(s);
					} else {
						e.getPlayer().sendMessage("」eThis 」aStorage 」eis Locked.");
						e.setCancelled(true);
					}
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
					if (e.getClickedBlock().getState() instanceof Chest) {
						Chest chh = (Chest) e.getClickedBlock().getState();
						
						if (chh.getInventory().getHolder() instanceof DoubleChest) {
							

							
							if (getDoubleChest(e.getClickedBlock().getLocation())[1] != null) {
							if (e.getPlayer().hasPermission(SWPermissions.WRENCH_STORAGE)) {
							DoubleChest c = (DoubleChest) chh.getInventory().getHolder();
							InventoryHolder left = c.getLeftSide();
							InventoryHolder right = c.getLeftSide();
							
							SID s1 = SID.random();
							SID s2 = SID.random();
							
							CustomConfig.setupWrenched(fix(left.getInventory().getContents(),true), s1, e.getPlayer().getUniqueId().toString());
							CustomConfig.setupWrenched(fix(right.getInventory().getContents(),false), s2, e.getPlayer().getUniqueId().toString());
							ItemStack it1 = new ItemStack(e.getClickedBlock().getType(), 1);
							ItemStack it2 = new ItemStack(e.getClickedBlock().getType(), 1);

							ItemMeta im1 = it1.getItemMeta();
							ItemMeta im2 = it2.getItemMeta();
							
							List<String> l1 = new ArrayList<String>();
							List<String> l2 = new ArrayList<String>();

							l1.add("」r」aWrenched");
							l1.add("」r」aLock : 」cfalse");
							l1.add("」r」aPlayer : 」9" + e.getPlayer().getName());
							l1.add("」r」0" + s1.toString());
							
							l2.add("」r」aWrenched");
							l2.add("」r」aLock : 」cfalse");
							l2.add("」r」aPlayer : 」9" + e.getPlayer().getName());
							l2.add("」r」0" + s2.toString());
							
							im1.setLore(l1);
							im2.setLore(l2);
							
							it1.setItemMeta(im1);
							it2.setItemMeta(im2);
							
							left.getInventory().clear();
							right.getInventory().clear();
							
							
							
							
								getDoubleChest(e.getClickedBlock().getLocation())[0].setType(Material.AIR);
								getDoubleChest(e.getClickedBlock().getLocation())[1].setType(Material.AIR);

								e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), it1);
								e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), it2);
								return;
							}}
						}}
					if (e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
					InventoryHolder i = (InventoryHolder) e.getClickedBlock().getState();
					
					if (isEmpty(i.getInventory().getContents())) return;
					SID s = SID.random();
					
					CustomConfig.setupWrenched(i.getInventory().getContents(), s, e.getPlayer().getUniqueId().toString());
					
					ItemStack it = new ItemStack(e.getClickedBlock().getType(), 1);
					ItemMeta im = it.getItemMeta();
					
					List<String> l = new ArrayList<String>();
					
					l.add("」r」aWrenched");
					l.add("」r」aLock : 」cfalse");
					l.add("」r」aPlayer : 」9" + e.getPlayer().getName());
					l.add("」r」0" + s.toString());
					
					im.setLore(l);
					
					it.setItemMeta(im);
					
					i.getInventory().clear();
					e.getClickedBlock().setType(Material.AIR);
					
					e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), it);
					
					
				}
			}
			/*if (e.getClickedBlock().getState() instanceof Chest) {
			Chest chh = (Chest) e.getClickedBlock().getState();
			System.out.println(1);
			if (chh.getInventory().getHolder() instanceof DoubleChest) { 
				System.out.println(2);

					
				if (getDoubleChest(e.getClickedBlock().getLocation())[1] != null) {
				if (e.getPlayer().hasPermission(SWPermissions.WRENCH_STORAGE)) {
				DoubleChest c = (DoubleChest) e.getClickedBlock().getState();
				System.out.println(1);
				InventoryHolder left = c.getLeftSide();
				InventoryHolder right = c.getLeftSide();
				
				SID s1 = SID.random();
				SID s2 = SID.random();
				
				CustomConfig.setupWrenched(left.getInventory().getContents(), s1, e.getPlayer().getUniqueId().toString());
				CustomConfig.setupWrenched(right.getInventory().getContents(), s2, e.getPlayer().getUniqueId().toString());
				ItemStack it1 = new ItemStack(e.getClickedBlock().getType(), 1);
				ItemStack it2 = new ItemStack(e.getClickedBlock().getType(), 1);

				ItemMeta im1 = it1.getItemMeta();
				ItemMeta im2 = it2.getItemMeta();
				
				List<String> l1 = new ArrayList<String>();
				List<String> l2 = new ArrayList<String>();

				l1.add("」r」aWrenched");
				l1.add("」r」aLock : 」cfalse");
				l1.add("」r」aPlayer : 」9" + e.getPlayer().getName());
				l1.add("」r」0" + s1.toString());
				
				l2.add("」r」aWrenched");
				l2.add("」r」aLock : 」cfalse");
				l2.add("」r」aPlayer : 」9" + e.getPlayer().getName());
				l2.add("」r」0" + s2.toString());
				
				im1.setLore(l1);
				im2.setLore(l2);
				
				it1.setItemMeta(im1);
				it2.setItemMeta(im2);
				
				left.getInventory().clear();
				right.getInventory().clear();
				
				
				
				
					getDoubleChest(e.getClickedBlock().getLocation())[0].setType(Material.AIR);
					getDoubleChest(e.getClickedBlock().getLocation())[1].setType(Material.AIR);

					e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), it1);
					e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), it2);

				}

			} else {
				
			}
			}
			}*/
			}}} catch (Exception ex) {
				ex.printStackTrace();
			}
				
			}
			
			
		
	
	}
	
	@EventHandler()
	public void onIteract2(PlayerInteractEvent e) {
	if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
		if (e.getPlayer().isSneaking()) {
			if (isStorage(e.getPlayer().getItemInHand().getType())) {
				ItemStack is = e.getPlayer().getItemInHand();
				if (is.getItemMeta().hasLore()) {
					if (is.getItemMeta().getLore().get(0).contains("Wrenched")) {
						SID s = new SID(ChatColor.stripColor(is.getItemMeta().getLore().get(3)));
						if (CustomConfig.isCorrectPlayer(s, e.getPlayer().getUniqueId().toString()))
						if (!CustomConfig.isPrivate(s)) {
							e.getPlayer().sendMessage("」aStorage」e is now lock.");
							
							ItemMeta mt = is.getItemMeta();
							List<String> li = mt.getLore();
							li.set(1, "」r」aLock : 」atrue");
							mt.setLore(li);
							is.setItemMeta(mt);
							
							CustomConfig.setPrivate(s, true, e.getPlayer().getUniqueId().toString());
						} else {
							e.getPlayer().sendMessage("」aStorage」e is unlocked.");
							
							ItemMeta mt = is.getItemMeta();
							List<String> li = mt.getLore();
							li.set(1, "」r」aLock : 」cfalse");
							mt.setLore(li);
							is.setItemMeta(mt);
							
							CustomConfig.setPrivate(s, false, e.getPlayer().getUniqueId().toString());
						}
					}
				}
			}
			} else {
				
			}
		
	}

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
	
	private final BlockFace[] faces = new BlockFace[] {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST }; 	

	private Block[] getDoubleChest(Location l) {
		Block[] b = new Block[2];
		b[0] = l.getBlock();
		b[1] = null;
		for (BlockFace f : faces) {
			if (b[0].getRelative(f).getType().equals(Material.CHEST)) {
				b[1] = b[0].getRelative(f);
				break;
			}
		}
		return b;
	}
	
	private boolean isEmpty(ItemStack[] i) {
		for (ItemStack t : i) {
			if (t != null) return false;
		}
		return true;
	}
	
	private ItemStack[] fix(ItemStack[] i,boolean b) {
		ItemStack[] it = new ItemStack[27];
		
		if (b) {
			System.arraycopy(i, 0, it, 0, 27);
		} else {
			System.arraycopy(i, 27, it, 0, 27);
		}
		return it;
	}
	
	@SuppressWarnings("unused")
	private ItemStack[] combine(ItemStack[] t1, ItemStack[] t2) {
		ItemStack[] i = new ItemStack[54];
		int p = 0;
		for (ItemStack temp : t1) {
			i[p] = temp;
			p++;
		}
		for (ItemStack temp : t2) {
			i[p] = temp;
			p++;
		}
		return i;
	}
}
