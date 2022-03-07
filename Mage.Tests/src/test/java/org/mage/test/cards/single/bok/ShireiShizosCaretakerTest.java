package org.mage.test.cards.single.bok;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ShireiShizosCaretakerTest extends CardTestPlayerBase {
    private static final String shirei = "Shirei, Shizo's Caretaker";
    private static final String rats = "Muck Rats";
    private static final String murder = "Murder";
    private static final String blink = "Momentary Blink";

    @Test
    public void testRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, shirei);
        addCard(Zone.BATTLEFIELD, playerA, rats);
        addCard(Zone.HAND, playerA, murder);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, rats);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, shirei, 1);
        assertPermanentCount(playerA, rats, 1);
    }

    @Test
    public void testLeftBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, shirei);
        addCard(Zone.BATTLEFIELD, playerA, rats);
        addCard(Zone.HAND, playerA, murder, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, rats);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, shirei);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, shirei, 0);
        assertPermanentCount(playerA, rats, 0);
    }

    @Test
    public void testBlinked() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        addCard(Zone.BATTLEFIELD, playerA, shirei);
        addCard(Zone.BATTLEFIELD, playerA, rats);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.HAND, playerA, blink);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, rats);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, blink, shirei);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, shirei, 1);
        assertPermanentCount(playerA, rats, 0);
    }
}
