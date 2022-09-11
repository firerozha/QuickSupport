package com.github.firerozha.quicksupport;

import Commands.SupportCommand;
import Listeners.SupportGUI;
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

        //config
        getConfig().options().copyDefaults();
        saveDefaultConfig();


        //others

    }

}
