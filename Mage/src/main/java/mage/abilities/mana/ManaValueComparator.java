package mage.abilities.mana;

import mage.Mana;

import java.util.Comparator;

/**
 * Comperator used for inside {@link ManaOptions#removeFullyIncludedVariations()}
 * <p>
 * The goal is to sort a list of Mana objects for removing fully included variations.
 *
 *
 * This will be run on a list which was created from a set, thus there are guaranteed to not be any duplicates.
 *
 *
 * @param <T>
 */
public final class ManaValueComparator implements Comparator {
    /**
     * @param o1 the first (Mana) object to be compared.
     * @param o2 the second (Mana) object to be compared.
     * @return int: +1  o1 is less valuable than o2
     *               0  o1 is as valuable as o2
     *              -1  o1 is more valuable than o2
     */
    @Override
    public int compare(Object o1, Object o2) {
        Mana mana1 = (Mana) o1;
        Mana mana2 = (Mana) o2;

        if (mana1.equals(mana2)) {
            return 0;
        }
        int retInt = 0;

        // Seperate out based on amount of colorless first since it can't be compared to anything else
        if (mana1.getColorless() > mana2.getColorless()) {
            return -1;
        } else if (mana1.getColorless() < mana2.getColorless()) {
            return 1;
        }

        // Put the any towards the top
        if (mana1.getAny() > mana2.getAny()) {
            return -1;
        } else if (mana1.getAny() < mana2.getAny()) {
            return 1;
        }

        // Sort based on WUBRG
        if (mana1.getWhite() > mana2.getWhite()) {
            return -1;
        } else if (mana1.getWhite() < mana2.getWhite()) {
            return 1;
        }
        if (mana1.getBlue() > mana2.getBlue()) {
            return -1;
        } else if (mana1.getBlue() < mana2.getBlue()) {
            return 1;
        }
        if (mana1.getBlack() > mana2.getBlack()) {
            return -1;
        } else if (mana1.getBlack() < mana2.getBlack()) {
            return 1;
        }
        if (mana1.getRed() > mana2.getRed()) {
            return -1;
        } else if (mana1.getRed() < mana2.getRed()) {
            return 1;
        }
        if (mana1.getGreen() > mana2.getGreen()) {
            return -1;
        } else if (mana1.getGreen() < mana2.getGreen()) {
            return 1;
        }

        // Sort based on count, so that {W}{U}{B} is above {W}{U}
        // Done here at the bottom rather than at the beginning so that {W}{U}{B} and {W}{U} stay together
        // rather than be seperated by count.
        if (mana1.count() > mana2.count()) {
            return -1;
        } else if (mana1.count() < mana2.count()) {
            return 1;
        }
        // TODO: If we get here, then something has gone wrong.
        return retInt;
    }
}
