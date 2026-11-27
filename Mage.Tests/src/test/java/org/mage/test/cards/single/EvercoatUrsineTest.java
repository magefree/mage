package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Evercoat Ursine {4}{G} 6/5, Trample, Hideaway 3 (twice)
 * Whenever Evercoat Ursine deals combat damage to a player, if there are cards exiled with it,
 * you may play one of them without paying its mana cost.
 * <p>
 * "if there are cards exiled with it" is an intervening-if (CR 603.4).
 */
public class EvercoatUrsineTest extends CardTestPlayerBase {

    private static final String ursine = "Evercoat Ursine";

    @Test
    public void testNothingExiledDoesNotTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, ursine, 1); // never cast, no hideaway ran: nothing exiled with it

        attack(1, playerA, ursine, playerB);
        checkStackSize("no trigger when nothing is exiled with it", 1, PhaseStep.COMBAT_DAMAGE, playerA, 0);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20 - 6);
    }

    @Test
    public void testHideawayExiledCardTriggers() {
        addCard(Zone.HAND, playerA, ursine, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 8);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ursine);

        attack(3, playerA, ursine, playerB);
        checkStackSize("trigger when cards are exiled with it", 3, PhaseStep.COMBAT_DAMAGE, playerA, 1);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 6);
    }
}
