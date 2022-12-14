package QuickSupport.Commands;

import QuickSupport.QuickSupport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SupportCommand implements CommandExecutor {

    //key = player uuid
    // long = epoch time of when the player ran the command
    // This is a very old code and its kind of messy so I'm sorry about that
    private final HashMap<UUID, Long> cooldown;

    private final QuickSupport main;

    public SupportCommand(QuickSupport main) {
        this.main = main;
        this.cooldown = new HashMap<>();
    }


    ItemStack glass;

    String[] versionsLegacy = {"1.8", "1.9", "1.10", "1.11", "1.12"};
    String[] versionsNew = {"1.13", "1.14", "1.15", "1.16", "1.17", "1.18", "1.19"};

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to do this.");
        } else {
            Player p = (Player) sender;
            if(!p.hasPermission("quicksupport.support")) {
                p.sendMessage(main.noperm);
            } else {
                //this is a cooldown
                // this checks if the player is in the cooldown hashmap
                // if they arent, it puts them in it
                if(!this.cooldown.containsKey(p.getUniqueId())) {
                    this.cooldown.put(p.getUniqueId(), System.currentTimeMillis());

                    Inventory support = Bukkit.createInventory(null, 27,
                            ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("guititle")));
                    //////////////////// GLASS ITEM ////////////////////
                    //this checks whether the server version is legacy or not
                    //if it is, the glass ID is different than a non legacy version
                    for(String vLegacy : versionsLegacy) {
                        if (Bukkit.getVersion().contains(vLegacy)) {
                            glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
                        }
                    }
                     for(String vNew : versionsNew) {
                         if(Bukkit.getVersion().contains(vNew)) {
                             glass = new ItemStack(Material.valueOf("GRAY_STAINED_GLASS_PANE"), 1);
                         }
                     }

                    ItemMeta glassMeta = glass.getItemMeta();
                    glassMeta.setDisplayName(" ");
                    glass.setItemMeta(glassMeta);
                    int[] numbers = {0,1, 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};
                    for(int i : numbers) {
                        support.setItem(i, glass);
                    }
                    /////////////////// SUPPORT SLOTS   ///////////////
                    // SUPPORT SLOT 1
                    Material s1mater = Material.valueOf(main.getConfig().getString("SupportSlot1.icon"));
                    ItemStack s1 = new ItemStack(s1mater);
                    ItemMeta s1meta = s1.getItemMeta();
                    s1meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                            main.getConfig().getString("SupportSlot1.name")));
                    List<String> s1lore = main.getConfig().getStringList("SupportSlot1.lore");
                    s1meta.setLore(s1lore);
                    s1.setItemMeta(s1meta);

                    support.setItem(10, s1);
                    // SUPPORT SLOT 2
                    Material s2mater = Material.valueOf(main.getConfig().getString("SupportSlot2.icon"));
                    ItemStack s2 = new ItemStack(s2mater);
                    ItemMeta s2meta = s2.getItemMeta();
                    s2meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                            main.getConfig().getString("SupportSlot2.name")));
                    List<String> s2lore = main.getConfig().getStringList("SupportSlot2.lore");
                    s2meta.setLore(s2lore);
                    s2.setItemMeta(s2meta);

                    support.setItem(13, s2);
                    // SUPPORT SLOT 3
                    Material s3mater = Material.valueOf(main.getConfig().getString("SupportSlot3.icon"));
                    ItemStack s3 = new ItemStack(s3mater);
                    ItemMeta s3meta = s3.getItemMeta();
                    s3meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                            main.getConfig().getString("SupportSlot3.name")));
                    List<String> s3lore = main.getConfig().getStringList("SupportSlot3.lore");
                    s3meta.setLore(s3lore);
                    s3.setItemMeta(s3meta);

                    support.setItem(16, s3);
                    /////////////
                    p.openInventory(support);
                } else {
                    long timeElapsed = System.currentTimeMillis() - cooldown.get(p.getUniqueId());
                    if(timeElapsed >= main.getConfig().getInt("delay")) {
                        this.cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                        Inventory support = Bukkit.createInventory(null, 27,
                                ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("guititle")));
                        //////////////////// GLASS ITEM ////////////////////
                        for(String v : versionsLegacy) {
                            if (Bukkit.getVersion().contains(String.valueOf(v))) {
                                glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 8);
                            } else {
                                 glass = new ItemStack(Material.valueOf("GRAY_STAINED_GLASS_PANE"), 1);
                            }
                     }
                        ItemMeta glassMeta = glass.getItemMeta();
                        glassMeta.setDisplayName(" ");
                        glass.setItemMeta(glassMeta);
                        int[] numbers = {0,1, 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};
                        for(int i : numbers) {
                            support.setItem(i, glass);
                        }
                        /////////////////// SUPPORT SLOTS   ///////////////
                        // SUPPORT SLOT 1
                        Material s1mater = Material.valueOf(main.getConfig().getString("SupportSlot1.icon"));
                        ItemStack s1 = new ItemStack(s1mater);
                        ItemMeta s1meta = s1.getItemMeta();
                        s1meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                                main.getConfig().getString("SupportSlot1.name")));
                        List<String> s1lore = main.getConfig().getStringList("SupportSlot1.lore");
                        s1meta.setLore(s1lore);
                        s1.setItemMeta(s1meta);

                        support.setItem(10, s1);
                        // SUPPORT SLOT 2
                        Material s2mater = Material.valueOf(main.getConfig().getString("SupportSlot2.icon"));
                        ItemStack s2 = new ItemStack(s2mater);
                        ItemMeta s2meta = s2.getItemMeta();
                        s2meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                                main.getConfig().getString("SupportSlot2.name")));
                        List<String> s2lore = main.getConfig().getStringList("SupportSlot2.lore");
                        s2meta.setLore(s2lore);
                        s2.setItemMeta(s2meta);

                        support.setItem(13, s2);
                        // SUPPORT SLOT 3
                        Material s3mater = Material.valueOf(main.getConfig().getString("SupportSlot3.icon"));
                        ItemStack s3 = new ItemStack(s3mater);
                        ItemMeta s3meta = s3.getItemMeta();
                        s3meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                                main.getConfig().getString("SupportSlot3.name")));
                        List<String> s3lore = main.getConfig().getStringList("SupportSlot3.lore");
                        s3meta.setLore(s3lore);
                        s3.setItemMeta(s3meta);

                        support.setItem(16, s3);
                        /////////////
                        p.openInventory(support);
                    } else {
                        int delayLeft = (int) TimeUnit.MILLISECONDS.toSeconds((main.getConfig().getInt("delay") - timeElapsed));
                        String delayMsg = main.getConfig().getString("delaymsg");
                        delayMsg = delayMsg.replace("{delay}", String.valueOf(delayLeft));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', delayMsg));
                    }
                }
                }
        }


        return true;
    }
}
