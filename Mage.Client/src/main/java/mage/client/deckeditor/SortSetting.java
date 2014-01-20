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
import static mage.client.constants.Constants.SortBy.CASTING_COST;
import static mage.client.constants.Constants.SortBy.COLOR;
import static mage.client.constants.Constants.SortBy.COLOR_DETAILED;
import static mage.client.constants.Constants.SortBy.NAME;
import static mage.client.constants.Constants.SortBy.RARITY;
import mage.client.dialog.PreferencesDialog;

/**
 *
 * @author LevelX2
 */
public abstract class SortSetting {
    
    SortBy sortBy;
    int sortIndex;
    boolean ascending;

    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
        PreferencesDialog.saveValue(PreferencesDialog.KEY_DRAFT_SORT_BY, sortBy.toString());
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
        PreferencesDialog.saveValue(PreferencesDialog.KEY_DRAFT_SORT_INDEX, Integer.toString(sortIndex));
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
        PreferencesDialog.saveValue(PreferencesDialog.KEY_DRAFT_SORT_ASCENDING, this.ascending ? "1":"0");
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

    public int convertSortByToIndex(SortBy sortBy) {
        switch(sortBy) {
            case NAME:
                return 1;
            case CASTING_COST:
                return 2;
            case COLOR:
            case COLOR_DETAILED:
                return 3;
            case RARITY:
                return 5;
            default:
                return 0;
        }
    }

    public SortBy convertIndexToSortBy(int index) {
        switch (index) {
            case 1:
                return SortBy.NAME;
            case 2:
                return SortBy.CASTING_COST;
            case 3:
                return SortBy.COLOR;
            case 5:
                return SortBy.RARITY;
            default:
                return SortBy.UNSORTED;
        }
    }
}
