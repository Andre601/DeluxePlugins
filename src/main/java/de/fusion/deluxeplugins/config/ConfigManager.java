package de.fusion.deluxeplugins.config;


import de.fusion.deluxeplugins.DeluxePlugins;
import java.io.File;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager extends ConfigAdapter implements ConfigAdapterInterface {


  public ConfigManager(File f) {
    super(f);
  }

  @Override
  public ConfigManager build() {
    reload();
    return this;
  }

  @Override
  public ConfigManager reload() {
    setConfiguration(YamlConfiguration.loadConfiguration(getFile()));
    return this;
  }

  @Override
  public ConfigWriter setPath(String path) {
    return new RealConfigWriter(getConfiguration(), path);
  }

  @Override
  public ConfigReader getPath(String path) {
    return new RealConfigReader(getConfiguration(), path);
  }


  private class RealConfigReader implements ConfigReader {

    private final YamlConfiguration configuration;
    private final String path;

    private RealConfigReader(YamlConfiguration configuration, String path) {
      this.configuration = configuration;
      this.path = path;
    }

    @Override
    public boolean contains() {
      return configuration.contains(path);
    }

    @Override
    public int getInt() {
      return configuration.getInt(path);
    }

    @Override
    public boolean getBoolean() {
      return configuration.getBoolean(path);
    }

    @Override
    public String getString() {
      return ChatColor.translateAlternateColorCodes('&', configuration.getString(path));
    }

    @Override
    public List<?> getList() {
      return configuration.getList(path);
    }

    @Override
    public Object getObject() {
      return configuration.get(path);
    }

    @Override
    public String getStringList() {
      StringBuilder stringList = new StringBuilder();
      getList().forEach(string -> stringList.append(string).append("\n"));
      return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', stringList.toString());
    }

    @Override
    public ConfigurationSection getConfigSection() {
      return configuration.getConfigurationSection(path);
    }
  }

  private class RealConfigWriter implements ConfigWriter {

    private final YamlConfiguration configuration;
    private final String path;

    private RealConfigWriter(YamlConfiguration configuration, String path) {
      this.configuration = configuration;
      this.path = path;
    }

    @Override
    public void setObject(Object o) {
      configuration.set(path, o);
    }

    @Override
    public void setAsyncObject(Object o) {
      DeluxePlugins.getInstance().getExecutorService().submit(() -> this.setObject(o));
    }
  }
}
