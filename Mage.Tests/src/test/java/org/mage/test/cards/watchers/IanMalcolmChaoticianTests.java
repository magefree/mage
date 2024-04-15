package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 *
 * @author jimga150
 */
public class IanMalcolmChaoticianTests extends CardTestCommander4Players {

    @Test
    public void testManaCostsandWatcher() {

        skipInitShuffling();

        // Whenever a player draws their second card each turn, that player exiles the top card of their library.
        // During each player's turn, that player may cast a spell from among the cards they don't own exiled with
        // Ian Malcolm, Chaotician, and mana of any type can be spent to cast it.
        addCard(Zone.BATTLEFIELD, playerA, "Ian Malcolm, Chaotician");

        // Flying
        // At the beginning of your draw step, draw an additional card.
        // At the beginning of your end step, discard your hand.
        addCard(Zone.BATTLEFIELD, playerA, "Avaricious Dragon");
        addCard(Zone.BATTLEFIELD, playerB, "Avaricious Dragon");
        addCard(Zone.BATTLEFIELD, playerC, "Avaricious Dragon");
        addCard(Zone.BATTLEFIELD, playerD, "Avaricious Dragon");

        addCard(Zone.LIBRARY, playerA, "Horde of Notions", 3);
        addCard(Zone.LIBRARY, playerB, "Fusion Elemental", 10);
        addCard(Zone.LIBRARY, playerC, "Chromanticore", 10);
        addCard(Zone.LIBRARY, playerD, "Garth One-Eye", 10);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 15);

        // Should be able to cast all cards exiled from other players' decks
        checkPlayableAbility("Can't cast card exiled with this Ian Malcolm", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fusion Elemental", true);
        checkPlayableAbility("Can't cast card exiled with this Ian Malcolm", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Chromanticore", true);
        checkPlayableAbility("Can't cast card exiled with this Ian Malcolm", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Garth One-Eye", true);

        // Should NOT be able to cast own card exiled in same way
        checkPlayableAbility("Able to cast Horde of Notions, but should not be.", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Horde of Notions", false);

        // Cast a card exiled with Ian Malcolm, preventing any future casts from this zone on this turn.
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Fusion Elemental", true);

        checkPlayableAbility("Able to cast Chromanticore, but should not be due to watcher.", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Chromanticore", false);
        checkPlayableAbility("Able to cast Garth One-Eye, but should not be due to watcher.", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Garth One-Eye", false);
        
        setStopAt(5, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

    }

    @Test
    public void testBlink() {

        skipInitShuffling();

        // Whenever a player draws their second card each turn, that player exiles the top card of their library.
        // During each player's turn, that player may cast a spell from among the cards they don't own exiled with
        // Ian Malcolm, Chaotician, and mana of any type can be spent to cast it.
        addCard(Zone.BATTLEFIELD, playerA, "Ian Malcolm, Chaotician");

        // Flying
        // At the beginning of your draw step, draw an additional card.
        // At the beginning of your end step, discard your hand.
        addCard(Zone.BATTLEFIELD, playerA, "Heightened Awareness");
        addCard(Zone.BATTLEFIELD, playerB, "Heightened Awareness");
        addCard(Zone.BATTLEFIELD, playerC, "Heightened Awareness");
        addCard(Zone.BATTLEFIELD, playerD, "Heightened Awareness");

        addCard(Zone.LIBRARY, playerA, "Horde of Notions", 3);
        addCard(Zone.LIBRARY, playerA, "Ephemerate", 1);
        addCard(Zone.LIBRARY, playerB, "Fusion Elemental", 10);
        addCard(Zone.LIBRARY, playerC, "Chromanticore", 10);
        addCard(Zone.LIBRARY, playerD, "Garth One-Eye", 10);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 15);

        // Should be able to cast all cards exiled from other players' decks
        checkPlayableAbility("Can't cast card exiled with this Ian Malcolm", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fusion Elemental", true);
        checkPlayableAbility("Can't cast card exiled with this Ian Malcolm", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Chromanticore", true);
        checkPlayableAbility("Can't cast card exiled with this Ian Malcolm", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Garth One-Eye", true);

        // Should NOT be able to cast own card exiled in same way
        checkPlayableAbility("Able to cast Horde of Notions, but should not be.", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Horde of Notions", false);

        // Blink Ian Malcolm, causing all cards in his current exile zone to become uncastable
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemerate", true);
        addTarget(playerA, "Ian Malcolm, Chaotician");

        // Should no longer be able to cast all cards exiled from other players' decks
        checkPlayableAbility("Can't cast card exiled with this Ian Malcolm", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fusion Elemental", false);
        checkPlayableAbility("Can't cast card exiled with this Ian Malcolm", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Chromanticore", false);
        checkPlayableAbility("Can't cast card exiled with this Ian Malcolm", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Garth One-Eye", false);

        setStopAt(5, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

    }

}
