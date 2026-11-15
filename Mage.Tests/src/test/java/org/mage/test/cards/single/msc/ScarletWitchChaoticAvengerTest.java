package org.mage.test.cards.single.msc;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class ScarletWitchChaoticAvengerTest extends CardTestPlayerBase {

    /**
     * Scarlet Witch, Chaotic Avenger {2}{U}{R}
     * Legendary Creature - Mutant Warlock Hero 3/3
     * Flying
     * Whenever Scarlet Witch deals combat damage to a player, look at the top two cards of your library,
     * then exile them face down. Then you may cast a Hero or noncreature spell from among cards exiled
     * with Scarlet Witch without paying its mana cost.
     */
    private static final String witch = "Scarlet Witch, Chaotic Avenger";

    private static final String bolt = "Lightning Bolt";
    private static final String quicksilver = "Quicksilver, Pietro Maximoff"; // Hero creature
    private static final String bears = "Grizzly Bears"; // non-Hero creature
    private static final String mountain = "Mountain";

    private void assertExiledFaceDown(String cardName) {
        assertExileCount(playerA, cardName, 1);
        for (Card card : currentGame.getExile().getAllCards(currentGame)) {
            if (card.getName().equals(cardName)) {
                Assert.assertTrue(cardName + " must be exiled face down", card.isFaceDown(currentGame));
            }
        }
    }

    @Test
    public void test_CastNoncreatureSpell() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, witch);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bears);
        addCard(Zone.LIBRARY, playerA, bolt);

        attack(1, playerA, witch, playerB);
        setChoice(playerA, true); // cast Lightning Bolt for free
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 3 - 3);
        assertGraveyardCount(playerA, bolt, 1);
        assertExiledFaceDown(bears);
    }

    @Test
    public void test_CastHeroCreatureSpell() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, witch);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bolt);
        addCard(Zone.LIBRARY, playerA, quicksilver);

        attack(1, playerA, witch, playerB);
        setChoice(playerA, quicksilver); // choose among the two castable cards
        setChoice(playerA, true); // cast it for free

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
        assertPermanentCount(playerA, quicksilver, 1);
        assertExiledFaceDown(bolt);
    }

    @Test
    public void test_CantCastNonHeroCreatureOrLand() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, witch);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bears);
        addCard(Zone.LIBRARY, playerA, mountain);

        attack(1, playerA, witch, playerB);
        // no castable cards, so no choices

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
        assertPermanentCount(playerA, bears, 0);
        assertPermanentCount(playerA, mountain, 0);
        assertExiledFaceDown(bears);
        assertExiledFaceDown(mountain);
    }

    @Test
    public void test_ChooseNotToCast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, witch);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bears);
        addCard(Zone.LIBRARY, playerA, bolt);

        attack(1, playerA, witch, playerB);
        setChoice(playerA, false); // don't cast Lightning Bolt

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerA, bolt, 0);
        assertExiledFaceDown(bolt);
        assertExiledFaceDown(bears);
    }

    @Test
    public void test_CastCardExiledByPreviousTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, witch);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, mountain, 3); // turn 3: one drawn, two exiled
        addCard(Zone.LIBRARY, playerA, bears);
        addCard(Zone.LIBRARY, playerA, bolt); // top of library
        skipInitShuffling();

        // first trigger exiles Lightning Bolt and Grizzly Bears
        attack(1, playerA, witch, playerB);
        setChoice(playerA, false); // don't cast Lightning Bolt yet

        // second trigger exiles two Mountains, Lightning Bolt is still exiled with Scarlet Witch
        attack(3, playerA, witch, playerB);
        setChoice(playerA, true); // cast Lightning Bolt for free
        addTarget(playerA, playerB);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 3 - 3 - 3);
        assertGraveyardCount(playerA, bolt, 1);
        assertHandCount(playerA, mountain, 1);
        assertExileCount(playerA, mountain, 2);
        assertExiledFaceDown(bears);
    }
}
