package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SunderingArchaicTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SunderingArchaic Sundering Archaic} Sundering Archaic {6
     * Creature — Avatar
     * Converge — When this creature enters, exile target nonland permanent an opponent controls with mana value less than or equal to the number of colors of mana spent to cast this creature.
     * {2}: Put target card from a graveyard on the bottom of its owner’s library.
     * 3/3
     */
    private static final String archaic = "Sundering Archaic";

    @Test
    public void test_Exile() {
        addCard(Zone.HAND, playerA, archaic, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, archaic);
        addTarget(playerA, "Goblin Piker");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerB, "Goblin Piker", 1);
    }

    @Test
    public void test_TooCostlyForExile() {
        addCard(Zone.HAND, playerA, archaic, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, archaic);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, archaic, 1);
        assertPermanentCount(playerB, "Goblin Piker", 1);
    }
}
