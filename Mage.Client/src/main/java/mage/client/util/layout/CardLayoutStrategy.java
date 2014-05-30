package mage.client.util.layout;

import javax.swing.*;

/**
 * Interface for operations that modify cards' layout
 *
 * @author noxx
 */
public interface CardLayoutStrategy {

    void doLayout(JLayeredPane jLayeredPane, int width);
}
