package org.mage.test.cards.cost.additional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801, xenohedron
 */
public class RevealedOrControlledDragonTest extends CardTestPlayerBase {
    private static final String dragon = "Shivan Dragon";
    private static final String lion = "Silvercoat Lion";
    private static final String orator = "Orator of Ojutai";
    private static final String sentinels = "Scaleguard Sentinels";

    private static final String roar = "Draconic Roar"; // 1R
    // As an additional cost to cast this spell, you may reveal a Dragon card from your hand.
    // Draconic Roar deals 3 damage to target creature. If you revealed a Dragon card or controlled a Dragon
    // as you cast this spell, Draconic Roar deals 3 damage to that creature’s controller.
    private static final String crab = "Fortress Crab"; // 1/6 for target
    private static final String hatchling = "Dragon Hatchling"; // for reveal
    private static final String whelp = "Dragon Whelp"; // for battlefield
    private static final String vampire = "Bloodthrone Vampire"; // for sac
    private static final String hellkite = "Bogardan Hellkite"; // for flash (6RR)
    // When Bogardan Hellkite enters the battlefield, it deals 5 damage divided as you choose among any number of targets.

    @Test
    public void noDragon() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, roar);
        addCard(Zone.BATTLEFIELD, playerB, crab);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, roar);
        addTarget(playerA, crab);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerB, crab, 3);
        assertLife(playerB, 20);
    }

    @Test
    public void chooseNotToReveal() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, roar);
        addCard(Zone.BATTLEFIELD, playerB, crab);
        addCard(Zone.HAND, playerA, hatchling);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, roar);
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // no reveal
        addTarget(playerA, crab);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerB, crab, 3);
        assertLife(playerB, 20);
    }

    @Test
    public void revealDragon() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, roar);
        addCard(Zone.BATTLEFIELD, playerB, crab);
        addCard(Zone.HAND, playerA, hatchling);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, roar);
        setChoice(playerA, hatchling); // to reveal
        addTarget(playerA, crab);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerB, crab, 3);
        assertLife(playerB, 17);
    }

    @Test
    public void controlDragon() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, roar);
        addCard(Zone.BATTLEFIELD, playerB, crab);
        addCard(Zone.BATTLEFIELD, playerA, whelp);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, roar);
        addTarget(playerA, crab);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerB, crab, 3);
        assertLife(playerB, 17);
    }

    @Test
    public void controlDragonSacBeforeResolve() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, roar);
        addCard(Zone.BATTLEFIELD, playerB, crab);
        addCard(Zone.BATTLEFIELD, playerA, whelp);
        addCard(Zone.BATTLEFIELD, playerA, vampire);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, roar);
        addTarget(playerA, crab);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, whelp);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerB, crab, 3);
        assertLife(playerB, 17);
        assertPowerToughness(playerA, vampire, 3, 3);
    }

    @Test
    public void controlDragonOnlyAfterCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.HAND, playerA, roar);
        addCard(Zone.BATTLEFIELD, playerB, crab);
        addCard(Zone.HAND, playerA, hellkite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, roar);
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // no reveal
        addTarget(playerA, crab);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hellkite);
        addTargetAmount(playerA, playerB, 5);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerB, crab, 3);
        assertLife(playerB, 15); // 5 damage from hellkite, but 0 from roar
    }

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
