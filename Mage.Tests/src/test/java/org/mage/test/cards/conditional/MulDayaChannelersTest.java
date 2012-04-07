package org.mage.test.cards.conditional;

import junit.framework.Assert;
import mage.Constants;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.filter.Filter;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class MulDayaChannelersTest extends CardTestPlayerBase {

    @Test
    public void testBoostFromTopCreatureCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Constants.Zone.HAND, playerA, "Mul Daya Channelers");

        addCard(Constants.Zone.LIBRARY, playerA, "Memnite");
        skipInitShuffling();

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mul Daya Channelers");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Mul Daya Channelers", 1);
        assertPowerToughness(playerA, "Mul Daya Channelers", 5, 5, Filter.ComparisonScope.Any);
    }

    @Test
    public void testNoBoostFromTopNonCreatureCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Constants.Zone.HAND, playerA, "Mul Daya Channelers");
        addCard(Constants.Zone.LIBRARY, playerA, "Shock");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mul Daya Channelers");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Mul Daya Channelers", 1);
        assertPowerToughness(playerA, "Mul Daya Channelers", 2, 2, Filter.ComparisonScope.Any);
    }

    @Test
    public void testBoostLossThroughPhases() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Constants.Zone.HAND, playerA, "Mul Daya Channelers");

        addCard(Constants.Zone.LIBRARY, playerA, "Shock");
        addCard(Constants.Zone.LIBRARY, playerA, "Memnite");
        skipInitShuffling();

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mul Daya Channelers");

        setStopAt(3, Constants.PhaseStep.PRECOMBAT_MAIN);
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
