
package mage.client.deckeditor;

import mage.client.dialog.PreferencesDialog;

/**
 *
 * @author LevelX2
 */

public class SortSettingDeck extends SortSetting {

    private static final SortSettingDeck instance = new SortSettingDeck();

    public static SortSettingDeck getInstance() {
        return instance;
    }

    private SortSettingDeck() {
        super(PreferencesDialog.KEY_DECK_SORT_BY, PreferencesDialog.KEY_DECK_SORT_INDEX, PreferencesDialog.KEY_DECK_SORT_ASCENDING, PreferencesDialog.KEY_DECK_PILES_TOGGLE);
    }
}