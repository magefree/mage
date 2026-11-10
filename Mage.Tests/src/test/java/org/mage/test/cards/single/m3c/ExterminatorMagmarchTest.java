package org.mage.test.cards.single.m3c;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

/**
 * @author JayDi85
 */
public class ExterminatorMagmarchTest extends CardTestMultiPlayerBaseWithRangeAll {

    @Test
    public void test_CopyTargetsPermanentOfAnotherOpponent() {
        // Whenever you cast an instant or sorcery spell that targets only a single nonland permanent an opponent
        // controls, if another opponent controls one or more nonland permanents that spell could target, choose one
        // of those permanents. Copy that spell. The copy targets the chosen permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Exterminator Magmarch");
        //
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Murder"); // {1}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerC, "Silvercoat Lion"); // lion 2/2
        addCard(Zone.BATTLEFIELD, playerC, "Gladecover Scout"); // scout 1/1, hexproof

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Grizzly Bears");
        setChoice(playerA, "Silvercoat Lion"); // permanent for the copy, the scout can't be targeted

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerC, "Silvercoat Lion", 1);
        assertPermanentCount(playerC, "Gladecover Scout", 1);
    }

    @Test
    public void test_AnotherOpponentHasNoPermanentThatSpellCouldTarget_NoTrigger() {
        // Exterminator Magmarch ruling:
        // Exterminator Magmarch's first ability won't trigger if no other opponent controls any nonland permanents
        // that spell could target. If the ability does trigger but no other opponent controls any nonland permanents
        // the spell could target by the time the ability resolves, the ability won't do anything.
        // (2024-06-07)

        // Whenever you cast an instant or sorcery spell that targets only a single nonland permanent an opponent
        // controls, if another opponent controls one or more nonland permanents that spell could target, choose one
        // of those permanents. Copy that spell. The copy targets the chosen permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Exterminator Magmarch");
        //
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Murder"); // {1}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerC, "Gladecover Scout"); // scout 1/1, hexproof

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Grizzly Bears");
        checkStackSize("no trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerC, "Gladecover Scout", 1);
    }
}
