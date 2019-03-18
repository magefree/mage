
package mage.client.deckeditor.collection.viewer;

import mage.client.MagePane;
import mage.client.plugins.impl.Plugins;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Collection viewer pane.
 * Contains background and components container.
 *
 * @author nantuko
 */
public class CollectionViewerPane extends MagePane {

    public CollectionViewerPane() {
        boolean initialized = false;
        this.setTitle("Collection Viewer");
        if (Plugins.instance.isThemePluginLoaded()) {
            Map<String, JComponent> uiComponents = new HashMap<>();
            JComponent container = Plugins.instance.updateTablePanel(uiComponents);
            if (container != null) {
                collectionViewerPanel = new CollectionViewerPanel();
                initComponents(container);
                container.add(collectionViewerPanel);
                container.setOpaque(false);
                collectionViewerPanel.setOpaque(false);
                initialized = true;
            }
        }
        if (!initialized) {
            initComponents(null);
        }
    }

    private void initComponents(Component container) {
        Component component = container != null ? container : new CollectionViewerPanel();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(component, javax.swing.GroupLayout.DEFAULT_SIZE, 885, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(component, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
        );
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (collectionViewerPanel != null) {
            collectionViewerPanel.showCards();
        }
    }

    private CollectionViewerPanel collectionViewerPanel;
}
