package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests undying
 * 
 * @author BetaSteward
 */
public class FlayerOfTheHateboundTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Flayer of the Hatebound");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Flayer of the Hatebound");

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 15);
        assertPermanentCount(playerA, "Flayer of the Hatebound", 1);
        assertPowerToughness(playerA, "Flayer of the Hatebound", 5, 3, Filter.ComparisonScope.Any);
    }

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Flayer of the Hatebound");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerA, "Reassembling Skeleton", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}:");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Flayer of the Hatebound", 1);
        assertPermanentCount(playerA, "Reassembling Skeleton", 1);
        assertPowerToughness(playerA, "Flayer of the Hatebound", 4, 2, Filter.ComparisonScope.Any);
    }

    @Test
    public void testCard2() {
        addCard(Zone.BATTLEFIELD, playerA, "Flayer of the Hatebound");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerB, "Reassembling Skeleton", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}{B}:");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Flayer of the Hatebound", 1);
        assertPermanentCount(playerB, "Reassembling Skeleton", 1);
        assertPowerToughness(playerA, "Flayer of the Hatebound", 4, 2, Filter.ComparisonScope.Any);
    }

}
