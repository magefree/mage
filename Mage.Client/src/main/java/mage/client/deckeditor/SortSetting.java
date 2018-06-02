
package mage.client.deckeditor;

import mage.client.constants.Constants.SortBy;
import mage.client.dialog.PreferencesDialog;

/**
 * @author LevelX2
 */
public abstract class SortSetting {

    SortBy sortBy;
    int sortIndex;
    boolean ascending;
    boolean pilesToggle;

    final String prefSortBy;
    final String prefSortIndex;
    final String prefSortAscending;
    final String prefPilesToggle;

    public SortSetting(String prefSortBy, String prefSortIndex, String prefSortAscending, String prefPilesToggle) {
        this.prefSortBy = prefSortBy;
        this.prefSortIndex = prefSortIndex;
        this.prefSortAscending = prefSortAscending;
        this.prefPilesToggle = prefPilesToggle;
        this.sortBy = SortBy.getByString(PreferencesDialog.getCachedValue(this.prefSortBy, "Color"));
        try {
            this.sortIndex = Integer.parseInt(PreferencesDialog.getCachedValue(this.prefSortIndex, "1"));
        } catch (NumberFormatException e) {
            this.sortIndex = 2;
        }
        this.ascending = PreferencesDialog.getCachedValue(this.prefSortAscending, "1").equals("1");
        this.pilesToggle = PreferencesDialog.getCachedValue(this.prefPilesToggle, "true").equals("true");
    }

    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
        PreferencesDialog.saveValue(prefSortBy, sortBy.toString());
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
        PreferencesDialog.saveValue(this.prefSortIndex, Integer.toString(sortIndex));
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
        PreferencesDialog.saveValue(this.prefSortAscending, this.ascending ? "1" : "0");
    }

    public void setPilesToggle(boolean pileToggle) {
        this.pilesToggle = pileToggle;
        PreferencesDialog.saveValue(this.prefSortAscending, this.pilesToggle ? "true" : "false");
    }

    public SortBy getSortBy() {
        return sortBy;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public boolean isAscending() {
        return ascending;
    }

    public boolean isPilesToggle() {
        return pilesToggle;
    }
}
