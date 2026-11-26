package org.mage.test.cards.single.grn;

import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DarkbladeAgentTest extends CardTestPlayerBase {

    @Test
    public void test_Surveilled() {
        skipInitShuffling();
        // As long as you've surveilled this turn, this creature has deathtouch and "Whenever this creature deals
        // combat damage to a player, you draw a card."
        addCard(Zone.BATTLEFIELD, playerA, "Darkblade Agent"); // 1/1
        //
        addCard(Zone.HAND, playerA, "Consider"); // {U}, surveil 1, then draw
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Forest", 5);

        checkAbility("before surveil", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Darkblade Agent", DeathtouchAbility.class, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, "Forest"); // surveil to graveyard
        checkAbility("after surveil", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Darkblade Agent", DeathtouchAbility.class, true);
        checkAbility("next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Darkblade Agent", DeathtouchAbility.class, false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OpponentSurveilled() {
        skipInitShuffling();
        // As long as you've surveilled this turn, this creature has deathtouch and "Whenever this creature deals
        // combat damage to a player, you draw a card."
        addCard(Zone.BATTLEFIELD, playerA, "Darkblade Agent"); // 1/1
        //
        addCard(Zone.HAND, playerB, "Consider"); // {U}, surveil 1, then draw
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Forest", 5);

        // only your own surveil counts
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Consider");
        addTarget(playerB, "Forest"); // surveil to graveyard
        checkAbility("opponent surveilled", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Darkblade Agent", DeathtouchAbility.class, false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }
}
