package org.mage.test.cards.single.c19;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ElshaOfTheInfiniteTest extends CardTestPlayerBase {

    @Test
    public void test_MustApplyToTopCardOnly() {
        // bug: flash ability can be applied to all cards in hand
        // https://github.com/magefree/mage/issues/7605

        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromHand(playerA);
        skipInitShuffling();

        // You may look at the top card of your library any time.
        // You may cast noncreature spells from the top of your library. If you cast a spell this way, you may cast it as though it had flash.
        addCard(Zone.BATTLEFIELD, playerA, "Elsha of the Infinite");
        //
        // Sorcery
        // Bolt of Keranos deals 3 damage to any target. Scry 1.
        addCard(Zone.HAND, playerA, "Bolt of Keranos", 1);// {1}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        // Birgi, God of Storytelling - creature, {2}{R}
        // Harnfel, Horn of Bounty - artifact, {4}{R}
        addCard(Zone.LIBRARY, playerA, "Birgi, God of Storytelling", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        // possible bug: sorcery card in hand got flash and can be playable
        checkPlayableAbility("hand on upkeep, can't cast", 1, PhaseStep.UPKEEP, playerA, "Cast Bolt of Keranos", false);
        checkPlayableAbility("lib on upkeep, can't cast left", 1, PhaseStep.UPKEEP, playerA, "Cast Birgi, God of Storytelling", false);
        checkPlayableAbility("lib on upkeep, can cast right", 1, PhaseStep.UPKEEP, playerA, "Cast Harnfel, Horn of Bounty", true);
        //
        checkPlayableAbility("hand on main", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bolt of Keranos", true);
        checkPlayableAbility("lib on main, can't cast left", 1, PhaseStep.UPKEEP, playerA, "Cast Birgi, God of Storytelling", false);
        checkPlayableAbility("lib on main, can cast right", 1, PhaseStep.UPKEEP, playerA, "Cast Harnfel, Horn of Bounty", true);

        checkLibraryCount("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birgi, God of Storytelling", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Harnfel, Horn of Bounty");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Harnfel, Horn of Bounty", 1);
    }
}
