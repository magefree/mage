package org.mage.test.cards.modal;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class PawPrintsTest extends CardTestPlayerBase {

    @Test
    public void test_Choose113() {
        // Test that draw effect sees power affected by counter effect

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        // {P} -- Put a +1/+1 counter on a creature you control. It gains vigilance and trample until end of turn.
        // {P}{P} -- Choose artifact or enchantment. Destroy all permanents of the chosen type.
        // {P}{P}{P} -- Draw cards equal to the greatest power among creatures you control.
        addCard(Zone.HAND, playerA, "Season of Gathering"); // Instant {4}{G}{G}

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Season of Gathering");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "3");
        addTarget(playerA, "Memnite"); // for 1
        addTarget(playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 3);

        assertPowerToughness(playerA, "Memnite", 3, 3);
        assertCounterCount("Memnite", CounterType.P1P1, 2);

    }

    @Test
    public void test_Choose123() {
        // Test that 1, 2, and 3 cannot all be selected (and that 1 and 2 will fire in that order)

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        // {P} -- Put a +1/+1 counter on a creature you control. It gains vigilance and trample until end of turn.
        // {P}{P} -- Choose artifact or enchantment. Destroy all permanents of the chosen type.
        // {P}{P}{P} -- Draw cards equal to the greatest power among creatures you control.
        addCard(Zone.HAND, playerA, "Season of Gathering"); // Instant {4}{G}{G}

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        // If one or more +1/+1 counters would be put on a creature you control, that many plus one +1/+1 counters are put on it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Hardened Scales");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Season of Gathering");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "3"); // no more paws for mode 3, see exception below
        addTarget(playerA, "Memnite"); // for 1
        setChoice(playerA, "Enchantment");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        try {
            execute();
        } catch (AssertionError e) {
            Assert.assertTrue("mode 3 must not be able to choose due total paws cost", e.getMessage().contains("Can't use mode: 3"));
        }
    }

    @Test
    public void test_Choose2111() {
        // Test that 1 and 2 will fire in that order when choices are made in reverse

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        // {P} -- Put a +1/+1 counter on a creature you control. It gains vigilance and trample until end of turn.
        // {P}{P} -- Choose artifact or enchantment. Destroy all permanents of the chosen type.
        // {P}{P}{P} -- Draw cards equal to the greatest power among creatures you control.
        addCard(Zone.HAND, playerA, "Season of Gathering"); // Instant {4}{G}{G}

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        // If one or more +1/+1 counters would be put on a creature you control, that many plus one +1/+1 counters are put on it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Hardened Scales");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Season of Gathering");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        addTarget(playerA, "Memnite"); // for 1
        addTarget(playerA, "Memnite"); // for 1
        addTarget(playerA, "Memnite"); // for 1
        setChoice(playerA, "Enchantment");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Add one more counter per choice from Hardened Scales, which was still on the battlefield when the counter placing effect triggered
        assertPowerToughness(playerA, "Memnite", 7, 7);
        assertCounterCount("Memnite", CounterType.P1P1, 6);

        // But not anymore...
        assertPermanentCount(playerA, "Hardened Scales", 0);
        assertGraveyardCount(playerA, "Hardened Scales", 1);

    }

    @Test
    public void test_Choose1x5() {
        // Test that max amount of modes can be chosen

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        // {P} -- Put a +1/+1 counter on a creature you control. It gains vigilance and trample until end of turn.
        // {P}{P} -- Choose artifact or enchantment. Destroy all permanents of the chosen type.
        // {P}{P}{P} -- Draw cards equal to the greatest power among creatures you control.
        addCard(Zone.HAND, playerA, "Season of Gathering"); // Instant {4}{G}{G}

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Season of Gathering");
        for (int i = 0; i < 5; ++i){
            setModeChoice(playerA, "1");
            addTarget(playerA, "Memnite");
        }

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Memnite", 6, 6);
        assertCounterCount("Memnite", CounterType.P1P1, 5);

    }

    @Test
    public void test_Choose23() {
        // Test that 2 and 3 fire in that order

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        // {P} -- Put a +1/+1 counter on a creature you control. It gains vigilance and trample until end of turn.
        // {P}{P} -- Choose artifact or enchantment. Destroy all permanents of the chosen type.
        // {P}{P}{P} -- Draw cards equal to the greatest power among creatures you control.
        addCard(Zone.HAND, playerA, "Season of Gathering"); // Instant {4}{G}{G}

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        // If one or more +1/+1 counters would be put on a creature you control, that many plus one +1/+1 counters are put on it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Hardened Scales");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Season of Gathering");
        setModeChoice(playerA, "3");
        setModeChoice(playerA, "2");
        setChoice(playerA, "Artifact");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Hardened Scales", 1);
        assertGraveyardCount(playerA, "Memnite", 1);

        // Draw effect saw no creatures, so no cards
        assertHandCount(playerA, 0);

    }

    @Test
    public void test_Choose122() {
        // Test destroying both artifacts and enchantments

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        // {P} -- Put a +1/+1 counter on a creature you control. It gains vigilance and trample until end of turn.
        // {P}{P} -- Choose artifact or enchantment. Destroy all permanents of the chosen type.
        // {P}{P}{P} -- Draw cards equal to the greatest power among creatures you control.
        addCard(Zone.HAND, playerA, "Season of Gathering"); // Instant {4}{G}{G}

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        // If one or more +1/+1 counters would be put on a creature you control, that many plus one +1/+1 counters are put on it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Hardened Scales");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Season of Gathering");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "2");
        addTarget(playerA, "Memnite");
        setChoice(playerA, "Artifact");
        setChoice(playerA, "Enchantment");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Hardened Scales", 1);
        assertGraveyardCount(playerA, "Memnite", 1);

    }
}
