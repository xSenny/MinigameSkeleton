package dev.xsenny.minigame.instance;

import dev.xsenny.minigame.GameState;
import dev.xsenny.minigame.MinigameSkeleton;
import dev.xsenny.minigame.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private int id;
    private Location spawn;
    private Game game;

    private MinigameSkeleton plugin;
    private GameState gameState;
    private List<UUID> players;
    private Countdown countdown;
    public Arena(MinigameSkeleton plugin, int id, Location spawn){
        this.id = id;
        this.spawn = spawn;

        this.gameState = GameState.RECRUITING;
        this.plugin = plugin;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(this, plugin);
        this.game = new Game(this);
    }

    public void sendMessage(String message){
        for (UUID uuid : players){
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle){
        for (UUID uuid : players){
            Bukkit.getPlayer(uuid).sendTitle(title, subtitle);
        }
    }

    public Game getGame() { return game; }

    public void addPlayer(Player p){
        players.add(p.getUniqueId());
        p.teleport(spawn);

        if (gameState == GameState.RECRUITING && players.size() >= ConfigManager.getRequiredPlayers()){
            countdown.start();
        }
    }

    public void start(){
        game.start();
    }

    public void reset(boolean kickPlayers){
        if (kickPlayers){
            Location location = ConfigManager.getLobbySpawn();
            for (UUID uuid : players){
                Bukkit.getPlayer(uuid).teleport(location);
            }
            players.clear();
        }
        sendTitle("", "");
        gameState = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(this, plugin);
        game = new Game(this);
    }


    public void removePlayer(Player p){
        players.remove(p.getUniqueId());
        p.teleport(ConfigManager.getLobbySpawn());
        p.sendTitle("", "");

        if (gameState == GameState.COUNTDOWN && players.size() < ConfigManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "There is not enough players. Countdown stopped.");
            reset(false);
            return;
        }

        if (gameState == GameState.LIVE && players.size() < ConfigManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "The game has ended as too many players have left.");
            reset(false);

        }

    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public void setPlayers(List<UUID> players) {
        this.players = players;
    }


}
