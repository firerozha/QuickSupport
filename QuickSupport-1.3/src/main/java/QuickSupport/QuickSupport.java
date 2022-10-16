package QuickSupport;

import QuickSupport.Commands.SupportCommand;
import QuickSupport.Listeners.SupportGUI;
import QuickSupport.Listeners.UpdateOnJoin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuickSupport extends JavaPlugin {

    public String noperm = ChatColor.translateAlternateColorCodes('&', getConfig().getString("noperm"));

    @Override
    public void onEnable() {
        // Commands
        getCommand("support").setExecutor(new SupportCommand(this));


        //Listeners
        Bukkit.getPluginManager().registerEvents(new SupportGUI(this), this);
        Bukkit.getPluginManager().registerEvents(new UpdateOnJoin(this), this);

        //config
        getConfig().options().copyDefaults();
        saveDefaultConfig();


        //check for updates
        new UpdateChecker(this, 104954).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available, your current version " +
                        "may be bugged. Update to the latest version here: " +
                        "https://www.spigotmc.org/resources/quicksupport-gui-plugin.104954/");
            }
        });

    }



}
