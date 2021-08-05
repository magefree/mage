package mage.abilities.icon;

import java.util.Comparator;

/**
 * @author JayDi85
 */
public enum CardIconComparator implements Comparator<CardIcon> {
    instance;

    @Override
    public int compare(CardIcon a, CardIcon b) {
        // by icon type
        int res = Integer.compare(a.getIconType().getSortOrder(), b.getIconType().getSortOrder());

        // by text
        if (res == 0) {
            res = String.CASE_INSENSITIVE_ORDER.compare(a.getCombinedInfo(), b.getCombinedInfo());
        }

        return res;
    }
}