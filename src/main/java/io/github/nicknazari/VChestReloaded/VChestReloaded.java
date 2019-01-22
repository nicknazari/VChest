package io.github.nicknazari.VChestReloaded;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class VChestReloaded extends JavaPlugin {
	HashMap<Player, Inventory> inventories = new HashMap<Player, Inventory>();
	
	@Override
	public void onEnable() {
		getLogger().info("VChestReloaded started");
		
		// read config values in to inventories HashMap
		
	}
	
	public void writeInventory(Player P) {
		for (int i = 0; i < inventories.get(P).getSize(); i++)  {
			ItemStack currentItem = inventories.get(P).getItem(i);
			// itemList.add(currentItem);
			getConfig().set(P.getName() + "." + i, currentItem);;
		}
		saveConfig();
		reloadConfig();
	}
	
	public void readInventory(Player P) {
		// this should update the hashmap with the most recent values stored in the config
		
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		int counter = 0;
		for (int i = 0; i < 54; i++) {
			
			ItemStack currentItem = getConfig().getItemStack((P.getName() + "." + counter));
			items.add(currentItem);
			counter++;
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, P.getName() + "'s Virtual Chest");
		inv.setContents(items.toArray(new ItemStack[0]));
		
		inventories.put(P, inv);
		
		P.sendMessage(inventories.get(P).getContents().toString());
	}
	
	public void saveInventory(Player P) {

		Inventory inv = inventories.get(P);

		File file = new File("plugins/VChestReloadedData/"+P.getUniqueId()+".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		for (int i = 0; i < 54; i++) {
		   if (inv.getItem(i) != null) {
		       config.set("Slot." + i, inv.getItem(i));
		   }
		}
		try {
		   config.save(file);
		} catch (IOException e) {
		   e.printStackTrace();
		}

	}
	
	public void getInventory(Player P) {
		File file = new File("plugins/VChestReloadedData/"+P.getUniqueId()+".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		Inventory inv = Bukkit.createInventory(null, 54, P.getName() + "'s Virtual Chest");
		
		for (int i = 0; i < 54; i++) {
		   if (config.get("Slot." + i) != null) {
		       inv.addItem(config.getItemStack("Slot." + i));
		   }
		}
		
		inventories.put(P, inv);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player P = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("vc")) {
			P.sendMessage(ChatColor.GRAY + "Opening VChest.");
			
			if (inventories.get(P) != null) {
				// readInventory(P);
				getInventory(P);
				
				P.openInventory(inventories.get(P));
				
				// writeInventory(P);
				
				saveInventory(P);

				getLogger().info("Saved " + P.getName() + " vchest.");
				
				return true;
				
			} else {
				// in the case that a player doesn't have a vchest, we will make one
				
				Inventory inv = Bukkit.createInventory(null, 54, P.getName() + "'s Virtual Chest");
				inventories.put(P, inv);
				P.openInventory(inventories.get(P));
				
				// writeInventory(P);
				
				saveInventory(P);
				
				getLogger().info("Saved " + P.getName() + " vchest.");
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("getinventories")) {
			for (Entry<Player, Inventory> entry : inventories.entrySet()) {
				P.sendMessage(entry.getKey().getName() + " : " + entry.getValue().getSize());
				P.sendMessage("inventories array length: " + inventories.size());
				return true;
			}
		}
		
		return true;
	}
	
	@Override
	public void onDisable() {
		getLogger().info("VChestReloaded stopped");
	}
	
}