package org.mage.test.cards.single.snc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.u.UnluckyWitness Unlucky Witness}
 * When Unlucky Witness dies, exile the top two cards of your library.
 * Until your next end step, you may play one of those cards.
 *
 * @author Alex-Vasile
 */
public class UnluckyWitnessTest extends CardTestPlayerBase {

    private static final String unluckyWitness = "Unlucky Witness";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9413
     *      You currently can't play either of the two exiled cards
     */
    @Test
    public void canPlayExiledCard() {
        String murder = "Murder";
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.BATTLEFIELD, playerA, unluckyWitness);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        addCard(Zone.LIBRARY, playerA, "Exotic Orchard", 2);

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder);

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Exotic Orchard");

        execute();
        assertHandCount(playerA, 0);
        assertExileCount(playerA, 1);
        assertExileCount(playerA, "Exotic Orchard", 1);
        assertGraveyardCount(playerA, unluckyWitness, 1);
    }

    /**
     * Same as the test above, but make sure that spell can also be cast
     */
    @Test
    public void canCastExiledCard() {
        String murder = "Murder";
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.BATTLEFIELD, playerA, unluckyWitness);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        addCard(Zone.LIBRARY, playerA, "Sol Ring", 2);

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sol Ring");

        execute();

        assertGraveyardCount(playerA, unluckyWitness, 1);
        assertHandCount(playerA, 0);
        assertExileCount(playerA, 1);
        assertPermanentCount(playerA, "Sol Ring", 1);
        assertExileCount(playerA, "Sol Ring", 1);
    }
}
