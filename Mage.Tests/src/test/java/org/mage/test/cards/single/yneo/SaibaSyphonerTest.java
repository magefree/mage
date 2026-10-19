package org.mage.test.cards.single.yneo;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SaibaSyphonerTest extends CardTestPlayerBase {

    @Test
    public void testReducedCostReturnsSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Saiba Syphoner");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saiba Syphoner");
        addTarget(playerA, "Lightning Bolt");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Saiba Syphoner", 1);
        assertHandCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void testInstantInHandPreventsDiscount() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Saiba Syphoner");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        checkPlayableAbility("no discount", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Saiba Syphoner", false);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Saiba Syphoner", 1);
    }

    @Test
    public void testDiesReplacementDoesNotTriggerDeathAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, "Saiba Syphoner");
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.HAND, playerB, "Doom Blade");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade", "Saiba Syphoner");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Saiba Syphoner", 0);
        assertLibraryCount(playerA, "Saiba Syphoner", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

}
