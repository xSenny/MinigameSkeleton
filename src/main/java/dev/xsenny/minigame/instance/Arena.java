package dev.xsenny.minigame.instance;

import com.google.common.collect.TreeMultimap;
import dev.xsenny.minigame.GameState;
import dev.xsenny.minigame.MinigameSkeleton;
import dev.xsenny.minigame.kit.Kit;
import dev.xsenny.minigame.kit.KitType;
import dev.xsenny.minigame.kit.type.FighterKit;
import dev.xsenny.minigame.kit.type.MinerKit;
import dev.xsenny.minigame.managers.ConfigManager;
import dev.xsenny.minigame.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Arena {

    private int id;
    private Location spawn;
    private Game game;

    private MinigameSkeleton plugin;
    private GameState gameState;
    private List<UUID> players;
    private HashMap<UUID, Team> teams;
    private HashMap<UUID, Kit> kits;
    private Countdown countdown;
    public Arena(MinigameSkeleton plugin, int id, Location spawn){
        this.id = id;
        this.spawn = spawn;

        this.gameState = GameState.RECRUITING;
        this.plugin = plugin;
        this.players = new ArrayList<>();
        this.kits = new HashMap<>();
        this.teams = new HashMap<>();
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


        TreeMultimap<Integer, Team> count  = TreeMultimap.create();
        for (Team team : teams.values()){
            count.put(getTeamCount(team), team);
        }

        Team lowest = (Team) count.values().toArray()[0];
        setTeam(p, lowest);
        p.sendMessage(ChatColor.AQUA + "You have been automatically placed on " + lowest.getDisplay() + ChatColor.AQUA +
                " team.");

        p.sendMessage(ChatColor.GOLD + "Choose your kit with /arena kit");
    }

    public void start(){
        game.start();
    }

    public void reset(boolean kickPlayers){
        if (kickPlayers){
            Location location = ConfigManager.getLobbySpawn();
            for (UUID uuid : players){
                Player p = Bukkit.getPlayer(uuid);
                p.teleport(location);
                removeKit(p.getUniqueId());
            }
            players.clear();
            kits.clear();
            teams.clear();
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
        removeKit(p.getUniqueId());
        removeTeam(p);

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

    public void setTeam(Player p, Team team){
        removePlayer(p);
        teams.put(p.getUniqueId(), team);
    }

    public void removeTeam(Player p){
        if (teams.containsKey(p.getUniqueId())){
            teams.remove(p.getUniqueId());
        }
    }

    public int getTeamCount(Team team){
        int a = 0;
        for (Team t : teams.values()){
            if (t.equals(team)){
                a +=1;
            }
        }
        return a;
    }

    public void setPlayers(List<UUID> players) {
        this.players = players;
    }

    public HashMap<UUID, Kit> getKits(){ return kits; }

    public void removeKit(UUID uuid){
        if (kits.containsKey(uuid)){
            kits.get(uuid).remove();
            kits.remove(uuid);
        }
    }

    public void setKit(UUID uuid, KitType type){
        removeKit(uuid);
        if (type == KitType.FIGHTER){
            kits.put(uuid, new FighterKit(plugin, uuid));
        }else if (type == KitType.MINER){
            kits.put(uuid, new MinerKit(plugin, uuid));
        }
    }

    public KitType getKit(Player p){
        return kits.containsKey(p.getUniqueId()) ? kits.get(p.getUniqueId()).getType() : null;
    }

    public Team getTeam(Player p) { return teams.get(p.getUniqueId()); }
}
