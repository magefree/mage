package mage.client.util;

/**
 * @author JayDi85
 */
public enum ClientEventType {
    CARD_CLICK,
    CARD_DOUBLE_CLICK,
    CARD_POPUP_MENU, // right click on windows // TODO: split into two events: CARD_ and PANEL_
    //
    SET_NUMBER,
    //
    DECK_REMOVE_SELECTION_MAIN,
    DECK_REMOVE_SELECTION_SIDEBOARD,
    DECK_REMOVE_SPECIFIC_CARD,
    DECK_ADD_SPECIFIC_CARD,
    //
    DRAFT_PICK_CARD,
    DRAFT_MARK_CARD,
    //
    PLAYER_TYPE_CHANGED
}
