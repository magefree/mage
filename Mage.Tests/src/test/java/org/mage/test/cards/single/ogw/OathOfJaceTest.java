package org.mage.test.cards.single.ogw;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class OathOfJaceTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.o.OathOfJace Oath of Jace} {2}{U}
     * Legendary Enchantment
     * When Oath of Jace enters, draw three cards, then discard two cards.
     * At the beginning of your upkeep, scry X, where X is the number of planeswalkers you control.
     */
    private static final String oath = "Oath of Jace";

    @Test
    public void test_scry2() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Ancient Amphitheater");
        addCard(Zone.LIBRARY, playerA, "Baleful Strix");

        addCard(Zone.BATTLEFIELD, playerA, oath, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Legions of Lim-Dul", 2); // not a planeswalker
        addCard(Zone.BATTLEFIELD, playerA, "Chandra Nalaar");
        addCard(Zone.BATTLEFIELD, playerA, "Jace Beleren");

        addCard(Zone.BATTLEFIELD, playerB, "Ajani, Caller of the Pride"); // not in your control

        // Oath of Jace's upkeep trigger triggers
        addTarget(playerA, "Ancient Amphitheater^Baleful Strix"); // put on bottom with scry 2
        setChoice(playerA, "Baleful Strix"); // ordering the bottom

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }
}
