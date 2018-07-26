package de.fusion.deluxeplugins.config;

import java.util.List;
import org.bukkit.configuration.ConfigurationSection;

public interface ConfigReader {


    boolean contains();

    int getInt();

    boolean getBoolean();

    String getString();

    List<?> getList();

    Object getObject();

    String getStringList();

    ConfigurationSection getConfigSection();

}
