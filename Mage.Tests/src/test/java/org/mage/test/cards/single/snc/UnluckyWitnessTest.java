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

    /**
     * Only one of the two cards, even once Unlucky Witness has left the graveyard: the cast is counted against the
     * ability that gave the permission, not against the object the card has become since.
     */
    @Test
    public void testOnlyOneOfTheCards() {
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.BATTLEFIELD, playerA, unluckyWitness);
        addCard(Zone.BATTLEFIELD, playerA, "Tormod's Crypt"); // {T}, Sacrifice: Exile all cards from target player's graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.LIBRARY, playerA, "Sol Ring", 2);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", unluckyWitness, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice", playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sol Ring", true);
        // the first Sol Ring pays for a second, so only the permission stops it
        checkPlayableAbility("one card only", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Sol Ring", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, unluckyWitness, 1);
        assertPermanentCount(playerA, "Sol Ring", 1);
        assertExileCount(playerA, "Sol Ring", 1);
    }
}
