package io.github.nicknazari.DataStorage;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DataStorage {
	private static HashMap<Player, Inventory> invenMap = new HashMap<Player, Inventory>();
	public static HashMap<Player, Inventory> getInvenMap() {
		return invenMap;
	}
}
