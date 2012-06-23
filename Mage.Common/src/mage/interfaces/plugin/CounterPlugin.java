package mage.interfaces.plugin;

import mage.interfaces.PluginException;
import net.xeoh.plugins.base.Plugin;

/**
 * Interface for counter plugins
 * 
 * @version 0.1 14.112010
 * @author nantuko
 */
public interface CounterPlugin extends Plugin {
    void addGamePlayed() throws PluginException;
    int getGamePlayed() throws PluginException;
}
