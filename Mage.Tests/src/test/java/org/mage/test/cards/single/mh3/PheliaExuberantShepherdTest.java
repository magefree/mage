package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class PheliaExuberantShepherdTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.p.PheliaExuberantShepherd Phelia, Exuberant Sheperd} {1}{W}
     * Legendary Creature — Dog
     * Flash
     * Whenever Phelia, Exuberant Shepherd attacks, exile up to one other target nonland permanent. At the beginning of the next end step, return that card to the battlefield under its owner’s control. If it entered under your control, put a +1/+1 counter on Phelia.
     * 2/2
     */
    private static final String phelia = "Phelia, Exuberant Shepherd";

    @Test
    public void test_Simple_Opponent() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, phelia, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);

        attack(1, playerA, phelia, playerB);
        addTarget(playerA, "Memnite");
        checkExileCount("Memnite got exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Memnite", 1);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, phelia, 1);
        assertPowerToughness(playerA, phelia, 2 + 0, 2 + 0);
        assertPermanentCount(playerB, "Memnite", 1);
    }

    @Test
    public void test_Simple_Owned() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, phelia, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);

        attack(1, playerA, phelia, playerB);
        addTarget(playerA, "Memnite");
        checkExileCount("Memnite got exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 1);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, phelia, 1);
        assertPowerToughness(playerA, phelia, 2 + 1, 2 + 1);
        assertPermanentCount(playerA, "Memnite", 1);
    }

    @Test
    public void test_Killed_OnTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, phelia, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Doom Blade", 1);

        attack(1, playerA, phelia, playerB);
        addTarget(playerA, "Memnite");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Doom Blade", phelia); // the attack trigger on the stack.

        checkExileCount("Memnite got exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 1);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, phelia, 0);
        assertPermanentCount(playerA, "Memnite", 1);
    }

    @Test
    public void test_Killed_AfterTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, phelia, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Doom Blade", 1);

        attack(1, playerA, phelia, playerB);
        addTarget(playerA, "Memnite");

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Doom Blade", phelia);

        checkExileCount("Memnite got exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 1);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, phelia, 0);
        assertPermanentCount(playerA, "Memnite", 1);
    }

    @Test
    public void test_Blink_OnTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, phelia, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Ephemerate", 1);

        attack(1, playerA, phelia, playerB);
        addTarget(playerA, "Memnite");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Ephemerate", phelia); // the attack trigger on the stack.

        checkExileCount("Memnite got exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 1);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, phelia, 1);
        assertPowerToughness(playerA, phelia, 2 + 0, 2 + 0);
        assertPermanentCount(playerA, "Memnite", 1);
    }

    @Test
    public void test_Blink_AfterTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, phelia, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Ephemerate", 1);

        attack(1, playerA, phelia, playerB);
        addTarget(playerA, "Memnite");

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Ephemerate", phelia);

        checkExileCount("Memnite got exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 1);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, phelia, 1);
        assertPowerToughness(playerA, phelia, 2 + 0, 2 + 0);
        assertPermanentCount(playerA, "Memnite", 1);
    }

}
