package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SuddenDisappearanceTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Constants.Zone.HAND, playerA, "Sudden Disappearance");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Air Elemental", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Horned Turtle", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Altar of the Lost", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Sudden Disappearance", playerB);

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Air Elemental", 0);
        assertPermanentCount(playerB, "Horned Turtle", 0);
        assertPermanentCount(playerB, "Altar of the Lost", 0);
        assertExileCount("Air Elemental", 1);
        assertExileCount("Horned Turtle", 4);
        assertExileCount("Altar of the Lost", 1);
    }


    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Constants.Zone.HAND, playerA, "Sudden Disappearance");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Air Elemental", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Horned Turtle", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Altar of the Lost", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Sudden Disappearance", playerB);

        setStopAt(2, Constants.PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Air Elemental", 1);
        assertPermanentCount(playerB, "Horned Turtle", 4);
        assertPermanentCount(playerB, "Altar of the Lost", 1);
        assertExileCount("Air Elemental", 0);
        assertExileCount("Horned Turtle", 0);
        assertExileCount("Altar of the Lost", 0);
    }

}
