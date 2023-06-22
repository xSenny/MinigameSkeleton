package dev.xsenny.minigame.instance;

import dev.xsenny.minigame.GameState;
import dev.xsenny.minigame.MinigameSkeleton;
import dev.xsenny.minigame.managers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private MinigameSkeleton plugin;
    private Arena arena;
    private int countdown;

    public Countdown(Arena arena, MinigameSkeleton plugin){
        this.plugin = plugin;
        this.arena = arena;
        this.countdown = ConfigManager.getCountdownSeconds();
    }

    public void start(){
        arena.setGameState( GameState.COUNTDOWN);
        runTaskTimer(plugin, 0, 20);
    }


    @Override
    public void run() {
        if (countdown == 0 ){
            cancel();
            arena.start();
            return;
        }

        if (countdown <= 10 || countdown % 15 == 0){
            arena.sendMessage(ChatColor.GREEN + "Game will start in " + countdown + " second" + (countdown == 1 ? "" : "s") + ".");
        }

        arena.sendTitle(ChatColor.GREEN.toString() + countdown + " second " + (countdown == 1 ? "" : "s"), ChatColor.GRAY + "until game starts.");

        countdown --;
    }
}
