package org.mage.test.cards.single.m12;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GrandAbolisherTest extends CardTestPlayerBase {

    @Test
    public void test_MakeSureItWorksFromBattlefieldOnly() {
        skipInitShuffling();

        // related bug: #13099
        String castAbility = "Cast Consider";

        // During your turn, your opponents can't cast spells or activate abilities of artifacts, creatures, or enchantments.
        addCard(Zone.LIBRARY, playerB, "Grand Abolisher", 1); // {W}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        //
        // Draw a card.
        addCard(Zone.HAND, playerA, "Consider", 1); // {U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        String info = "turn 1 (opponent, in library - no restriction)";
        checkHandCardCount(info, 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Grand Abolisher", 0);
        checkPlayableAbility(info, 1, PhaseStep.POSTCOMBAT_MAIN, playerA, castAbility, true);

        info = "turn 2 (own, in hand - no restriction)";
        checkHandCardCount(info, 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Grand Abolisher", 1);
        checkPlayableAbility(info, 2, PhaseStep.POSTCOMBAT_MAIN, playerA, castAbility, true);

        info = "turn 3 (opponent, in hand - no restriction)";
        checkHandCardCount(info, 3, PhaseStep.PRECOMBAT_MAIN, playerB, "Grand Abolisher", 1);
        checkPlayableAbility(info, 3, PhaseStep.POSTCOMBAT_MAIN, playerA, castAbility, true);

        info = "turn 4 (own, in battlefield - must have restriction)";
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Grand Abolisher");
        waitStackResolved(4, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount(info, 4, PhaseStep.PRECOMBAT_MAIN, playerB, "Grand Abolisher", 1);
        checkPlayableAbility(info, 4, PhaseStep.POSTCOMBAT_MAIN, playerA, castAbility, false);

        info = "turn 5 (opponent, in battlefield - no restriction)";
        checkPermanentCount(info, 5, PhaseStep.PRECOMBAT_MAIN, playerB, "Grand Abolisher", 1);
        checkPlayableAbility(info, 5, PhaseStep.POSTCOMBAT_MAIN, playerA, castAbility, true);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();
    }
}
