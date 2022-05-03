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

        for (String channel : main.getConfig().getStringList("channels")) {
            List<String> stringList = main.getConfig().getStringList("channels." + channel + ".commands");

            String capture = message.split(" ")[0];
            if (stringList.contains(capture)) {

                TextChannel channelToSend = main.getJDA().getTextChannelById(channel);
                if (channelToSend != null) {
                    String rawMsg = main.getConfig().getString("channels." + channel + ".message");
                    String finalMsg = rawMsg.replace("{player}", executee.getName()).replace("{command}", message);
                    channelToSend.sendMessage(finalMsg).queue();
                }
            }
        }
    }

    public static String getBotToken() {
        return main.getConfig().getString("bot-token");
    }
}
