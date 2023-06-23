package dev.xsenny.minigame.managers;

import dev.xsenny.minigame.MinigameSkeleton;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LangManager {

    private static YamlConfiguration lang;

    public static void setupLangFile(MinigameSkeleton plugin){
        File file = new File(plugin.getDataFolder(), "lang.yml");
        if (!file.exists()){
            plugin.saveResource("lang.yml", false);
        }
        lang = YamlConfiguration.loadConfiguration(file);
    }

    public static String getLeaveArena(){ return lang.getString("leave-arena"); } // enum for 99990 messages
}
