
package mage.client.deckeditor;

import mage.client.dialog.PreferencesDialog;

/**
 *
 * @author LevelX2
 */

public class SortSettingSideboard extends SortSetting {

    private static final SortSettingSideboard instance = new SortSettingSideboard();

    public static SortSettingSideboard getInstance() {
        return instance;
    }

    private SortSettingSideboard() {
        super(PreferencesDialog.KEY_SIDEBOARD_SORT_BY, PreferencesDialog.KEY_SIDEBOARD_SORT_INDEX, PreferencesDialog.KEY_SIDEBOARD_SORT_ASCENDING, PreferencesDialog.KEY_SIDEBOARD_PILES_TOGGLE);
    }
}
