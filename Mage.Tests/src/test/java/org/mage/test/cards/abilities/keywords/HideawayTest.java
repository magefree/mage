package org.mage.test.cards.abilities.keywords;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class HideawayTest extends CardTestPlayerBase {

    /**
     * 702.74. Hideaway 702.74a Hideaway represents a static ability and a
     * triggered ability. “Hideaway” means “This permanent enters the
     * battlefield tapped” and “When this permanent enters the battlefield, look
     * at the top four cards of your library. Exile one of them face down and
     * put the rest on the bottom of your library in any order. The exiled card
     * gains ‘Any player who has controlled the permanent that exiled this card
     * may look at this card in the exile zone.'”
     *
     */
    /**
     * Shelldock Isle Land Hideaway (This land enters the battlefield tapped.
     * When it does, look at the top four cards of your library, exile one face
     * down, then put the rest on the bottom of your library.) {T}: Add {U} to
     * your mana pool. {U}, {T}: You may play the exiled card without paying its
     * mana cost if a library has twenty or fewer cards in it.
     *
     */
    @Test
    public void testHideaway() {
        addCard(Zone.HAND, playerA, "Shelldock Isle");
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion");
        skipInitShuffling();

        this.playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shelldock Isle");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Shelldock Isle", 1);
        assertExileCount(playerA, "Silvercoat Lion", 1);
        for (Card card : currentGame.getExile().getAllCards(currentGame)) {
            Assert.assertTrue("Exiled card is not face down", card.isFaceDown(currentGame));
        }
    }

    /**
     * In commander, an opponent cast Ulamog, the Ceaseless Hunger off of
     * Mosswort Bridge. After it resolved, another opponent exile Ulamog with a
     * Quarantine Field. Ulamog was shown as exile face down, as it had been
     * from the Mosswort Bridge.
     */
    @Test
    public void testMosswortBridge() {
        // Hideaway (This land enters the battlefield tapped. When it does, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library.)
        // {T}: Add {G}.
        // {G}, {T}: You may play the exiled card without paying its mana cost if creatures you control have total power 10 or greater.
        addCard(Zone.HAND, playerA, "Mosswort Bridge");
        // When you cast Ulamog, the Ceaseless Hunger, exile two target permanents.
        // Indestructible
        // Whenever Ulamog attacks, defending player exiles the top twenty cards of their library.
        addCard(Zone.LIBRARY, playerA, "Ulamog, the Ceaseless Hunger");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Dross Crocodile", 2);// 5/1

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mosswort Bridge");
        setChoice(playerA, "Ulamog, the Ceaseless Hunger");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{G},");
        setChoice(playerA, "Ulamog, the Ceaseless Hunger"); // play Ulamog

        addTarget(playerA, "Dross Crocodile^Dross Crocodile");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertExileCount("Dross Crocodile", 2);
        assertPermanentCount(playerA, "Mosswort Bridge", 1);
        assertExileCount(playerA, 2);
        assertExileCount("Ulamog, the Ceaseless Hunger", 0);

        assertPermanentCount(playerA, "Ulamog, the Ceaseless Hunger", 1);

        assertTapped("Mosswort Bridge", true);

        Permanent permanent = getPermanent("Ulamog, the Ceaseless Hunger", playerA);
        Card card = currentGame.getCard(permanent.getId());
        Assert.assertFalse("Previous exiled card may be no longer face down", card.isFaceDown(currentGame));
    }

    @Test
    public void testCannotPlayLandIfPlayedLand() {
        // Hideaway (This land enters the battlefield tapped. When it does, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library.)
        // {tap}: Add {W}.
        // {W}, {T}: You may play the exiled card without paying its mana cost if you attacked with three or more creatures this turn.        
        addCard(Zone.HAND, playerA, "Windbrisk Heights");
        addCard(Zone.HAND, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Ghost Quarter");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Auriok Champion", 3);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Windbrisk Heights");
        setChoice(playerA, "Ghost Quarter");

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");

        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");

        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "{W},");
        setChoice(playerA, "Ghost Quarter"); // play Ghost Quarter

        setStopAt(3, PhaseStep.END_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Ghost Quarter", 0);
        assertTapped("Windbrisk Heights", true);
    }

    @Test
    public void testCannotPlayLandIfNotOwnTurn() {
        // Hideaway (This land enters the battlefield tapped.
        //           When it does, look at the top four cards of your library, exile one face down,
        //           then put the rest on the bottom of your library.)
        // {T}: Add {G}.
        // {G}, {T}: You may play the exiled card without paying its mana cost if creatures you control have total power 10 or greater.        
        addCard(Zone.HAND, playerA, "Mosswort Bridge");
        addCard(Zone.LIBRARY, playerA, "Ghost Quarter");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Dross Crocodile", 2);// 5/1

        setStrictChooseMode(true);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mosswort Bridge");
        setChoice(playerA, "Ghost Quarter");

        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerA, "{G},");
        setChoice(playerA, "Ghost Quarter");

        setStopAt(4, PhaseStep.BEGIN_COMBAT);

        execute();

        assertTapped("Mosswort Bridge", true); // Tapped from activating the ability
        assertPermanentCount(playerA, "Ghost Quarter", 0); // Land couldn't be played since not playerA's turn
    }

    @Test
    public void testCanPlayLandIfNotPlayedLand() {
        addCard(Zone.HAND, playerA, "Windbrisk Heights");
        addCard(Zone.LIBRARY, playerA, "Ghost Quarter");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Auriok Champion", 3);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Windbrisk Heights");
        setChoice(playerA, "Ghost Quarter");

        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");

        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "{W},");
        setChoice(playerA, "Ghost Quarter"); // play Ghost Quarter

        setStopAt(3, PhaseStep.END_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Ghost Quarter", 1);
        assertTapped("Windbrisk Heights", true);
        Assert.assertEquals(1, playerA.getLandsPlayed());
    }

    @Test
    public void testCanPlayMoreLandsIfAble() {
        addCard(Zone.HAND, playerA, "Windbrisk Heights");
        addCard(Zone.LIBRARY, playerA, "Ghost Quarter");
        addCard(Zone.HAND, playerA, "Plains");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Auriok Champion", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Fastbond", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Windbrisk Heights");
        setChoice(playerA, "Ghost Quarter");

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");

        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");

        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "{W},");
        setChoice(playerA, "Ghost Quarter"); // play Ghost Quarter

        setStopAt(3, PhaseStep.END_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Ghost Quarter", 1);
        assertTapped("Windbrisk Heights", true);
        Assert.assertEquals(2, playerA.getLandsPlayed());
    }

    /**
     * Reported bug issue #3310: Shelldock's hideaway requirement is for any
     * library to have 20 or fewer cards, it only allows itself to be activated
     * sometimes when the owner of Shelldock's library has 20 or fewer cards,
     * never the opponents is 20 or fewer
     */
    @Test
    public void testShelldockIsleHideawayConditionOwnLibrary() {

        /*
         Shelldock Isle
         Land Hideaway
         {T}: Add {U}.
         {U}, {T}: You may play the exiled card without paying its mana cost if a library has twenty or fewer cards in it.
         */
        String sIsle = "Shelldock Isle";
        String ulamog = "Ulamog's Crusher"; // {8} 8/8 annihilator 2 attacks each turn if able

        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, sIsle);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, ulamog, 4);
        skipInitShuffling();

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, sIsle);
        setChoice(playerA, ulamog);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}");
        setChoice(playerA, ulamog); // play Ulamog's Crusher

        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertTappedCount("Island", true, 1);
        assertPermanentCount(playerA, sIsle, 1);
        assertLibraryCount(playerA, 2);
        assertPermanentCount(playerA, ulamog, 1);
    }

    /**
     * Reported bug issue #3310: Shelldock's hideaway requirement is for any
     * library to have 20 or fewer cards, it only allows itself to be activated
     * sometimes when the owner of Shelldock's library has 20 or fewer cards,
     * never the opponents is 20 or fewer
     *
     * NOTE: test is currently failing due to bug in code. see issue #3310
     */
    @Test
    public void testShelldockIsleHideawayConditionOpponentsLibrary() {

        /*
         Shelldock Isle
         Land Hideaway
         {T}: Add {U}.
         {U}, {T}: You may play the exiled card without paying its mana cost if a library has twenty or fewer cards in it.
         */
        String sIsle = "Shelldock Isle";
        String ulamog = "Ulamog's Crusher"; // {8} 8/8 annihilator 2 attacks each turn if able
        String bSable = "Bronze Sable"; // {2} 2/1 artifact creature

        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, sIsle);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerA, ulamog, 4);
        addCard(Zone.LIBRARY, playerB, bSable, 4);
        skipInitShuffling();

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, sIsle);
        setChoice(playerA, ulamog);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}");
        setChoice(playerA, ulamog); // play Ulamog's Crusher

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, sIsle, 1);
        assertLibraryCount(playerB, 3); // opponents library less than 20 so should be able to activate shelldock
        assertTappedCount("Island", true, 1);
        assertPermanentCount(playerA, ulamog, 1);
    }

    /**
     * Rule 607.3 - if Hideaway trigger is copied, "the exiled card" refers to each card exiled by Hideaway abilities.
     */
    @Test
    public void testMultipleHideawayTriggers() {
        addCard(Zone.HAND, playerA, "Windbrisk Heights");
        addCard(Zone.LIBRARY, playerA, "Llanowar Elves", 4);
        addCard(Zone.LIBRARY, playerA, "Auriok Champion", 4);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Auriok Glaivemaster", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Elesh Norn, Mother of Machines");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Windbrisk Heights");
        setChoice(playerA, "Hideaway 4"); // Order Hideaway triggers
        setChoice(playerA, "Auriok Champion");
        setChoice(playerA, "Llanowar Elves");

        attack(3, playerA, "Auriok Glaivemaster");
        attack(3, playerA, "Auriok Glaivemaster");
        attack(3, playerA, "Elesh Norn, Mother of Machines");

        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "{W},");
        setChoice(playerA, "Llanowar Elves^Auriok Champion"); // play Llanowar Elves, then Auriok Champion (goes on stack second, resolves first)
        setChoice(playerA, "Whenever"); // Order Auriok Champion's two gain life triggers thanks to Elesh Norn
        setChoice(playerA, true); // Gain life
        setChoice(playerA, true); // Gain life

        setStopAt(3, PhaseStep.END_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertPermanentCount(playerA, "Auriok Champion", 1);
        assertLife(playerA, 22); // Gained a life from Auriok Champion resolving first.
        assertTapped("Windbrisk Heights", true);
    }

    /**
     * Hideaway two lands, attempt to play both exiled lands, only the first one succeeds.
     */
    @Test
    public void testMultipleHideawayTriggersPlayOneLand() {
        addCard(Zone.HAND, playerA, "Windbrisk Heights");
        addCard(Zone.LIBRARY, playerA, "Field of the Dead", 4);
        addCard(Zone.LIBRARY, playerA, "Ghost Quarter", 4);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Auriok Champion", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Elesh Norn, Mother of Machines");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Windbrisk Heights");
        setChoice(playerA, "Hideaway 4"); // Order Hideaway triggers
        setChoice(playerA, "Ghost Quarter");
        setChoice(playerA, "Field of the Dead");

        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");

        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "{W},");
        setChoice(playerA, "Ghost Quarter^Field of the Dead"); // play Ghost Quarter, attempt to play Field of the Dead

        setStopAt(3, PhaseStep.END_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Ghost Quarter", 1);
        assertTapped("Windbrisk Heights", true);
        assertExileCount(playerA, 1);
        Assert.assertEquals(1, playerA.getLandsPlayed());
    }

    @Test
    public void testMultipleHideawayTriggersPlayMultipleLands() {
        addCard(Zone.HAND, playerA, "Windbrisk Heights");
        addCard(Zone.LIBRARY, playerA, "Ghost Quarter", 5);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Auriok Champion", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Elesh Norn, Mother of Machines");
        addCard(Zone.BATTLEFIELD, playerA, "Fastbond", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Windbrisk Heights");
        setChoice(playerA, "Hideaway 4"); // Order Hideaway triggers
        setChoice(playerA, "Ghost Quarter", 2);

        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");

        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "{W},");
        setChoice(playerA, "Ghost Quarter^Ghost Quarter"); // play Ghost Quarter

        setStopAt(3, PhaseStep.END_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Ghost Quarter", 2);
        assertTapped("Windbrisk Heights", true);
        Assert.assertEquals(2, playerA.getLandsPlayed());
    }

    /**
     * Watcher for tomorrow - Watcher of Tomorrow not working when been blinked
     * by any source, like Ephemerate or Soulherder, still working if dies
     * blocking.
     */
    @Test
    public void testBlinkWatcherForTomorrow() {

        /* Hideaway (This permanent enters the battlefield tapped" and
           "When this permanent enters the battlefield, look at the top four cards of
           your library. Exile one of them face down and put the rest on the bottom of
           your library in any order. The exiled card gains 'Any player who has
           controlled the permanent that exiled this card may look at this card in the
           exile zone.'") */
        // When Watcher for Tomorrow leaves the battlefield, put the exiled card into its owner's hand.
        addCard(Zone.HAND, playerA, "Watcher for Tomorrow"); // Creature 2/1 - {1}{U}

        // Exile target creature you control, then return it to the battlefield under its owner's control.
        // Rebound
        addCard(Zone.HAND, playerA, "Ephemerate"); // Instant {W}

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 4);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Watcher for Tomorrow");
        setChoice(playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Ephemerate", "Watcher for Tomorrow");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Watcher for Tomorrow", 1);
        assertExileCount(playerA, "Ephemerate", 1);

        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertExileCount(playerA, 2);
        assertTapped("Watcher for Tomorrow", true);
    }

    /**
     * Possible bug with the Hideaway mechanic or with Watcher for Tomorrow. If
     * it leaves the bf before Hideaway resolves you don't get to exile a card
     * when the former eventually resolves, that shouldn't be the case.
     */
    @Test
    public void testWatcherForTomorrowLeft() {

        // Hideaway
        // When Watcher for Tomorrow leaves the battlefield, put the exiled card into its owner's hand.
        addCard(Zone.HAND, playerA, "Watcher for Tomorrow"); // Creature 2/1 - {1}{U}

        // Exile target creature you control, then return it to the battlefield under its owner's control.
        // Rebound
        addCard(Zone.HAND, playerA, "Ephemerate"); // Instant {W}

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 4);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Watcher for Tomorrow");
        setChoice(playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Ephemerate", "Watcher for Tomorrow");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Watcher for Tomorrow", 1);
        assertExileCount(playerA, "Ephemerate", 1);

        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertExileCount(playerA, 2);
        assertTapped("Watcher for Tomorrow", true);
    }
}
