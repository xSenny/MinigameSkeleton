package dev.xsenny.minigame.kit;

import dev.xsenny.minigame.MinigameSkeleton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.UUID;

public abstract class Kit implements Listener {

    protected KitType type;
    protected UUID uuid;
    public Kit(MinigameSkeleton plugin, KitType type, UUID uuid){
        this.type = type;
        this.uuid = uuid;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public UUID getUuid(){ return uuid; }
    public KitType getType() { return type; }

    public abstract void onStart(Player player);

    public void remove(){
        HandlerList.unregisterAll(this);
    }

}
