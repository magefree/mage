package org.mage.test.multiplayer;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.util.List;

/**
 * Test for simple multiplayer game.
 *
 * @author magenoxx_at_gmail.com
 */
public class SimpleGameTest extends CardTestMultiPlayerBase {

    @Test
    public void testSimple() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerB, "Forest");
        addCard(Zone.BATTLEFIELD, playerC, "Forest");
        addCard(Zone.BATTLEFIELD, playerD, "Forest");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertLife(playerC, 20);
        assertLife(playerD, 20);

        FilterPermanent filterPermanent = new FilterLandPermanent();
        filterPermanent.add(new SubtypePredicate(SubType.FOREST));
        List<Permanent> forestCards = currentGame.getBattlefield().getAllActivePermanents(filterPermanent, currentGame);
        Assert.assertEquals(4, forestCards.size());
    }

}
