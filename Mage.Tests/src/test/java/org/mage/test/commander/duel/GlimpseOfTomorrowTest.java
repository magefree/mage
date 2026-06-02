package org.mage.test.commander.duel;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * Pins the bug: Glimpse of Tomorrow shuffles all permanents you own into your
 * library, but when one of those permanents is your commander the commander
 * replacement effect ("...may put it into the command zone instead") is never
 * offered. The commander just gets shuffled away (and re-deployed by Glimpse).
 */
public class GlimpseOfTomorrowTest extends CardTestCommanderDuelBase {

    @Test
    public void test_CommanderShuffledByGlimpseCanReturnToCommandZone() {
        // Plargg, Dean of Chaos {1}{R} Legendary Creature — Orc Shaman 2/2
        // {4}{R}, {T}: Reveal cards from the top of your library until you reveal a
        //   nonlegendary, nonland card with mana value 3 or less. You may cast that
        //   card without paying its mana cost. Put all revealed cards not cast this
        //   way on the bottom of your library in a random order.
        addCard(Zone.COMMAND, playerA, "Plargg, Dean of Chaos // Augusta, Dean of Order");
        // {1}{R} to cast + {4}{R} to activate = 7 mana
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        // haste, so Plargg can tap for its ability the turn it enters
        addCard(Zone.BATTLEFIELD, playerA, "Fervor");

        // nothing else
        removeAllCardsFromLibrary(playerA);


        // Glimpse of Tomorrow (Suspend 3—{R}{R}, mana value 0) is the ONLY nonland,
        // nonlegendary card in the library, so Plargg's reveal-until ability is
        // guaranteed to find and cast it no matter how the deck is ordered.
        // Sorcery: Shuffle all permanents you own into your library, then reveal that
        //   many cards from the top. Put all non-Aura permanent cards revealed this way
        //   onto the battlefield, then the same for Auras, then the rest on the bottom.
        addCard(Zone.LIBRARY, playerA, "Glimpse of Tomorrow", 1);

        // cast the commander from the command zone (no tax on first cast)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plargg, Dean of Chaos");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // activate the impulse ability -> reveals + casts Glimpse of Tomorrow for free
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{R}, {T}: Reveal");
        setChoice(playerA, "Yes"); // yes, cast the revealed Glimpse of Tomorrow

        // Glimpse resolves and shuffles every permanent you own — including Plargg, the
        // commander — into the library. The commander replacement SHOULD prompt here.
        // It currently does not, so this choice goes unused and the asserts below fail.
        setChoice(playerA, true); // return commander to command zone

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCommandZoneCount(playerA, "Plargg, Dean of Chaos", 1);
        assertPermanentCount(playerA, "Plargg, Dean of Chaos", 0);
    }
}