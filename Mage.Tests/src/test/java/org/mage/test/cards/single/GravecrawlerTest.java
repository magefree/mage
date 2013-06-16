package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class GravecrawlerTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.GRAVEYARD, playerA, "Gravecrawler");
        addCard(Zone.BATTLEFIELD, playerA, "Black Cat");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gravecrawler");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Gravecrawler", 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCard1() {
        addCard(Zone.GRAVEYARD, playerA, "Gravecrawler");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gravecrawler");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Gravecrawler", 0);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void testCopiedCantBlockAbilityWorks() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Cryptoplasm");
        addCard(Zone.BATTLEFIELD, playerA, "Fervor");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        addCard(Zone.BATTLEFIELD, playerB, "Gravecrawler");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        addTarget(playerA, "Gravecrawler");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cryptoplasm");
        attack(3, playerA, "Elite Vanguard");
        block(3, playerB, "Gravecrawler", "Elite Vanguard");

        attack(4, playerB, "Llanowar Elves");
        block(4, playerA, "Gravecrawler", "Llanowar Elves");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gravecrawler", 1);
        assertPermanentCount(playerB, "Gravecrawler", 1);
        assertLife(playerB, 18);
        assertLife(playerA, 19);
    }

    @Test
    public void testCantBlockAbilityAfterChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Fervor");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Gravecrawler");
        addCard(Zone.BATTLEFIELD, playerB, "Walking Corpse");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Gravecrawler");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Gravecrawler");

        attack(3, playerA, "Elite Vanguard");
        block(3, playerB, "Gravecrawler", "Elite Vanguard");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Gravecrawler", 1);
        assertLife(playerB, 18);
    }
}
