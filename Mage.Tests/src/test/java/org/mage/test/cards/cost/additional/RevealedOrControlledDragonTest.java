package org.mage.test.cards.cost.additional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class RevealedOrControlledDragonTest extends CardTestPlayerBase {
    private static final String dragon = "Shivan Dragon";
    private static final String roar = "Draconic Roar";
    private static final String lion = "Silvercoat Lion";
    private static final String orator = "Orator of Ojutai";
    private static final String sentinels = "Scaleguard Sentinels";

    public void addDragonToHand(String cardName) {
        addCard(Zone.HAND, playerA, dragon);
        addCard(Zone.HAND, playerA, cardName);
    }

    public void addDragonToBattlefield(String cardName) {
        addCard(Zone.BATTLEFIELD, playerA, dragon);
        addCard(Zone.HAND, playerA, cardName);
    }

    @Test
    public void testRoarHand() {
        addDragonToHand(roar);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, lion);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, roar, lion);
        setChoice(playerA, dragon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerA, roar, 1);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void testRoarBattlefield() {
        addDragonToBattlefield(roar);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, lion);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, roar, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerA, roar, 1);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void testRoarFalse() {
        addCard(Zone.HAND, playerA, roar);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, lion);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, roar, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerA, roar, 1);
        assertLife(playerA, 20);
    }

    @Test
    public void testOratorHand() {
        addDragonToHand(orator);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, orator);
        setChoice(playerA, dragon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, orator, 1);
        assertHandCount(playerA, 1 + 1);
    }

    @Test
    public void testOratorBattlefield() {
        addDragonToBattlefield(orator);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, orator);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, orator, 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testOratorFalse() {
        addCard(Zone.HAND, playerA, orator);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, orator);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, orator, 1);
        assertHandCount(playerA, 0);
    }

    @Test
    public void testSentinelsHand() {
        addDragonToHand(sentinels);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sentinels);
        setChoice(playerA, dragon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sentinels, 1);
        assertCounterCount(playerA, sentinels, CounterType.P1P1, 1);
    }

    @Test
    public void testSentinelsBattlefield() {
        addDragonToBattlefield(sentinels);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sentinels);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sentinels, 1);
        assertCounterCount(playerA, sentinels, CounterType.P1P1, 1);
    }

    @Test
    public void testSentinelsFalse() {
        addCard(Zone.HAND, playerA, sentinels);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sentinels);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sentinels, 1);
        assertCounterCount(playerA, sentinels, CounterType.P1P1, 0);
    }
}
