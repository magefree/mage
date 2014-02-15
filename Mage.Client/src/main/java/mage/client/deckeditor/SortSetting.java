/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.client.deckeditor;

import mage.client.constants.Constants.SortBy;
import mage.client.dialog.PreferencesDialog;

/**
 *
 * @author LevelX2
 */
public abstract class SortSetting {
    
    SortBy sortBy;
    int sortIndex;
    boolean ascending;
    boolean pilesToggle;
    
    String prefSortBy;
    String prefSortIndex;
    String prefSortAscending;
    String prefPilesToggle;

    public SortSetting(String prefSortBy, String prefSortIndex, String prefSortAscending, String prefPilesToggle) {
        this.prefSortBy = prefSortBy;
        this.prefSortIndex = prefSortIndex;
        this.prefSortAscending = prefSortAscending;
        this.prefPilesToggle = prefPilesToggle;
        this.sortBy = SortBy.getByString(PreferencesDialog.getCachedValue(this.prefSortBy, "Color"));
        this.sortIndex = Integer.parseInt(PreferencesDialog.getCachedValue(this.prefSortIndex, "1"));
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
        PreferencesDialog.saveValue(this.prefSortAscending, this.ascending ? "1":"0");
    }

    public void setPilesToggle(boolean pileToggle) {
        this.pilesToggle = pileToggle;
        PreferencesDialog.saveValue(this.prefSortAscending, this.pilesToggle ? "true":"false");
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
