package dev.xsenny.minigame.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum KitType {

    MINER(ChatColor.GRAY + "Miner", Material.DIAMOND_PICKAXE, "Best mining kit!"),
    FIGHTER(ChatColor.RED + "Fighter", Material.DIAMOND_SWORD, "Best fighting kit!");

    private String display;
    private String description;
    private Material material;
    KitType(String display, Material material, String description){
        this.display = display;
        this.description = description;
        this.material = material;
    }

    public String getDisplay() { return display; }
    public String getDescription() { return description; }
    public Material getMaterial() { return material; }

}
