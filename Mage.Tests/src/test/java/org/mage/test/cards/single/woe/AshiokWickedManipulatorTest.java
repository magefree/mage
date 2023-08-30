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

    /**
     * Well sometimes you do need a 2/2 vanilla with mana value 2.
     */
    private static final String lion = "Silvercoat Lion";

    /**
     * Bolas's Citadel
     * {3}{B}{B}{B}
     * Legendary Artifact
     * <p>
     * You may look at the top card of your library any time.
     * <p>
     * You may play lands and cast spells from the top of your library. If you cast a spell this way, pay life equal to its mana value rather than pay its mana cost.
     * <p>
     * {T}, Sacrifice ten nonland permanents: Each opponent loses 10 life.
     */
    private static final String citadel = "Bolas's Citadel";

    /**
     * Lurking Evil
     * {B}{B}{B}
     * Enchantment
     * <p>
     * Pay half your life, rounded up: Lurking Evil becomes a 4/4 Phyrexian Horror creature with flying.
     */
    private static final String lurking = "Lurking Evil";

    /**
     * Arrogant Poet
     * {1}{B}
     * Creature — Human Warlock
     * <p>
     * Whenever Arrogant Poet attacks, you may pay 2 life. If you do, it gains flying until end of turn.
     */
    private static final String poet = "Arrogant Poet";

    @Test
    public void emptyALibrary() {
        setStrictChooseMode(true);

        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, lion, 10);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        assertExileCount(playerA, 0);
        assertLibraryCount(playerA, 10);
        assertLibraryCount(playerA, lion, 10);
    }

    private void finalPaymentTest(int lionInLibrary, boolean replaced) {
        setStrictChooseMode(true);

        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
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

    @Test
    public void ReplacementCitadel() {
        setStrictChooseMode(true);

        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, lion, 10);

        addCard(Zone.BATTLEFIELD, playerA, ashiok);
        addCard(Zone.BATTLEFIELD, playerA, citadel);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lion);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertPermanentCount(playerA, lion, 1);
        assertExileCount(playerA, 2);
        assertLibraryCount(playerA, 10 - 2 - 1); // Lion was cast from there, and 2 cards were exiled as payment.
    }

    @Test
    public void ReplacementLurkingEvil() {
        setStrictChooseMode(true);

        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        int libraryCount = 18;
        int exileCount = 0;
        int lifeCount = 20;
        addCard(Zone.LIBRARY, playerA, lion, libraryCount);

        addCard(Zone.BATTLEFIELD, playerA, ashiok);
        addCard(Zone.BATTLEFIELD, playerA, lurking);

        activateAbility(1, PhaseStep.UPKEEP, playerA, "Pay half your life, rounded up:");
        libraryCount -= 10;
        exileCount += 10;

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, lifeCount);
        assertExileCount(playerA, exileCount);
        assertLibraryCount(playerA, libraryCount);

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Pay half your life, rounded up:");
        lifeCount -= 10;

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, lifeCount);
        assertExileCount(playerA, exileCount);
        assertLibraryCount(playerA, libraryCount);

        activateAbility(1, PhaseStep.END_TURN, playerA, "Pay half your life, rounded up:");
        libraryCount -= 5;
        exileCount += 5;

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, lifeCount);
        assertExileCount(playerA, exileCount);
        assertLibraryCount(playerA, libraryCount);
    }

    @Test
    public void ReplacementPoet() {
        setStrictChooseMode(true);

        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, lion, 10);

        addCard(Zone.BATTLEFIELD, playerA, ashiok);
        addCard(Zone.BATTLEFIELD, playerA, poet);

        attack(1, playerA, poet);
        setChoice(playerA, true);// Yes to pay 2 life, those are replaced by cards exiled.

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertExileCount(playerA, 2);
    }

    @Test
    public void TokensTrigger() {
        setStrictChooseMode(true);

        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        for (int i = 0; i < 10; ++i) {
            // Alternating, so choices are possible on Ashiok's +1
            addCard(Zone.LIBRARY, playerA, lion);
            addCard(Zone.LIBRARY, playerA, poet);
        }

        addCard(Zone.BATTLEFIELD, playerA, ashiok);
        addCard(Zone.BATTLEFIELD, playerA, poet);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Nightmare Token", 1, 1);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        addTarget(playerA, poet);
        // 2 tokens, so stacking trigger.
        setChoice(playerA, "At the beginning of combat on your turn, if a card was put "
                + "into exile this turn, put a +1/+1 counter on this creature.");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Nightmare Token", 2, 2);

        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        addTarget(playerA, lion);
        // 2 tokens, so stacking trigger.
        setChoice(playerA, "At the beginning of combat on your turn, if a card was put "
                + "into exile this turn, put a +1/+1 counter on this creature.");

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Nightmare Token", 3, 3);

        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Nightmare Token", 3, 3);
    }

    @Test
    public void Ultimate() {
        setStrictChooseMode(true);

        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        for (int i = 0; i < 10; ++i) {
            // Alternating, so choices are possible on Ashiok's +1
            addCard(Zone.LIBRARY, playerA, lion);
            addCard(Zone.LIBRARY, playerA, lurking);
        }

        addCard(Zone.BATTLEFIELD, playerA, ashiok);
        addCard(Zone.BATTLEFIELD, playerA, poet);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        addTarget(playerA, lion);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        addTarget(playerA, lurking);

        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        addTarget(playerA, lurking);

        activateAbility(7, PhaseStep.PRECOMBAT_MAIN, playerA, "-7:", playerB);

        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, 2 + 3 + 3);
    }
}