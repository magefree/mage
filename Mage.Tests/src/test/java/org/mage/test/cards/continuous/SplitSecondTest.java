package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class SplitSecondTest extends CardTestPlayerBase {

    @Test
    public void testCounterSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 4);
        addCard(Zone.HAND, playerA, "Sudden Shock");
        addCard(Zone.HAND, playerA, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sudden Shock", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Counterspell", "Sudden Shock");

        setStopAt(1, PhaseStep.END_TURN);

        // TODO: Needed, see https://github.com/magefree/mage/issues/8973
        try {
            execute();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Cast Counterspell$target=Sudden Shock")) {
                Assert.fail("Should have thrown error about trying to use Counterspell, but got:\n" + e.getMessage());
            }
        }

        // TODO: Re-enable when checkPlayableAbility can be used instead of try-catch
//        assertHandCount(playerA, "Counterspell", 1);
//        assertGraveyardCount(playerA, "Sudden Shock", 1);
//        assertLife(playerB, 20 - 2);
    }

    @Test
    public void testCopiedSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Doublecast");
        addCard(Zone.HAND, playerA, "Sudden Shock");
        addCard(Zone.HAND, playerA, "Raging Goblin");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doublecast");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sudden Shock", playerB);

        // No split second spells are on the stack, effect should not apply anymore
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Raging Goblin");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2 - 2);
        assertPermanentCount(playerA, "Raging Goblin", 1);
    }

    private static final String molten = "Molten Disaster";
    /* {X}{R}{R} Sorcery
     Kicker {R}
     If this spell was kicked, it has split second.
     Molten Disaster deals X damage to each creature without flying and each player.
     */
    private static final String shock = "Shock";
    private static final String crab = "Fortress Crab"; // 1/6
    private static final String gnomes = "Bottle Gnomes"; // Sacrifice Bottle Gnomes: You gain 3 life.
    private static final String bear = "Runeclaw Bear"; // 2/2
    private static final String drake = "Seacoast Drake"; // 1/3 flying

    public void setupMoltenDisaster() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerA, molten);
        addCard(Zone.HAND, playerB, shock);
        addCard(Zone.BATTLEFIELD, playerA, crab);
        addCard(Zone.BATTLEFIELD, playerA, gnomes);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.BATTLEFIELD, playerB, drake);
    }

    @Test
    public void testMoltenDisasterUnkicked() {
        setupMoltenDisaster();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, molten);
        setChoice(playerA, false); // no kicker
        setChoice(playerA, "X=1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, shock, crab);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1 + 3);
        assertLife(playerB, 20 - 1);
        assertDamageReceived(playerA, crab, 1 + 2);
        assertGraveyardCount(playerA, gnomes, 1);
        assertDamageReceived(playerB, bear, 1);
        assertDamageReceived(playerB, drake, 0);
    }

    @Test
    public void testMoltenDisasterKickedNoSpell() {
        setupMoltenDisaster();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, molten);
        setChoice(playerA, true); // no kicker
        setChoice(playerA, "X=1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, shock, crab);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);

        try {
            execute();
            throw new AssertionError("expected failure to cast Shock");
        } catch (AssertionError e) {
            Assert.assertTrue(e.getMessage().contains("Can't find ability to activate command: Cast Shock$target=Fortress Crab"));
        }


    }

    @Test
    public void testMoltenDisasterKickedNoAbility() {
        setupMoltenDisaster();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, molten);
        setChoice(playerA, true); // no kicker
        setChoice(playerA, "X=1");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);

        try {
            execute();
            throw new AssertionError("expected failure to activate sacrifice ability");
        } catch (AssertionError e) {
            Assert.assertTrue(e.getMessage().contains("Can't find ability to activate command: Sacrifice"));
        }

    }

}
