package dev.xsenny.minigame.team;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Team {

    RED(ChatColor.RED + "Red", Material.RED_WOOL),
    BLUE(ChatColor.BLUE + "Blue", Material.BLUE_WOOL),
    GREEN(ChatColor.GREEN + "Green", Material.GREEN_WOOL);

    private String display;
    private Material material;
    Team(String display, Material material){
        this.display = display;
        this.material = material;
    }

    public String getDisplay() { return display; }
    public Material getMaterial() { return material; }

}
