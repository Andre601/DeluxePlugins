package de.fusion.deluxeplugins.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fusion.deluxeplugins.DeluxePlugins;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DeluxePlugin {

  public DeluxePlugin(String name) {
    file = new File(DeluxePlugins.getInstance().getDataFolder() + "//plugins", name + ".json");
    this.NAME = name;
  }

  public DeluxePlugin load() {
    BufferedReader input = null;
    try {
      input = new BufferedReader(new FileReader(file));
      DeluxePlugin dp =  new Gson().fromJson(input, DeluxePlugin.class).setFile(file);
      input.close();
      return dp;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  return null;
  }

  public void save() {
    try {
      if (!file.exists()) {
        file.createNewFile();
      }
      FileWriter fileOut = new FileWriter(file);
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      fileOut.write(gson.toJson(this));
      fileOut.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String NAME;
  private String DISPLAYNAME;
  private String DESCRIPTION;
  private String CATEGORY;
  private String DOWNLOAD_LINK;

  private transient File file;

  private DeluxePlugin setFile(File file) {
    this.file = file;
    return this;
  }

  public File getFile() {
    return file;
  }

  public String getName() {
    return NAME;
  }

  public String getCategory() {
    return CATEGORY;
  }

  public String getDescription() {
    return DESCRIPTION;
  }

  public String getDownloadLink() {
    return DOWNLOAD_LINK;
  }

  public void setName(String NAME) {
    this.NAME = NAME;
  }

  public void setCategory(String CATEGORY) {
    this.CATEGORY = CATEGORY;
  }

  public void setDescription(String DESCRIPTION) {
    this.DESCRIPTION = DESCRIPTION;
  }

  public void setDownloadLink(String DOWNLOAD_LINK) {
    this.DOWNLOAD_LINK = DOWNLOAD_LINK;
  }

  public void setDisplayname(String DISPLAYNAME) {
    this.DISPLAYNAME = DISPLAYNAME;
  }

  public String getDisplayname() {
    return DISPLAYNAME;
  }
}
