package org.mage.test.cards.single._40k;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TheHorusHeresyTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheHorusHeresy The Horus Heresy} {3}{U}{B}{R}
     * Enchantment — Saga
     * I — For each opponent, gain control of up to one target nonlegendary creature that player controls for as long as this Saga remains on the battlefield.
     * II — Draw a card for each creature you control but don’t own.
     * III — Starting with you, each player chooses a creature. Destroy each creature chosen this way.
     */
    private static final String heresy = "The Horus Heresy";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, heresy, 1);

        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, heresy);
        addTarget(playerA, "Goblin Piker");

        checkPermanentCount("after I, A control of Goblin Piker", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Goblin Piker", 1);

        // turn 3
        // draw 1 with II

        // turn 5
        checkPermanentCount("before III, A control of Goblin Piker", 5, PhaseStep.UPKEEP, playerA, "Goblin Piker", 1);

        setChoice(playerA, "Ornithopter");
        setChoice(playerB, "Memnite");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Goblin Piker", 1);
        assertGraveyardCount(playerB, "Ornithopter", 1);
        assertGraveyardCount(playerA, "Memnite", 1);
        assertHandCount(playerA, 3);
    }
}
