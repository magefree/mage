package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class GravecrawlerTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.GRAVEYARD, playerA, "Gravecrawler");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Black Cat");        
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Gravecrawler");
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Gravecrawler", 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCard1() {
        addCard(Constants.Zone.GRAVEYARD, playerA, "Gravecrawler");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Gravecrawler");
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Gravecrawler", 0);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void testCopiedCantBlockAbilityWorks() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Constants.Zone.HAND, playerA, "Cryptoplasm");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Fervor");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Gravecrawler");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        addTarget(playerA, "Gravecrawler");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Cryptoplasm");
        attack(3, playerA, "Elite Vanguard");
        block(3, playerB, "Gravecrawler", "Elite Vanguard");

        attack(4, playerB, "Llanowar Elves");
        block(4, playerA, "Gravecrawler", "Llanowar Elves");

        setStopAt(4, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gravecrawler", 1);
        assertPermanentCount(playerB, "Gravecrawler", 1);
        assertLife(playerB, 18);
        assertLife(playerA, 19);
    }

    @Test
    public void testCantBlockAbilityAfterChangeZone() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Fervor");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Gravecrawler");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Walking Corpse");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Gravecrawler");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Gravecrawler");

        attack(3, playerA, "Elite Vanguard");
        block(3, playerB, "Gravecrawler", "Elite Vanguard");

        setStopAt(3, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Gravecrawler", 1);
        assertLife(playerB, 18);
    }
}
