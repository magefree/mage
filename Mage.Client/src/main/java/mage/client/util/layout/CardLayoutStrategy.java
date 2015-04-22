package mage.client.util.layout;

import javax.swing.JLayeredPane;

/**
 * Interface for operations that modify cards' layout
 *
 * @author noxx
 */
public interface CardLayoutStrategy {

    int getDefaultZOrder();

    void onAdd(JLayeredPane jLayeredPane);

    void doLayout(JLayeredPane jLayeredPane, int battlefieldWidth);
}
