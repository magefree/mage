package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class TheRuinousPowersTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheRuinousPowers The Ruinous Powers}
     * Enchantment
     * At the beginning of your upkeep, choose an opponent at random. Exile the top card of that player’s library.
     * Until end of turn, you may play that card and you may spend mana as though it were mana of any color to cast it.
     * When you cast a spell this way, its owner loses life equal to its mana value.
     */
    private static final String powers = "The Ruinous Powers";

    @Test
    public void test_castWithDamage() {

        addCard(Zone.BATTLEFIELD, playerA, powers, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.LIBRARY, playerB, "Squee, the Immortal", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Squee, the Immortal");

        skipInitShuffling();
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Squee, the Immortal", 1);
        assertLife(playerB, currentGame.getStartingLife() - 3);
    }
}
