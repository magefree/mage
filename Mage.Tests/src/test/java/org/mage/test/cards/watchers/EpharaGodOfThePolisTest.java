package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX
 */
public class EpharaGodOfThePolisTest extends CardTestPlayerBase {

    /*
     * Ephara, God of the Polis
     * Legendary Enchantment Creature — God 6/5, 2WU (4)
     * Indestructible
     * As long as your devotion to white and blue is less than seven, Ephara
     * isn't a creature.
     * At the beginning of each upkeep, if you had another creature enter the
     * battlefield under your control last turn, draw a card.
     *
     */
    // test that an extra card is drawn
    @Test
    public void testDrawCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Ephara, God of the Polis");
        addCard(Zone.HAND, playerA, "Goblin Roughrider");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Roughrider", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephara, God of the Polis");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        this.assertHandCount(playerA, 2);

    }

    // test that an extra card is not drawn
    @Test
    public void testNotDrawCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Ephara, God of the Polis");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephara, God of the Polis");
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        this.assertHandCount(playerA, 1);
        this.assertPermanentCount(playerA, "Ephara, God of the Polis", 1);

    }

}
