package org.mage.test.cards.single.acr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SenuKeenEyedProtectorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SenuKeenEyedProtector Senu, Keen-Eyed Protector} {1}{W}
     * Legendary Creature — Bird Scout
     * Flying, vigilance
     * {T}, Exile Senu, Keen-Eyed Protector: You gain 2 life and scry 2.
     * When a legendary creature you control attacks and isn’t blocked, if Senu is exiled, put it onto the battlefield attacking.
     * 2/1
     */
    private static final String senu = "Senu, Keen-Eyed Protector";

    @Test
    public void test_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.EXILED, playerA, senu);
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda");

        attack(1, playerA, "Isamaru, Hound of Konda");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2 - 2);
        assertPermanentCount(playerA, senu, 1);
    }

    @Test
    public void test_NoTrigger_FromGraveyard() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, senu);
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda");

        attack(1, playerA, "Isamaru, Hound of Konda");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerA, senu, 0);
    }

    @Test
    public void test_NoTrigger_FromHand() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, senu);
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda");

        attack(1, playerA, "Isamaru, Hound of Konda");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerA, senu, 0);
    }

    @Test
    public void test_NoTrigger_NonLegendaryAttack() {
        setStrictChooseMode(true);

        addCard(Zone.EXILED, playerA, senu);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        attack(1, playerA, "Elite Vanguard");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerA, senu, 0);
    }

    @Test
    public void test_NoTrigger_OpponentAttack() {
        setStrictChooseMode(true);

        addCard(Zone.EXILED, playerB, senu);
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda");

        attack(1, playerA, "Isamaru, Hound of Konda");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerA, senu, 0);
        assertPermanentCount(playerB, senu, 0);
    }
}
