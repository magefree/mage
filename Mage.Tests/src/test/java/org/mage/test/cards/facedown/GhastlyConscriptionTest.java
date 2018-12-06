package org.mage.test.cards.facedown;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author BetaSteward
 */
public class GhastlyConscriptionTest extends CardTestPlayerBase {

    /**
     * Ghastly Conscription
     * Sorcery, 5BB (7)
     * Exile all creature cards from target player's graveyard in a face-down pile,
     * shuffle that pile, then manifest those cards. (To manifest a card, put it
     * onto the battlefield face down as a 2/2 creature. Turn it face up any time
     * for its mana cost if it's a creature card.)
     */

    // test that cards exiled using Ghastly Conscription return face down
    @Test
    public void testGhastlyConscription() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.HAND, playerA, "Ghastly Conscription");
        addCard(Zone.GRAVEYARD, playerA, "Ashcloud Phoenix");
        addCard(Zone.GRAVEYARD, playerA, "Goblin Roughrider");
        addCard(Zone.GRAVEYARD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ghastly Conscription", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, 2);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 2);

    }

}
