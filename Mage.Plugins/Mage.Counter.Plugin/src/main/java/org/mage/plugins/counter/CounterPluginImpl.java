package org.mage.plugins.counter;

import mage.interfaces.PluginException;
import mage.interfaces.plugin.CounterPlugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;
import net.xeoh.plugins.base.annotations.events.PluginLoaded;
import net.xeoh.plugins.base.annotations.meta.Author;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Implementation of {@link CounterPlugin}.<br/>
 * Stores data in data folder. 
 * 
 * @version 0.1 14.11.2010 Initial Version
 * @author nantuko
 */
@PluginImplementation
@Author(name = "nantuko")
public class CounterPluginImpl implements CounterPlugin {

    private static final String PLUGIN_DATA_FOLDER_PATH = "plugins" + File.separator + "plugin.data" + File.separator + "counters";

    private static final String DATA_STORAGE_FILE = "counters";

    private static final Logger log = Logger.getLogger(CounterPluginImpl.class);

    private boolean isLoaded = false;

    @Init
    public void init() {
        File dataFolder = new File(PLUGIN_DATA_FOLDER_PATH);
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
            if (!dataFolder.exists()) {
                throw new RuntimeException("CounterPluginImpl: Couldn't create folders: " + PLUGIN_DATA_FOLDER_PATH);
            }
        }
        File data = new File(PLUGIN_DATA_FOLDER_PATH + File.separator + DATA_STORAGE_FILE);
        if (!data.exists()) {
            try {
                data.getParentFile().mkdirs();
                data.createNewFile();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException("Couldn't create data file for counter plugin: " + e.getMessage());
            }
        }

        this.isLoaded = true;
    }

    @PluginLoaded
    public void newPlugin(CounterPlugin plugin) {
        log.info(plugin.toString() + " has been loaded.");
    }

    @Override
    public String toString() {
        return "[Game counter plugin, version 0.1]";
    }

    @Override
    public void addGamePlayed() throws PluginException {
        if (!isLoaded) return;
        File data = new File(PLUGIN_DATA_FOLDER_PATH + File.separator + DATA_STORAGE_FILE);
        if (data.exists()) {
            int prev = 0;

            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(data))) {
                Object o = ois.readObject();
                CounterBean c;
                if (o instanceof CounterBean) {
                    c = (CounterBean)o;
                    prev = c.getGamesPlayed();
                }
            } catch (EOFException e) {
                // do nothing
            } catch (IOException | ClassNotFoundException e) {
                throw new PluginException(e);
            }

            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(data))) {
                synchronized (this) {
                    CounterBean c = new CounterBean();
                    c.setGamesPlayed(prev+1);
                    oos.writeObject(c);
                    oos.close();
                }
            } catch (IOException e) {
                throw new PluginException(e);
            }
        } else {
            log.error("Counter plugin: data file doesn't exist, please restart plugin.");
        }
    }

    @Override
    public int getGamePlayed() throws PluginException {
        if (!isLoaded) return -1;
        File data = new File(PLUGIN_DATA_FOLDER_PATH + File.separator + DATA_STORAGE_FILE);
        if (!data.exists()) {
            return 0;
        }
        if (data.exists()) {
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(data))) {
                synchronized (this) {
                    Object o = ois.readObject();
                    CounterBean c = null;
                    if (o instanceof CounterBean) {
                        c = (CounterBean)o;
                    }
                    ois.close();
                    return c == null ? 0 : c.getGamesPlayed();
                }
            } catch (EOFException e) {
                return 0;
            } catch (IOException | ClassNotFoundException e) {
                throw new PluginException(e);
            }
        } else {
            log.error("Counter plugin: data file doesn't exist, please restart plugin.");
            return 0;
        }
    }

}
