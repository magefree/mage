package mage.client.util.layout;

import javax.swing.*;

/**
 * Interface for operations that modify cards' layout
 *
 * @author noxx
 */
public interface ICardLayoutStrategy {

    int getDefaultZOrder();

    void onAdd(JLayeredPane jLayeredPane);

    void doLayout(JLayeredPane jLayeredPane, int battlefieldWidth);
}
