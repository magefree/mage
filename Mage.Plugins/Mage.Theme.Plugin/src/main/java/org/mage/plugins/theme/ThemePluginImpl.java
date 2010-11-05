package org.mage.plugins.theme;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import mage.interfaces.plugin.ThemePlugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;
import net.xeoh.plugins.base.annotations.events.PluginLoaded;
import net.xeoh.plugins.base.annotations.meta.Author;

import org.apache.log4j.Logger;
import org.mage.plugins.component.ImagePanel;

@PluginImplementation
@Author(name="nantuko")
public class ThemePluginImpl implements ThemePlugin {
	
	private final static Logger log = Logger.getLogger(ThemePluginImpl.class);
	
	@Init
    public void init() {
    }
	
	@PluginLoaded
    public void newPlugin(ThemePlugin plugin) {
        log.info(plugin.toString() + " has been loaded.");
    }
	
	public String toString() {
		return "[Theme plugin, version 0.2]";
	}
	
	public void apply(Map<String, JComponent> ui) {
		//log.info("Adding background...");
		try {
			BufferedImage background = ImageIO.read(this.getClass().getResourceAsStream("/dk_gray.jpg"));
			
			if (background == null)
				throw new FileNotFoundException("Couldn't find dk_gray.jpg in resources.");
			
			if (ui.containsKey("gamePanel") && ui.containsKey("jLayeredPane")) {
				ImagePanel bgPanel = new ImagePanel(background, ImagePanel.TILED);

				unsetOpaque(ui.get("jSplitPane1"));
				unsetOpaque(ui.get("pnlBattlefield"));
				unsetOpaque(ui.get("jPanel3"));
				unsetOpaque(ui.get("hand"));
				unsetOpaque(ui.get("chatPanel"));

				ui.get("gamePanel").remove(ui.get("jLayeredPane"));
				bgPanel.add(ui.get("jLayeredPane"));
				ui.get("gamePanel").add(bgPanel);
			} else {
				log.error("error: no components");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return;
		}
		
		//log.info("Done.");
	}
	
	private void unsetOpaque(JComponent c) {
		if (c != null) c.setOpaque(false);
	}
}
