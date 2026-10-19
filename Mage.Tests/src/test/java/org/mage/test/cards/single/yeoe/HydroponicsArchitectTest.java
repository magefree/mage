package org.mage.test.cards.single.yeoe;

import mage.abilities.mana.BlueManaAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HydroponicsArchitectTest extends CardTestPlayerBase {

    @Test
    public void testLandKeepsOriginalTypesAndDrawsWhenPlayed() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Divination");
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Hydroponics Architect");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Reach Through Mists");
        addCard(Zone.HAND, playerB, "Vesuva");
        attack(1, playerA, "Hydroponics Architect");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Reach Through Mists");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Forest");
        runCode("both land types", 1, PhaseStep.END_TURN, playerA, (info, player, game) -> {
            Permanent land = game.getBattlefield().getAllActivePermanents(player.getId()).stream()
                    .filter(permanent -> permanent.getName().equals("Forest")).findFirst().get();
            org.junit.Assert.assertTrue(land.hasSubtype(SubType.FOREST, game));
            org.junit.Assert.assertTrue(land.hasSubtype(SubType.ISLAND, game));
            org.junit.Assert.assertTrue(land.isBasic(game));
            org.junit.Assert.assertTrue(land.getAbilities().containsClass(BlueManaAbility.class));
        });
        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Vesuva");
        setChoice(playerB, true);
        setChoice(playerB, "Forest");
        runCode("perpetual types are not copied", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, (info, player, game) -> {
            Permanent land = game.getBattlefield().getAllActivePermanents(player.getId()).iterator().next();
            org.junit.Assert.assertTrue(land.hasSubtype(SubType.FOREST, game));
            org.junit.Assert.assertFalse(land.hasSubtype(SubType.ISLAND, game));
            org.junit.Assert.assertFalse(land.getAbilities().containsClass(BlueManaAbility.class));
        });
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Divination", 1);
        assertPermanentCount(playerA, "Forest", 1);
        assertHandCount(playerB, 1);
    }

    @Test
    public void testNoLandDoesNotChangeOtherCards() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Hydroponics Architect");
        attack(1, playerA, "Hydroponics Architect");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLibraryCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, 0);
    }

}
