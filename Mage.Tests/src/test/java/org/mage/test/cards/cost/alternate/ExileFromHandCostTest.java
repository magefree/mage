package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ExileFromHandCostTest extends CardTestPlayerBase {

    @Test
    public void test_TwoCards_OnlyOneOtherInHand_NotPlayable() {
        // You may exile two green cards from your hand rather than pay this spell's mana cost.
        // Allosaurus Rider's power and toughness are each equal to 1 plus the number of lands you control.
        addCard(Zone.HAND, playerA, "Allosaurus Rider"); // {5}{G}{G}
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        checkPlayableAbility("not playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Allosaurus Rider", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Allosaurus Rider", 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_TwoCards_TwoOthersInHand_ExilesThem() {
        // You may exile two green cards from your hand rather than pay this spell's mana cost.
        // Allosaurus Rider's power and toughness are each equal to 1 plus the number of lands you control.
        addCard(Zone.HAND, playerA, "Allosaurus Rider"); // {5}{G}{G}
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Giant Growth");

        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Allosaurus Rider", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Allosaurus Rider");
        setChoice(playerA, "Cast with alternative cost: Exile two green cards from your hand (source: Allosaurus Rider");
        setChoice(playerA, "Grizzly Bears^Giant Growth"); // exile cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Allosaurus Rider", 1);
        assertExileCount(playerA, "Grizzly Bears", 1);
        assertExileCount(playerA, "Giant Growth", 1);
    }
}
