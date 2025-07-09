package org.mage.test.cards.abilities.oneshot;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;
/**
 *
 * @author notgreat
 */
public class OneShotNonTargetTest extends CardTestPlayerBase {
    @Test
    public void YorionChooseAfterTriggerTest() {
        addCard(Zone.HAND, playerA, "Yorion, Sky Nomad");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Resolute Reinforcements");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yorion, Sky Nomad");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkPermanentCount("Yorion on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yorion, Sky Nomad", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Resolute Reinforcements");
        setChoice(playerA, "Resolute Reinforcements");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertTokenCount(playerA, "Soldier Token", 2);
        assertPermanentCount(playerA, "Yorion, Sky Nomad", 1);
        assertPermanentCount(playerA, "Resolute Reinforcements", 1);
        assertTappedCount("Plains", true, 7);
    }
    @Test
    public void NonTargetAdjusterTest() {
        addCard(Zone.HAND, playerA, "Temporal Firestorm");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Squire");
        addCard(Zone.BATTLEFIELD, playerA, "Python");
        addCard(Zone.BATTLEFIELD, playerA, "Watchwolf");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Temporal Firestorm");
        setChoice(playerA, true);
        setChoice(playerA, true);
        setChoice(playerA, "Squire^Python");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertGraveyardCount(playerA, "Squire", 0);
        assertGraveyardCount(playerA, "Python", 0);
        assertGraveyardCount(playerA, "Watchwolf", 1);
    }
    @Test
    public void ModeSelectionTest() {
        addCard(Zone.HAND, playerA, "SOLDIER Military Program");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Squire", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "SOLDIER Military Program");
        setModeChoice(playerA, "2");
        setChoice(playerA, "Squire");
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        setModeChoice(playerA, "1");

        setModeChoice(playerA, "2");
        setChoice(playerA, "Squire^Soldier Token");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();
        assertPowerToughness(playerA, "Squire", 3, 4);
        assertPowerToughness(playerA, "Soldier Token", 2, 2);
    }

    @Test
    public void MuseVesselTest() {
        String muse = "Muse Vessel";
        addCard(Zone.HAND, playerA, muse);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 30);
        addCard(Zone.BATTLEFIELD, playerA, "Vizier of Tumbling Sands", 2);

        addCard(Zone.HAND, playerA, "Squire");
        addCard(Zone.HAND, playerA, "Alpha Myr");
        addCard(Zone.HAND, playerA, "Void Snare");
        addCard(Zone.HAND, playerB, "Island");
        addCard(Zone.HAND, playerB, "Corridor Monitor");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, muse, true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}: Target player", playerB);
        setChoice(playerB, "Island");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Untap", muse);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}: Target player", playerB);
        setChoice(playerB, "Corridor Monitor");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Untap", muse);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}: Target player", playerA);
        setChoice(playerA, "Squire");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: Choose");
        setChoice(playerA, "Island");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: Choose");
        setChoice(playerA, "Squire");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Squire", true);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Void Snare", muse, true); // Bounce the vessel to hand, check exile ID correctly managed
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, muse, true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{1}: Choose"); // Should activate but no possible choices

        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{3}, {T}: Target player", playerA);
        setChoice(playerA, "Alpha Myr");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{1}: Choose");
        setChoice(playerA, "Alpha Myr");
        checkPlayableAbility("Can cast on current turn", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Alpha Myr", true);
        checkPlayableAbility("Can't cast on future turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Alpha Myr", false);

        activateAbility(3, PhaseStep.BEGIN_COMBAT, playerA, "{1}: Choose");
        setChoice(playerA, "Alpha Myr");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Alpha Myr");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertGraveyardCount(playerA, 1); // Void Snare
        assertGraveyardCount(playerB, 0);
        assertExileCount(playerA, 0);
        assertExileCount(playerB, 1); // Corridor Monitor remains in exile
        assertPermanentCount(playerA, "Island", 1);
        assertPermanentCount(playerA, "Squire", 1);
        assertPermanentCount(playerA, "Alpha Myr", 1);
    }
}
