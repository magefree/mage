package org.mage.plugins.counter;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mage.interfaces.PluginException;
import mage.interfaces.plugin.CounterPlugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;
import net.xeoh.plugins.base.annotations.events.PluginLoaded;
import net.xeoh.plugins.base.annotations.meta.Author;

import org.apache.log4j.Logger;

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

	public String toString() {
		return "[Game counter plugin, version 0.1]";
	}
	
	@Override
	public void addGamePlayed() throws PluginException {
		if (!isLoaded) return;
		File data = new File(PLUGIN_DATA_FOLDER_PATH + File.separator + DATA_STORAGE_FILE);
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		if (data.exists()) {
			int prev = 0;
			try {
				ois = new ObjectInputStream(new FileInputStream(data));
				Object o = ois.readObject();
				CounterBean c = null;
				if (o instanceof CounterBean) {
					c = (CounterBean)o;
					prev = c.getGamesPlayed();
				}
			} catch (EOFException e) {
				// do nothing
			} catch (IOException e) {
				throw new PluginException(e);
			} catch (ClassNotFoundException e) {
				throw new PluginException(e);
			} finally {
				if (ois != null) try { ois.close(); } catch (Exception e) {}
			}
			
			try {
				synchronized (this) {
					oos = new ObjectOutputStream(new FileOutputStream(data));
					CounterBean c = new CounterBean();
					c.setGamesPlayed(prev+1);
					oos.writeObject(c);
					oos.close();
				}
			} catch (IOException e) {
				throw new PluginException(e);
			} finally {
				if (oos != null) try { oos.close(); } catch (Exception e) {}
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
			ObjectInputStream ois = null;
			try {
				synchronized (this) {
					ois = new ObjectInputStream(new FileInputStream(data));
					Object o = ois.readObject();
					CounterBean c = null;
					if (o instanceof CounterBean) {
						c = (CounterBean)o;
					}
					ois.close();
					return c.getGamesPlayed();
				}
			} catch (EOFException e) {
				return 0;
			} catch (IOException e) {
				throw new PluginException(e);
			} catch (ClassNotFoundException e) {
				throw new PluginException(e);
			} finally {
				if (ois != null) try { ois.close(); } catch (Exception e) {}
			}
		} else {
			log.error("Counter plugin: data file doesn't exist, please restart plugin.");
			return 0;
		}
	}
	
}
