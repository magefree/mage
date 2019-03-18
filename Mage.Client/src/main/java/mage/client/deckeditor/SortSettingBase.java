
package mage.client.deckeditor;

import mage.client.dialog.PreferencesDialog;

/**
 *
 * @author LevelX2
 */

public class SortSettingBase extends SortSetting {

    private static final SortSettingBase instance = new SortSettingBase();

    public static SortSettingBase getInstance() {
        return instance;
    }

    private SortSettingBase() {
        super(PreferencesDialog.KEY_BASE_SORT_BY, PreferencesDialog.KEY_BASE_SORT_INDEX, PreferencesDialog.KEY_BASE_SORT_ASCENDING, PreferencesDialog.KEY_BASE_PILES_TOGGLE);
    }
}