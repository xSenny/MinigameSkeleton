package dev.xsenny.minigame.managers;

import dev.xsenny.minigame.instance.Arena;
import dev.xsenny.minigame.MinigameSkeleton;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private List<Arena> arenas = new ArrayList<>();

    public ArenaManager(MinigameSkeleton plugin){
        FileConfiguration config = plugin.getConfig();
        for (String str : config.getConfigurationSection("arenas").getKeys(false)){
            World world = Bukkit.createWorld(new WorldCreator(config.getString("arenas." + str + ".world")));
            world.setAutoSave(false);

            arenas.add(new Arena(plugin, Integer.parseInt(str), new Location(
                    Bukkit.getWorld(config.getString("arenas." + str + ".player-spawn.world")),
                    config.getDouble("arenas." + str + ".player-spawn.x"),
                    config.getDouble("arenas." + str + ".player-spawn.y"),
                    config.getDouble("arenas." + str + ".player-spawn.z"),
                    (float) config.getDouble("arenas." + str + ".player-spawn.yaw"),
                    (float) config.getDouble("arenas." + str + ".player-spawn.pitch")
            ), new Location(
                    Bukkit.getWorld(config.getString("arenas." + str + ".sign.world")),
                    config.getDouble("arenas." + str + ".sign.x"),
                    config.getDouble("arenas." + str + ".sign.y"),
                    config.getDouble("arenas." + str + ".sign.z")
            )));
        }
    }

    public List<Arena> getArenas() { return arenas; }

    public Arena getArena(Player p){
        for (Arena arena : arenas){
            if (arena.getPlayers().contains(p.getUniqueId())){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(int id){
        for (Arena arena : arenas){
            if (arena.getId() == id){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(World world){
        for (Arena arena : arenas){
            if (arena.getWorld().equals(world)){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(Location sign){
        for (Arena arena : arenas){
            if (arena.getSign().equals(sign)){
                return arena;
            }
        }
        return null;
    }

}
