package mage.client.cards;

import mage.abilities.icon.CardIconColor;
import mage.abilities.icon.CardIconOrder;
import mage.abilities.icon.CardIconPosition;
import mage.abilities.icon.CardIconRenderSettings;

/**
 * GUI panel to drawning icons (one of the card's side)
 *
 * @author JayDi85
 */
public class CardIconsPanelFactory {

    public static CardIconsPanel createAbilitiesPanel() {
        return new CardIconsPanel(
                CardIconPosition.LEFT,
                CardIconOrder.CENTER,
                CardIconColor.DEFAULT,
                5,
                30
        );
    }

    public static CardIconsPanel createPlayablePanel() {
        return new CardIconsPanel(
                CardIconPosition.CORNER_BOTTOM_LEFT,
                CardIconOrder.CENTER,
                CardIconColor.DEFAULT,
                1,
                30
        );
    }

    public static CardIconsPanel createCommanderPanel() {
        // must be x2 bigger than normal icons
        return new CardIconsPanel(
                CardIconPosition.TOP,
                CardIconOrder.CENTER,
                CardIconColor.RED,
                3,
                30 * 2 // TODO: big size doesn't work due icons same size limits, see CardIconsPanel
        );
    }

    public static CardIconsPanel createDebugPanel(CardIconRenderSettings render) {
        return new CardIconsPanel(render);
    }
}
