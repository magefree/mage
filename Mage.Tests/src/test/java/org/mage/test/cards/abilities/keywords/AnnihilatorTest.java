package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AnnihilatorTest extends CardTestPlayerBase {

    @Test
    public void testCardsSacrificedToAnnihilatorTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // Annihilator 6 (Whenever this creature attacks, defending player sacrifices two permanents.)
        addCard(Zone.BATTLEFIELD, playerB, "Emrakul, the Aeons Torn");

        attack(2, playerB, "Emrakul, the Aeons Torn");
        setChoice(playerA, "Island");
        setChoice(playerA, "Island");
        setChoice(playerA, "Island");
        setChoice(playerA, "Mountain");
        setChoice(playerA, "Mountain");
        setChoice(playerA, "Mountain");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 5);
        assertPermanentCount(playerA, 1);
    }

    /**
     * I was attacked with an It that Betrays while i had an Academy Rector and
     * with the annihilator trigger on the stack i cast Cauldron Haze targeting
     * academy rector then sacrificed her to the annihilator trigger and chose
     * not to exile her. My persist resolved before the second ability of it
     * that betrays because i was not the active player, the game log shows:
     *
     * 9:18 AM: Ability triggers: Academy Rector [e15] - Persist (When this
     * creature dies, if it had no -1/-1 counters on it, return it to the
     * battlefield under its owner's control with a -1/-1 counter on it.)
     *
     * 9:19 AM: EllNubNub puts Academy Rector [e15] from graveyard onto the
     * Battlefield
     *
     * 9:20 AM: hellmo puts Academy Rector [e15] from battlefield onto the
     * Battlefield
     *
     * The It that Betrays trigger should have fissled, instead it stole her
     * from my battlefield and removed the persist counter.
     */
    @Test
    public void testCardItThatBetrays() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Choose any number of target creatures. Each of those creatures gains persist until end of turn.
        // Persist (When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)
        addCard(Zone.HAND, playerA, "Cauldron Haze", 1); // Instant {1}{W/B}

        // When Academy Rector dies, you may exile it. If you do, search your library for an enchantment card, put that card onto the battlefield, then shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Academy Rector", 1);

        // Annihilator 2 (Whenever this creature attacks, defending player sacrifices two permanents.)
        // Whenever an opponent sacrifices a nontoken permanent, put that card onto the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerB, "It That Betrays");

        attack(2, playerB, "It That Betrays");
        setChoice(playerA, "Academy Rector"); // Annihilator
        setChoice(playerA, "Plains"); // Annihilator
        castSpell(2, PhaseStep.DECLARE_ATTACKERS, playerA, "Cauldron Haze", "Academy Rector", "Annihilator");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Cauldron Haze", 1);
        assertPermanentCount(playerB, "Academy Rector", 0);
        assertPowerToughness(playerA, "Academy Rector", 0, 1);
    }
}
