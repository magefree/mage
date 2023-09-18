package org.mage.test.cards.single.vow;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class SaviorOfOllenbockTest extends CardTestPlayerBase {
    private static final String savior = "Savior of Ollenbock";
    private static final String lion = "Silvercoat Lion";
    private static final String bear = "Grizzly Bears";
    private static final String murder = "Murder";

    @Test
    public void test_ExilePermanent() {
        addCard(Zone.BATTLEFIELD, playerA, savior);
        addCard(Zone.BATTLEFIELD, playerA, lion);

        attack(1, playerA, savior);
        attack(1, playerA, lion);
        addTarget(playerA, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, lion, 1);
    }

    @Test
    public void test_ExileCard() {
        addCard(Zone.BATTLEFIELD, playerA, savior);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.GRAVEYARD, playerA, bear);

        attack(1, playerA, savior);
        attack(1, playerA, lion);
        addTarget(playerA, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, bear, 1);
    }

    @Test
    public void test_ExilePermanentAndReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, savior);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, murder);

        attack(1, playerA, savior);
        attack(1, playerA, lion);
        addTarget(playerA, lion);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, savior);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, savior, 0);
        assertPermanentCount(playerA, lion, 1);
        assertGraveyardCount(playerA, murder, 1);
    }

    @Test
    public void test_ExileCardAndReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, savior);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.GRAVEYARD, playerB, bear);
        addCard(Zone.HAND, playerA, murder);

        attack(1, playerA, savior);
        attack(1, playerA, lion);
        addTarget(playerA, bear);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, savior);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, savior, 0);
        assertPermanentCount(playerA, lion, 1);
        assertPermanentCount(playerB, bear, 1);
        assertGraveyardCount(playerA, murder, 1);
    }
}
