package org.mage.test.cards.single.woe;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.players.Player;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Set;

/**
 * @author Susucr
 */
public class AshiokWickedManipulatorTest extends CardTestPlayerBase {

    /**
     * Ashiok, Wicked Manipulator
     * {3}{B}{B}
     * Legendary Planeswalker — Ashiok
     * <p>
     * If you would pay life while your library has at least that many cards in it, exile that many cards from the top of your library instead.
     * +1: Look at the top two cards of your library. Exile one of them and put the other into your hand.
     * −2: Create two 1/1 black Nightmare creature tokens with "At the beginning of combat on your turn, if a card was put into exile this turn, put a +1/+1 counter on this creature."
     * −7: Target player exiles the top X cards of their library, where X is the total mana value of cards you own in exile.
     * <p>
     * Loyalty: 5
     */
    private static final String ashiok = "Ashiok, Wicked Manipulator";

    /**
     * Final Payment
     * {W}{B}
     * Instant
     * <p>
     * As an additional cost to cast this spell, pay 5 life or sacrifice a creature or enchantment.
     * <p>
     * Destroy target creature.
     */
    private static final String finalPayment = "Final Payment";

    // Well sometimes you do need a 2/2 vanilla.
    private static final String lion = "Silvercoat Lion";

    // Move all cards from the player's library into its graveyard (exile is not where we want them here.).
    public void millAllLibrary(Player player) {
        Set<Card> cards = currentGame.getPlayer(player.getId()).getLibrary().getTopCards(currentGame, 100);
        player.moveCards(cards, Zone.GRAVEYARD, null, currentGame);
    }

    @Test
    public void emptyALibrary() {
        setStrictChooseMode(true);
        
        skipInitShuffling();
        millAllLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, lion, 10);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        assertLibraryCount(playerA, 10);
        assertLibraryCount(playerA, lion, 10);
    }

    public void finalPaymentTest(int lionInLibrary, boolean replaced) {
        setStrictChooseMode(true);

        skipInitShuffling();
        millAllLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, lion, lionInLibrary);

        addCard(Zone.BATTLEFIELD, playerA, ashiok);
        addCard(Zone.BATTLEFIELD, playerB, lion);
        addCard(Zone.HAND, playerA, finalPayment);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, finalPayment, lion);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - (replaced ? 0 : 5));
        assertPermanentCount(playerB, lion, 0);
        assertExileCount(playerA, replaced ? 5 : 0);
        assertLibraryCount(playerA, lionInLibrary - (replaced ? 5 : 0));
        assertGraveyardCount(playerB, lion, 1); // Lion
        assertGraveyardCount(playerA, finalPayment, 1);
    }

    @Test
    public void finalPayment_0() {
        finalPaymentTest(0, false);
    }

    @Test
    public void finalPayment_4() {
        finalPaymentTest(4, false);
    }

    @Test
    public void finalPayment_5() {
        finalPaymentTest(5, true);
    }

    @Test
    public void finalPayment_10() {
        finalPaymentTest(10, true);
    }

}