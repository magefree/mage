package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Added this test class to test issue #5880.
 * https://github.com/magefree/mage/issues/5880
 *
 * Chandra's Embercat's effect could not be used on Chandra planeswalkers.
 *
 * Card Type: Creature — Elemental Cat
 * P/T: 2 / 2
 * Description: T: Add R. Spend this mana only to cast an Elemental spell or a Chandra planeswalker spell.
 * @author jgray1206
 */
public class ChandrasEmbercatTest extends CardTestPlayerBase {

    /**
     * Make sure we can use the mana to cast elementals
     */
    @Test
    public void testCanCastElementalWithMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra's Embercat", 1);

        //An elemental creature that costs {1}{R}
        addCard(Zone.HAND, playerA, "Chandra's Embercat", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chandra's Embercat");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chandra's Embercat", 2);

    }

    /**
     * Make sure we can use the mana to cast Chandra Planeswalkers
     */
    @Test
    public void testCanCastChandraPlaneswalkerWithMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra's Embercat", 1);

        //A Chandra Planeswalker that costs {3}{R}
        addCard(Zone.HAND, playerA, "Chandra, Novice Pyromancer", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chandra, Novice Pyromancer");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chandra, Novice Pyromancer", 1);

    }

    /**
     * Make sure we can't use the mana to cast non-Chandra Planeswalkers.
     */
    @Test
    public void testCantCastNonChandraPlaneswalkerWithMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra's Embercat", 1);

        //A non-Chandra planeswalker that costs {2}{U}{U}
        addCard(Zone.HAND, playerA, "Jace, the Mind Sculptor", 1);

        checkPlayableAbility("Mana should be restricted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Jace", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Jace, the Mind Sculptor", 0);

    }

    /**
     * Make sure we can't use the mana to cast non-Elemental creatures.
     */
    @Test
    public void testCantCastNonElementalCreatureWithMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra's Embercat", 1);

        //A non-elemental creature that costs {1}{R}
        addCard(Zone.HAND, playerA, "Raptor Hatchling", 1);

        checkPlayableAbility("Mana should be restricted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Raptor", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Raptor Hatchling", 0);
    }
}
