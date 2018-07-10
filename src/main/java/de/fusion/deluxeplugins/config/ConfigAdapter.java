package de.fusion.deluxeplugins.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class ConfigAdapter {

    private final File f;
    private YamlConfiguration configuration;

    protected ConfigAdapter(File f) {
        this.f = f;
    }

    public File getFile() {
        return f;
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(YamlConfiguration configuration) {
        this.configuration = configuration;
    }
}
