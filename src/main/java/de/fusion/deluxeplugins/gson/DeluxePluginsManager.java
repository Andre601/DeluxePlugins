package de.fusion.deluxeplugins.gson;

import de.fusion.deluxeplugins.DeluxePlugins;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class DeluxePluginsManager {

  public DeluxePluginsManager() {

  }

  public void checkPlugins() {
    for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
      DeluxePlugin deluxePlugin = new DeluxePlugin(plugin.getDescription().getName());
      if (!deluxePlugin.getFile().exists()) {
        deluxePlugin.setCategory("Free");
        deluxePlugin.setDescription("This is a example description");
        deluxePlugin.setDisplayname(plugin.getDescription().getName());
        deluxePlugin.setDownloadLink("https://www.spigotmc.org/resources/");
        deluxePlugin.save();
        DeluxePlugins.getInstance().log("Created plugin file for " + plugin.getName());
      }
    }
  }



}
