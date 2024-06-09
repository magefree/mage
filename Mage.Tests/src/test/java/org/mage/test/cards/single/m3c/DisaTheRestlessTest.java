package org.mage.test.cards.single.m3c;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DisaTheRestlessTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DisaTheRestless Disa the Restless} {2}{B}{R}{G}
     * Legendary Creature — Human Scout
     * Whenever a Lhurgoyf permanent card is put into your graveyard from anywhere other than the battlefield, put it onto the battlefield.
     * Whenever one or more creatures you control deal combat damage to a player, create a Tarmogoyf token.
     * 5/6
     */
    private static final String disa = "Disa the Restless";

    @Test
    public void test_Discard() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, disa);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Tarmogoyf", 2);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 2);
        addCard(Zone.HAND, playerA, "One with Nothing");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "One with Nothing");
        setChoice(playerA, "Whenever"); // stack triggers

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Tarmogoyf", 2);
        assertGraveyardCount(playerA, "Grizzly Bears", 2);
    }

    @Test
    public void test_Destroy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, disa);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Tarmogoyf", 1);
        addCard(Zone.HAND, playerA, "Go for the Throat");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Go for the Throat", "Tarmogoyf");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Tarmogoyf", 0);
        assertGraveyardCount(playerA, "Tarmogoyf", 1);
    }
}
