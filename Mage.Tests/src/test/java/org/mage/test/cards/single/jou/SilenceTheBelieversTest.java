package org.mage.test.cards.single.jou;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class SilenceTheBelieversTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SilenceTheBelievers}
     * <p>
     * {2}{B}{B} Instant<br>
     * Strive - This spell costs {2}{B} more to cast for each target beyond the first.<br>
     * Exile any number of target creatures and all Auras attached to them.
     */
    private static final String SILENCE_THE_BELIEVERS = "Silence the Believers";

    @Test
    public void test_ExileAtSameTime_OneOrMore_KetramoseTheNewDawn() {
        // Whenever one or more cards are put into exile from graveyards and/or the battlefield during your turn,
        // you draw a card and lose 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Ketramose, the New Dawn"); // 4/4
        //
        addCard(Zone.HAND, playerA, SILENCE_THE_BELIEVERS);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4 + 3);
        //
        addCard(Zone.HAND, playerA, "Holy Strength"); // {W}, aura, +1/+2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // lion 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // creatures and auras are exiled at the same time, so it triggers once
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, SILENCE_THE_BELIEVERS, "Grizzly Bears^Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, "Grizzly Bears", 1);
        assertExileCount(playerB, "Silvercoat Lion", 1);
        assertExileCount(playerA, "Holy Strength", 1);
        assertLife(playerA, 20 - 1);
        assertHandCount(playerA, 1);
    }
}
