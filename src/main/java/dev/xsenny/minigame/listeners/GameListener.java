package dev.xsenny.minigame.listeners;

import dev.xsenny.minigame.GameState;
import dev.xsenny.minigame.MinigameSkeleton;
import dev.xsenny.minigame.instance.Arena;
import dev.xsenny.minigame.kit.KitType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GameListener implements Listener {

    private MinigameSkeleton plugin;

    public GameListener(MinigameSkeleton plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().contains("Kit Selector") && e.getInventory() != null && e.getCurrentItem() != null){
            e.setCancelled(true);
            KitType type = KitType.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName().toUpperCase());
            Arena arena = plugin.getArenaManager().getArena(p);
            if (arena != null){
                KitType activated = arena.getKit(p);
                if (activated != null && activated.equals(type)){
                    p.sendMessage("You already have this kit.");
                } else {
                    p.sendMessage(ChatColor.RED + "You have equipped the " + type.getDisplay() + ChatColor.GREEN + " kit!");
                    arena.setKit(p.getUniqueId(), type);
                }

                p.closeInventory();
            }
        }
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
