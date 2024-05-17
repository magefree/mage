package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SentinelSarahLyonsTest extends CardTestPlayerBase {

    /*
     * Sentinel Sarah Lyons {3}{R}{W}
     * Legendary Creature - Human Knight (4/4)
     * Haste
     * As long as an artifact entered the battlefield under your control this turn, creatures you control get +2/+2.
     * Battalion - Whenever Sentinel Sarah Lyons and at least two other creatures attack,
     * Sentinel Sarah Lyons deals damage equal to the number of artifacts you control to target player.
     */

    @Test
    public void test_ArtifactEntersUnderYourControlBeforeAndAfter() {
        addCard(Zone.BATTLEFIELD, playerA, "Sentinel Sarah Lyons", 1);
        addCard(Zone.HAND, playerA, "Darksteel Relic", 1); // Artifact w/ Indestructible

        // Before casting an artifact (4/4)
        checkPT("1: before casting Relic", 1, PhaseStep.UPKEEP, playerA, "Sentinel Sarah Lyons", 4, 4);

        // Casting the artifact (should become a 6/6 until end of turn)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Darksteel Relic");
        checkPT("1: after casting Relic", 1, PhaseStep.END_TURN, playerA, "Sentinel Sarah Lyons", 6, 6);

        // Turn after casting the artifact (should return to 4/4)
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPowerToughness(playerA, "Sentinel Sarah Lyons", 4, 4);
    }

    @Test
    public void test_ArtifactEntersUnderOpponentsControl() {
        addCard(Zone.BATTLEFIELD, playerA, "Sentinel Sarah Lyons", 1);

        addCard(Zone.HAND, playerB, "Darksteel Relic", 1);
        // Artifact w/ "You may cast spells as though they had flash."
        addCard(Zone.BATTLEFIELD, playerB, "Vedalken Orrery", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Darksteel Relic");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Sentinel Sarah Lyons", 4, 4);
    }

    @Test
    public void test_NonartifactEntersUnderYourControl() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Sentinel Sarah Lyons", 1);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Sentinel Sarah Lyons", 4, 4);
    }

    @Test
    public void test_ArtifactEntersBeforeSarahLyons() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 5);
        addCard(Zone.HAND, playerA, "Sentinel Sarah Lyons", 1);
        addCard(Zone.HAND, playerA, "Darksteel Relic", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Darksteel Relic", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sentinel Sarah Lyons");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Sentinel Sarah Lyons", 6, 6);
    }
}
