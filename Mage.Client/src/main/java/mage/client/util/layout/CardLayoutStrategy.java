package mage.client.util.layout;

import mage.client.game.BattlefieldPanel;

/**
 * Interface for operations that modify cards' layout
 *
 * @author noxx
 */
public interface CardLayoutStrategy {

    int getDefaultZOrder();

    void onAdd(BattlefieldPanel jLayeredPane);

    void doLayout(BattlefieldPanel battlefieldPanel, int battlefieldWidth);
}
