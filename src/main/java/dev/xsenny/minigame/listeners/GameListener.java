package dev.xsenny.minigame.listeners;

import dev.xsenny.minigame.GameState;
import dev.xsenny.minigame.MinigameSkeleton;
import dev.xsenny.minigame.instance.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GameListener implements Listener {

    private MinigameSkeleton plugin;

    public GameListener(MinigameSkeleton plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        Arena arena = plugin.getArenaManager().getArena(p);
        if (arena != null){
            if (arena.getGameState() == GameState.LIVE){
                arena.getGame().addPoint(p);
            }
        }
    }

}
