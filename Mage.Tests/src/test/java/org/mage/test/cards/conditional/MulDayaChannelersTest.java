package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class MulDayaChannelersTest extends CardTestPlayerBase {

    @Test
    public void testBoostFromTopCreatureCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Mul Daya Channelers");

        addCard(Zone.LIBRARY, playerA, "Memnite");
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mul Daya Channelers");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Mul Daya Channelers", 1);
        assertPowerToughness(playerA, "Mul Daya Channelers", 5, 5, Filter.ComparisonScope.Any);
    }

    @Test
    public void testNoBoostFromTopNonCreatureCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Mul Daya Channelers");
        addCard(Zone.LIBRARY, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mul Daya Channelers");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Mul Daya Channelers", 1);
        assertPowerToughness(playerA, "Mul Daya Channelers", 2, 2, Filter.ComparisonScope.Any);
    }

    @Test
    public void testBoostLossThroughPhases() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Mul Daya Channelers");

        addCard(Zone.LIBRARY, playerA, "Shock");
        addCard(Zone.LIBRARY, playerA, "Memnite");
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mul Daya Channelers");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Mul Daya Channelers", 1);
        System.out.println(
                currentGame.getPlayer(playerA.getId()).getLibrary().getFromTop(currentGame).getName()
        );
        assertPowerToughness(playerA, "Mul Daya Channelers", 2, 2, Filter.ComparisonScope.Any);
    }

}
