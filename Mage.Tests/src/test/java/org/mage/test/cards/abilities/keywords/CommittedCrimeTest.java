package org.mage.test.cards.abilities.keywords;

import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class CommittedCrimeTest extends CardTestPlayerBase {

    private void makeTester() {
        addCustomCardWithAbility(
                "tester", playerA, new CommittedCrimeTriggeredAbility(new GainLifeEffect(1), false)
        );
    }

    private static final String spike = "Lava Spike";

    @Test
    public void testSpikeOpponent() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, spike);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spike, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void testSpikeSelf() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, spike);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spike, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 3);
        assertLife(playerB, 20);
    }

    private static final String voidslime = "Voidslime";
    private static final String sanctifier = "Cathedral Sanctifier";

    @Test
    public void testCounterSpellOpponent() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.HAND, playerA, voidslime);
        addCard(Zone.HAND, playerB, sanctifier);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, sanctifier);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, voidslime, sanctifier);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
    }

    @Test
    public void testCounterAbilityOpponent() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.HAND, playerA, voidslime);
        addCard(Zone.HAND, playerB, sanctifier);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, sanctifier);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, voidslime, "stack");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20);
    }

    @Test
    public void testCounterSpellSelf() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, voidslime);
        addCard(Zone.HAND, playerA, sanctifier);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sanctifier);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, voidslime, sanctifier);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
    }

    @Test
    public void testCounterAbilitySelf() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, voidslime);
        addCard(Zone.HAND, playerA, sanctifier);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sanctifier);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, voidslime, "stack");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
    }

    private static final String bear = "Grizzly Bears";
    private static final String murder = "Murder";

    @Test
    public void testMurderOpponent() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, murder);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, bear, 0);
        assertGraveyardCount(playerB, bear, 1);
        assertGraveyardCount(playerA, murder, 1);
        assertLife(playerA, 20 + 1);
    }

    @Test
    public void testMurderSelf() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, murder);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bear, 0);
        assertGraveyardCount(playerA, bear, 1);
        assertGraveyardCount(playerA, murder, 1);
        assertLife(playerA, 20);
    }

    private static final String purge = "Coffin Purge";

    @Test
    public void testGraveyardOpponent() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, purge);
        addCard(Zone.GRAVEYARD, playerB, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, purge, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, bear, 0);
        assertExileCount(playerB, bear, 1);
        assertLife(playerA, 20 + 1);
    }

    @Test
    public void testGraveyardSelf() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, purge);
        addCard(Zone.GRAVEYARD, playerA, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, purge, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bear, 0);
        assertExileCount(playerA, bear, 1);
        assertLife(playerA, 20);
    }

    private static final String desert = "Sunscorched Desert";

    @Test
    public void testTriggerOpponent() {
        makeTester();
        addCard(Zone.HAND, playerA, desert);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, desert);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20 - 1);
    }

    @Test
    public void testTriggerSelf() {
        makeTester();
        addCard(Zone.HAND, playerA, desert);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, desert);
        addTarget(playerA, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1);
    }

    private static final String fireslinger = "Goblin Fireslinger";

    @Test
    public void testActivateOpponent() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, fireslinger);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20 - 1);
    }

    @Test
    public void testActivateSelf() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, fireslinger);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}", playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1);
    }
}
