package org.mage.test.cards.single.ltr;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class WitchKingOfAngmarTest extends CardTestPlayerBase {
    static final String witchKing = "Witch-king of Angmar";
    @Test
    public void testSacrifice() {
        setStrictChooseMode(true);

        String watchwolf = "Watchwolf";
        String swallower = "Simic Sky Swallower"; // Has shroud - should still be a choice

        addCard(Zone.BATTLEFIELD, playerA, witchKing, 1, true);
        addCard(Zone.HAND, playerA, "Swamp", 5);

        addCard(Zone.BATTLEFIELD, playerB, watchwolf, 1);
        addCard(Zone.BATTLEFIELD, playerB, swallower, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Nivix Cyclops", 1);

        attack(2, playerB, watchwolf);
        attack(2, playerB, swallower);
        checkStackObject("Sacrifice trigger check", 2, PhaseStep.COMBAT_DAMAGE, playerB, "Whenever one or more creatures deal combat damage to you", 1);

        // Choose which creature to sacrifice
        addTarget(playerB, watchwolf);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 3 - 6);
        // Player B had to sacrifice one permanent:
        assertPermanentCount(playerB, 2);
    }

    @Test
    public void testIndestructible() {
        addCard(Zone.BATTLEFIELD, playerA, witchKing, 1);
        addCard(Zone.HAND, playerA, "Swamp", 5);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card:");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, witchKing, IndestructibleAbility.getInstance(), true);
        assertTapped(witchKing, true);
        assertHandCount(playerA, 4);

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertAbility(playerA, witchKing, IndestructibleAbility.getInstance(), false);
    }
}