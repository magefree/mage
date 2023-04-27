package org.mage.test.cards.single.tor;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class RadiateTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_Play_Manual() {
        // Choose target instant or sorcery spell that targets only a single permanent or player. Copy that spell
        // for each other permanent or player the spell could target. Each copy targets a different one of those
        // permanents and players.
        addCard(Zone.HAND, playerA, "Radiate", 6); // {3}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Kitesail Corsair", 2);

        // cast bolt and copy spell for each another target
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // skip stack order
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Radiate", "Lightning Bolt", "Lightning Bolt");
        checkStackSize("before radiate", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        // must have: 2x for corsairs, 2x for bears, 1x for A
        checkStackSize("after radiate", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1 + 5);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, 6); // 6 lands
        assertPermanentCount(playerB, 0);
        assertLife(playerA, 20 - 3);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_Play_AI() {
        // This test has trouble now but the manual version works

        // Choose target instant or sorcery spell that targets only a single permanent or player. Copy that spell
        // for each other permanent or player the spell could target. Each copy targets a different one of those
        // permanents and players.
        addCard(Zone.HAND, playerA, "Radiate", 6); // {3}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Kitesail Corsair", 2);

        // cast bolt and copy spell for each another target
        // must call commands manually cause it's a bad scenario and AI don't cast it itself
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Radiate", "Lightning Bolt", "Lightning Bolt");
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA); // but AI can choose targets

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, 6); // 6 lands
        assertPermanentCount(playerB, 0);
        assertLife(playerA, 20 - 3);
        assertLife(playerB, 20 - 3);
    }
}
