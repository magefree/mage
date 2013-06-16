package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AlphaBrawlTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.HAND, playerA, "Alpha Brawl");
        addCard(Zone.BATTLEFIELD, playerB, "Air Elemental", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Horned Turtle", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alpha Brawl", "Air Elemental");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Air Elemental", 0);
        assertPermanentCount(playerB, "Horned Turtle", 0);
    }


    @Test
    public void testCardWithInfect() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.HAND, playerA, "Alpha Brawl");
        addCard(Zone.BATTLEFIELD, playerB, "Blackcleave Goblin", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Air Elemental", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alpha Brawl", "Blackcleave Goblin");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Blackcleave Goblin", 0);
        assertPermanentCount(playerB, "Air Elemental", 2);
        assertPowerToughness(playerB, "Air Elemental", 2, 2, Filter.ComparisonScope.All);
    }

}
