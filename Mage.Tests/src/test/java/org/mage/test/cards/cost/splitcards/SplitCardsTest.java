package org.mage.test.cards.cost.splitcards;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SplitCardsTest extends CardTestPlayerBase {

    @Test
    public void testReturnCardFromSoulfireGrandMaster() {
        // Total CMC of Failure // Comply is 3, so should be exiled by Transgress the Mind.
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 6);

        // Lifelink
        // Instant and sorcery spells you control have lifelink.
        // {2}{U/R}{U/R}: The next time you cast an instant or sorcery spell from your hand this turn, put that card into your hand instead of your graveyard as it resolves.
        addCard(Zone.BATTLEFIELD, playerA, "Soulfire Grand Master");

        // Fire - Instant {1}{R}
        // Fire deals 2 damage divided as you choose among one or two target creatures and/or players.
        // Ice - Instant {1}{U}
        // Tap target permanent.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Fire // Ice");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{U/R}{U/R}");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fire", playerB);
        addTargetAmount(playerA, "targetPlayer=PlayerB", 2);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 22);
        assertLife(playerB, 18);

        assertHandCount(playerA, "Fire // Ice", 1);
    }

}
