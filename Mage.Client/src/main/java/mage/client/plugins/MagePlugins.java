package mage.client.plugins;

import java.util.Map;

import javax.swing.JComponent;

public interface MagePlugins {
	void loadPlugins();
	void shutdown();
	void updateGamePanel(Map<String, JComponent> ui);
}
