

package mage.client.deckeditor;

import mage.client.dialog.PreferencesDialog;

/**
 *
 * @author LevelX2
 */
public class SortSettingDraft extends SortSetting {

    private static final SortSettingDraft instance = new SortSettingDraft();

    public static SortSettingDraft getInstance() {
        return instance;
    }

    private SortSettingDraft() {
        super(PreferencesDialog.KEY_DRAFT_SORT_BY, PreferencesDialog.KEY_DRAFT_SORT_INDEX, PreferencesDialog.KEY_DRAFT_SORT_ASCENDING, PreferencesDialog.KEY_DRAFT_PILES_TOGGLE );
    }
}
