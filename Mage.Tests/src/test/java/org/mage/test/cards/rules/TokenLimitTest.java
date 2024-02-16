package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */

public class TokenLimitTest extends CardTestPlayerBase {
    private static final String secure = "Secure the Wastes";
    private static final String procession = "Anointed Procession";
    private static final String warrior = "Warrior Token";

    @Test
    public void testOnePlayerHitsLimit() {
        addCard(Zone.BATTLEFIELD, playerA, procession, 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 34);
        addCard(Zone.HAND, playerA, secure, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, secure);
        setChoice(playerA, "X=16");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, secure);
        setChoice(playerA, "X=16");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, warrior, 500);
    }

    @Test
    public void testOnePlayerHitsLimitWithOtherPlayer() {
        addCard(Zone.HAND, playerA, secure);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 33);
        addCard(Zone.BATTLEFIELD, playerA, procession, 4);

        addCard(Zone.HAND, playerB, secure);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, secure);
        setChoice(playerA, "X=32");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, secure);
        setChoice(playerB, "X=2");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, warrior, 500);
        assertPermanentCount(playerB, warrior, 2);
    }
}
