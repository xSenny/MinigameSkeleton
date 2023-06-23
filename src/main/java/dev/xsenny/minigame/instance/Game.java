package dev.xsenny.minigame.instance;

import dev.xsenny.minigame.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private Arena arena;
    private HashMap<UUID, Integer> points;
    public Game(Arena arena){
        this.arena = arena;

        this.points = new HashMap<>();
    }

    public void start(){
        arena.setGameState(GameState.LIVE);
        arena.sendMessage(ChatColor.GREEN + "GAME HAS STARTED, YOUR OBJECTIVE IS TO BREAK 20 BLOCKS.");

        for (UUID uuid : arena.getKits().keySet()){
            arena.getKits().get(uuid).onStart(Bukkit.getPlayer(uuid));
        }

        for (UUID uuid : arena.getPlayers()){
            points.put(uuid, 0);
            Bukkit.getPlayer(uuid).closeInventory();
        }
    }

    public void addPoint(Player p){
        int playerPoints = points.get(p.getUniqueId()) + 1;
        if (playerPoints == 20){
            arena.sendMessage(ChatColor.GOLD + p.getName() + " has won. Thanks for playing. :)");
            arena.reset(true);
            return;
        }
        points.replace(p.getUniqueId(), playerPoints);
        p.sendMessage(ChatColor.GREEN + "+1 point");
    }

}
