package de.fusion.deluxeplugins.commands;

import de.fusion.deluxeplugins.DeluxePlugins;
import de.fusion.deluxeplugins.gson.DeluxePlugin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.ClickEvent.Action;
import net.kyori.text.event.HoverEvent;
import net.kyori.text.serializer.ComponentSerializers;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public class DeluxePluginsCommand implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s,
      String[] args) {

    if (args.length == 0) {
      ConfigurationSection section = DeluxePlugins.getConfiguration().getPath("Categories")
          .getConfigSection();
      for (String key : section.getKeys(false)) {
        String prefix = ChatColor.translateAlternateColorCodes('&', section.getString(key));
        commandSender.sendMessage(" §7");
        commandSender.sendMessage(
            DeluxePlugins.getConfiguration().getPath("General.Plugin.CategoryLayout").getString()
                .replace("%category%", prefix));
        commandSender.sendMessage(" §7");

        for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
          DeluxePlugin dp = new DeluxePlugin(p.getDescription().getName()).load();
          if (dp.getCategory().equalsIgnoreCase(key)) {
            TextComponent textComponent = TextComponent
                .of(DeluxePlugins.getConfiguration().getPath("General.Plugin.Layout").getString()
                    .replace("%displayname%", dp.getDisplayname())
                    .replace("%pluginversion%", p.getDescription().getVersion())
                    .replace("%author%", p.getDescription().getAuthors().get(0)))
                .clickEvent(new ClickEvent(Action.RUN_COMMAND, "/deluxeplugins " + dp.getName()))
                .hoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent
                    .of(DeluxePlugins.getConfiguration().getPath("General.Plugin.Hover")
                        .getString())));
            String json = ComponentSerializers.JSON.serialize(textComponent);
            commandSender.spigot().sendMessage(ComponentSerializer.parse(json));

          }
        }
      }
    } else if (args.length == 1) {
      String plugin = args[0];
      if (Bukkit.getPluginManager().getPlugin(plugin) != null) {
        Plugin p = Bukkit.getPluginManager().getPlugin(plugin);
        DeluxePlugin dp = new DeluxePlugin(p.getDescription().getName()).load();
        commandSender.sendMessage(
            DeluxePlugins.getConfiguration().getPath("General.Plugin.DetailedLayout")
                .getStringList()
                .replace("%displayname%", dp.getDisplayname())
                .replace("%pluginversion%", p.getDescription().getVersion())
                .replace("%authors%",
                    p.getDescription().getAuthors().toString().replace("[", "").replace("]", ""))
                .replace("%category%",
                    DeluxePlugins.getConfiguration().getPath("Categories." + dp.getCategory())
                        .getString())
                .replace("%description%", dp.getDescription()));

      } else {
        commandSender.sendMessage(DeluxePlugins.getPrefix() + DeluxePlugins.getConfiguration()
            .getPath("General.Plugin.UnknownPlugin").getString());
        return true;
      }
    }

    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s,
      String[] strings) {
    List<String> l = new ArrayList<>();
    for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
      l.add(p.getDescription().getName());
    }

    Collections.sort(l);
    return l;
  }


}
