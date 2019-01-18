package io.github.nicknazari.VChest;

import org.bukkit.Bukkit;

import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.nicknazari.DataStorage.DataStorage;

public final class VChest extends JavaPlugin {
	@Override
    public void onEnable() {
    	getLogger().info("VChest 1 ENABLE\n");
    }
	
    @Override
    public void onDisable() {
    	getLogger().info("VChest v1 DISABLE\n");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	
    	if (cmd.getName().equalsIgnoreCase("vc")) {
    		
    		Player player = (Player) sender;
    		
    		HashMap<Player, Inventory> inventories = DataStorage.getInvenMap();
    		
    		if (!(inventories.containsKey(player))) {
    			Inventory chest = Bukkit.getServer().createInventory(null, 54);
        		inventories.put(player, chest);     		
    		} else {
    			player.openInventory(inventories.get(player));
        		inventories.put(player, inventories.get(player));
    		}
    		
    		
    		
    		
    		return true;
    	}
    		
    	
    	return false;
    }
}
