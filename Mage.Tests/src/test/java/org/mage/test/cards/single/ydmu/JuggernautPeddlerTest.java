package org.mage.test.cards.single.ydmu;

import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.Assert.assertEquals;

public class JuggernautPeddlerTest extends CardTestPlayerBase {

    private void castPeddler(TestPlayer target) {
        addCard(Zone.HAND, playerA, "Juggernaut Peddler");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Juggernaut Peddler");
        addTarget(playerA, target);
    }

    @Test
    public void testControllerChoosesAndTargetOwnsConjuredCard() {
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerB, "Forest");
        castPeddler(playerB);
        setChoice(playerA, "Lightning Bolt");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, "Lightning Bolt", 1);
        assertHandCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerB, "Forest", 1);
        assertHandCount(playerB, "Juggernaut", 1);
        assertHandCount(playerA, "Juggernaut", 0);
        Card conjured = playerB.getHand().getCards(currentGame).stream()
                .filter(card -> card.getName().equals("Juggernaut")).findFirst().get();
        assertEquals(playerB.getId(), conjured.getOwnerId());
        assertAbility(playerA, "Juggernaut Peddler", VigilanceAbility.getInstance(), true);
        assertPowerToughness(playerA, "Juggernaut Peddler", 2, 2);
    }

    @Test
    public void testCanTargetSelf() {
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        castPeddler(playerA);
        setChoice(playerA, "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, "Juggernaut", 1);
    }

    @Test
    public void testMayDeclineChoosingACard() {
        addCard(Zone.HAND, playerB, "Grizzly Bears");
        castPeddler(playerB);
        setChoice(playerA, TestPlayer.CHOICE_SKIP);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerB, "Juggernaut", 0);
        assertExileCount(playerB, 0);
    }

    @Test
    public void testAllLandHandDoesNotConjure() {
        addCard(Zone.HAND, playerB, "Forest", 2);
        castPeddler(playerB);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, "Forest", 2);
        assertHandCount(playerB, "Juggernaut", 0);
        assertExileCount(playerB, 0);
    }

    @Test
    public void testEmptyHandDoesNotConjure() {
        castPeddler(playerB);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, 0);
    }
}
