package me.shreyasayyengar.commandlogger;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class CommandLogger extends JavaPlugin {

    private JDA jda;

    @Override
    public void onEnable() {

        getLogger().info("CommandLogger has been enabled!");
        getServer().getPluginManager().registerEvents(new CommandListener(), this);

        ConfigManager.init(this);
        registerJDA();
    }

    private void registerJDA() {
        try {
            jda = JDABuilder.createDefault(ConfigManager.getBotToken())
                    .build()
                    .awaitReady();
        } catch (InterruptedException | LoginException e) {
            getLogger().info("The plugin could not connect to your bot/discord server, please check that the BOT"
                    + "token is valid and the text channel(s) specified exists!");
        }

        if (jda == null) {
            getLogger().info("The plugin could not connect to your bot/discord server, please check that the BOT"
                    + "token is valid and the text channel specified exists!");
            this.getServer().getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CommandLogger getInstance() {
        return JavaPlugin.getPlugin(CommandLogger.class);
    }

    public JDA getJDA() {
        return jda;
    }
}
