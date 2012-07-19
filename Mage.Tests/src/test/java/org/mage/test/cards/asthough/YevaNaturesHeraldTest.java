package org.mage.test.cards.asthough;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Yeva, Nature's Herald:
 * You may cast green creature cards as though they had flash.
 *
 * @author noxx
 */
public class YevaNaturesHeraldTest extends CardTestPlayerBase {

    @Test
    public void testOnBattlefield() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Yeva, Nature's Herald");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Constants.Zone.HAND, playerA, "Llanowar Elves");

        castSpell(1, Constants.PhaseStep.BEGIN_COMBAT, playerA, "Llanowar Elves");

        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 1);
    }

    @Test
    public void testNonGreen() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Yeva, Nature's Herald");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.HAND, playerA, "Elite Vanguard");

        castSpell(1, Constants.PhaseStep.BEGIN_COMBAT, playerA, "Elite Vanguard");

        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 0);
    }

    @Test
    public void testOtherZones() {
        addCard(Constants.Zone.GRAVEYARD, playerA, "Yeva, Nature's Herald");
        addCard(Constants.Zone.LIBRARY, playerA, "Yeva, Nature's Herald");
        addCard(Constants.Zone.HAND, playerA, "Yeva, Nature's Herald");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Constants.Zone.HAND, playerA, "Llanowar Elves");

        castSpell(1, Constants.PhaseStep.BEGIN_COMBAT, playerA, "Llanowar Elves");

        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 0);
    }

    @Test
    public void testEffectGetRemovedOnExile() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Yeva, Nature's Herald");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.HAND, playerA, "Llanowar Elves");
        addCard(Constants.Zone.HAND, playerA, "Path to Exile");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Path to Exile", "Yeva, Nature's Herald");
        castSpell(1, Constants.PhaseStep.BEGIN_COMBAT, playerA, "Llanowar Elves");

        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 0);
    }

}
