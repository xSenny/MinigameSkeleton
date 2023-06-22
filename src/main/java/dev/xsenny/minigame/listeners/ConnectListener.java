package dev.xsenny.minigame.listeners;

import dev.xsenny.minigame.MinigameSkeleton;
import dev.xsenny.minigame.instance.Arena;
import dev.xsenny.minigame.managers.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    private MinigameSkeleton plugin;

    public ConnectListener(MinigameSkeleton plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        e.getPlayer().teleport(ConfigManager.getLobbySpawn());

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        Player p = e.getPlayer();
        Arena arena = plugin.getArenaManager().getArena(p);
        if (arena != null){
            arena.removePlayer(p);
        }

    }

}
