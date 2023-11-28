package org.mage.test.cards.single.ltr;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class WitchKingOfAngmarTest extends CardTestPlayerBase {

    // Flying
    // Whenever one or more creatures deal combat damage to you, each opponent sacrifices a creature that dealt
    // combat damage to you this turn. The Ring tempts you.
    // Discard a card: Witch-king of Angmar gains indestructible until end of turn. Tap it.
    static final String witchKing = "Witch-king of Angmar";

    @Test
    public void test_Sacrifice() {
        String watchwolf = "Watchwolf"; // creature
        String swallower = "Simic Sky Swallower"; // creature with Shroud - should still be a choice

        addCard(Zone.BATTLEFIELD, playerA, witchKing, 1, true);
        addCard(Zone.HAND, playerA, "Swamp", 5);

        addCard(Zone.BATTLEFIELD, playerB, watchwolf, 1);
        addCard(Zone.BATTLEFIELD, playerB, swallower, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Nivix Cyclops", 1);

        // attack by multiple creatures and get 1 trigger
        attack(2, playerB, watchwolf);
        attack(2, playerB, swallower);
        checkStackObject("Sacrifice trigger check", 2, PhaseStep.COMBAT_DAMAGE, playerB, "Whenever one or more creatures deal combat damage to you", 1);
        addTarget(playerB, watchwolf); // choose which creature to sacrifice

        runCode("check ring bear", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertNotNull(playerA.getRingBearer(game));
            Assert.assertEquals(witchKing, playerA.getRingBearer(game).getName());
            Assert.assertNull(playerB.getRingBearer(game));
        });

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 3 - 6);
        assertPermanentCount(playerB, 3 - 1); // b sacrifice 1
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

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertAbility(playerA, witchKing, IndestructibleAbility.getInstance(), false);
    }
}