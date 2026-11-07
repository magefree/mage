package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class FireGiantsFuryTest extends CardTestPlayerBase {

    @Test
    public void test_CombatDamage_MayPlayUntilEndOfYourNextTurn() {
        skipInitShuffling();

        // Target Giant you control gets +2/+2 and gains trample until end of turn. Whenever it deals combat damage
        // to a player this turn, exile that many cards from the top of your library. Until the end of your next
        // turn, you may play those cards.
        addCard(Zone.HAND, playerA, "Fire Giant's Fury"); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // top card, {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire Giant's Fury", "Hill Giant");
        attack(1, playerA, "Hill Giant");

        checkPlayableAbility("opponent turn", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("end of your next turn", 3, PhaseStep.END_TURN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("after your next turn", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 5);
        assertExileCount(playerA, 5);
    }
}
