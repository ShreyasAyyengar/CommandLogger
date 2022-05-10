package me.shreyasayyengar.commandlogger;

import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.entity.Player;

import java.util.List;

public class ConfigManager {

    private static CommandLogger main;

    public static void init(CommandLogger main) {
        ConfigManager.main = main;
        main.getConfig().options().configuration();
        main.saveDefaultConfig();
    }

    public static void log(Player executee, String message) {

        //noinspection ConstantConditions
        for (String channel : main.getConfig().getConfigurationSection("channels").getKeys(false)) {
            List<String> stringList = main.getConfig().getStringList("channels." + channel + ".commands");
            boolean logLocation = main.getConfig().getBoolean("channels." + channel + ".log-location");

            String capture = message.split(" ")[0];
            if (stringList.contains(capture)) {
                TextChannel channelToSend = main.getJDA().getTextChannelById(channel);
                if (channelToSend != null) {
                    String rawMsg = main.getConfig().getString("channels." + channel + ".message");
                    String finalMsg = rawMsg.replace("{player}", executee.getName()).replace("{command}", message);
                    if (logLocation) {
                        finalMsg += " | `" + executee.getLocation().getWorld().getName() + ", " + executee.getLocation().getBlockX() + ", " + executee.getLocation().getBlockY() + ", " + executee.getLocation().getBlockZ() + "`";
                    }
                    channelToSend.sendMessage(finalMsg).queue();
                }
            }
        }
    }

    public static String getBotToken() {
        return main.getConfig().getString("bot-token");
    }
}
