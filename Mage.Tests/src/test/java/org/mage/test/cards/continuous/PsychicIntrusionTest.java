package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */

public class PsychicIntrusionTest extends CardTestPlayerBase {

    /**
     * Tests that exiled card can be cast from Exile the next turn with any mana
     */
    @Test
    public void testCastFromExile() {
        // Psychic Intrusion  {3}{U}{B}
        // Sorcery
        // Target opponent reveals their hand. You choose a nonland card from that player's
        // graveyard or hand and exile it. You may cast that card for as long as it remains exiled,
        // and you may spend mana as though it were mana of any color to cast that spell.
        addCard(Zone.HAND, playerA, "Psychic Intrusion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.HAND, playerB, "Elspeth, Sun's Champion", 1); // {4}{W}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Psychic Intrusion", playerB);
        setChoice(playerA, "Elspeth, Sun's Champion");

        // cast from exile with any mana
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Elspeth, Sun's Champion");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Psychic Intrusion", 1);
        assertHandCount(playerB, "Elspeth, Sun's Champion", 0);
        assertPermanentCount(playerA, "Elspeth, Sun's Champion", 1);

    }


}
