package dev.xsenny.minigame;

import dev.xsenny.minigame.commands.ArenaCommand;
import dev.xsenny.minigame.listeners.ConnectListener;
import dev.xsenny.minigame.listeners.GameListener;
import dev.xsenny.minigame.managers.ArenaManager;
import dev.xsenny.minigame.managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinigameSkeleton extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigManager.setupConfig(this);

        arenaManager = new ArenaManager(this);

        getServer().getPluginManager().registerEvents(new ConnectListener(this), this);
        getServer().getPluginManager().registerEvents(new GameListener(this), this);

        getCommand("arena").setExecutor(new ArenaCommand(this));
    }

    public ArenaManager getArenaManager(){ return arenaManager; }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
