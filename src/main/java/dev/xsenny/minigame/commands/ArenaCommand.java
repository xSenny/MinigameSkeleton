package dev.xsenny.minigame.commands;

import dev.xsenny.minigame.GameState;
import dev.xsenny.minigame.MinigameSkeleton;
import dev.xsenny.minigame.instance.Arena;
import dev.xsenny.minigame.kit.KitUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

    private MinigameSkeleton plugin;

    public ArenaCommand(MinigameSkeleton plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length == 1 && args[0].equalsIgnoreCase("list")){
                p.sendMessage(ChatColor.GREEN + "These are available arenas: ");
                for (Arena arena : plugin.getArenaManager().getArenas()){
                    p.sendMessage(ChatColor.GREEN + "- " + arena.getId() + "(" + arena.getGameState().name() + ")");
                }
            }else if (args.length == 1 && args[0].equalsIgnoreCase("kit")){
                Arena arena = plugin.getArenaManager().getArena(p);
                if (arena != null){
                    if (arena.getGameState() != GameState.LIVE){
                        new KitUI(p);
                    }else{
                        p.sendMessage(ChatColor.RED + "You cannot select a kit right neow");
                    }
                }else{
                    p.sendMessage(ChatColor.RED + "you arent in an arena");
                }
            }else if (args.length == 1 && args[0].equalsIgnoreCase("leave")){

                Arena arena = plugin.getArenaManager().getArena(p);
                if (arena == null){
                    p.sendMessage(ChatColor.RED + "You are not in an arena");
                    return true;
                }
                p.sendMessage(ChatColor.RED + "You left the arena");
                arena.removePlayer(p);

            }else if (args.length == 2 && args[0].equalsIgnoreCase("join")){
                Arena arena = plugin.getArenaManager().getArena(p);
                if (arena != null){
                    p.sendMessage(ChatColor.RED + "You are already in one arena");
                    return true;
                }
                int id;
                try{
                    id = Integer.parseInt(args[1]);
                }catch (NumberFormatException e){
                    p.sendMessage(ChatColor.RED + "Use a number");
                    return false;
                }
                if (id >= 0 && id < plugin.getArenaManager().getArenas().size()){
                    Arena arena1 = plugin.getArenaManager().getArena(id);
                    if (arena1 != null && (arena1.getGameState() == GameState.RECRUITING || arena1.getGameState() == GameState.COUNTDOWN)){
                        p.sendMessage("You aren now playing in Arena " + id);
                        arena1.addPlayer(p);
                    }else{
                        p.sendMessage(ChatColor.RED + "You cannot join on this arena right now.");
                    }
                }
            }else {
                p.sendMessage(ChatColor.RED + "Invalid usage! These are the following options: list, leave, join, kit");
            }
        }

        return true;
    }
}
