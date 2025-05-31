package org.mage.test.cards.single.mom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class JinGitaxiasTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.j.JinGitaxias Jin-Gitaxias // The Great Synthesis}
     * Jin-Gitaxias {3}{U}{U}
     * Legendary Creature — Phyrexian Praetor
     * Ward {2}
     * Whenever you cast a noncreature spell with mana value 3 or greater, draw a card.
     * {3}{U}: Exile Jin-Gitaxias, then return it to the battlefield transformed under its owner’s control. Activate only as a sorcery and only if you have seven or more cards in hand.
     * 5/5
     * The Great Synthesis
     * Enchantment — Saga
     * I — Draw cards equal to the number of cards in your hand. You have no maximum hand size for as long as you control this Saga.
     * II — Return all non-Phyrexian creatures to their owners’ hands.
     * III — You may cast any number of spells from your hand without paying their mana costs. Exile this Saga, then return it to the battlefield (front face up).
     */
    private static final String jin = "Jin-Gitaxias";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.BATTLEFIELD, playerA, jin, 1);
        addCard(Zone.HAND, playerA, "Mountain", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{U}: Exile ");

        checkHandCount("after I", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 14);

        // turn 3
        checkHandCount("after III", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 15);
        checkHandCardCount("after III", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Memnite", 4);

        // turn 5
        checkHandCount("after III", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, 16);

        setStrictChooseMode(true);
        setStopAt(6, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, 7);
    }
}
