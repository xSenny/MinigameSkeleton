package dev.xsenny.minigame.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class KitUI {

    public KitUI(Player player){
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Kit Selector");

        for (KitType type : KitType.values()){
            ItemStack item = new ItemStack(type.getMaterial());
            ItemMeta isMeta = item.getItemMeta();
            isMeta.setDisplayName(type.getDisplay());
            isMeta.setLore(Arrays.asList(type.getDescription()));
            isMeta.setLocalizedName(type.name());
            item.setItemMeta(isMeta);

            gui.addItem(item);
        }

        player.openInventory(gui);
    }

}
