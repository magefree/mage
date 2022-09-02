package org.mage.test.utils;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CardUtilTest extends CardTestPlayerBase {
    // Whenever you cast or copy an instant or sorcery spell, reveal the top card of your library.
    // If it’s a nonland card, you may cast it by paying {1} rather than paying its mana cost.
    // If it’s a land card, put it onto the battlefield.
    private static final String jadzi = "Jadzi, Oracle of Arcavios";
    // Whenever you discard a nonland card, you may cast it from your graveyard.
    private static final String oskar = "Oskar, Rubbish Reclaimer";
    // MDFC where the back side is a land "Akoum Teeth"
    private static final String akoumWarrior = "Akoum Warrior"; // {5}{R}
    // Discard your hand, then draw a card for each card you’ve discarded this turn.
    private static final String changeOfFortune = "Change of Fortune"; // {3}{R}
    // MDFC where both sides should be playable
    private static final String birgi = "Birgi, God of Storytelling"; // {2}{R}, frontside of Harnfel
    private static final String harnfel = "Harnfel, Horn of Bounty"; // {4}{R}, backside of Birgi

    /**
     * Test that it will for trigger for discarding a MDFC but will only let you cast the nonland side.
     */
    @Test
    public void cantPlayLandSideOfMDFC() {
        addCard(Zone.HAND, playerA, changeOfFortune);
        addCard(Zone.HAND, playerA, akoumWarrior);

        addCard(Zone.BATTLEFIELD, playerA, oskar);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);

        skipInitShuffling();
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, changeOfFortune);
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();
        assertPermanentCount(playerA, akoumWarrior, 1);
    }

    /**
     * Test that when both sides of a MDFC card match, we can choose either side.
     */
    @Test
    public void testFrontSideOfMDFC() {
        addCard(Zone.HAND, playerA, changeOfFortune);
        addCard(Zone.HAND, playerA, birgi, 2);

        addCard(Zone.BATTLEFIELD, playerA, oskar);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 12);

        skipInitShuffling();
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, changeOfFortune);
        setChoice(playerA, "Whenever you discard");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Cast " + birgi);
        setChoice(playerA, "Yes");
        setChoice(playerA, "Cast " + harnfel);

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();
        assertPermanentCount(playerA, birgi, 1);
        assertPermanentCount(playerA, harnfel, 1);
    }

    /**
     * Test that with Jadzi, you are able to play the nonland side of a MDFC, and that the alternative cost works properly.
     */
    @Test
    public void testJadziPlayingLandAndCast() {
        addCard(Zone.BATTLEFIELD, playerA, jadzi);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1+1+1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.LIBRARY, playerA, "Cragcrown Pathway");
        addCard(Zone.LIBRARY, playerA, akoumWarrior);

        skipInitShuffling();
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, "Yes");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();
        assertPermanentCount(playerA, akoumWarrior, 1);
        assertPermanentCount(playerA, "Cragcrown Pathway", 1);
    }
}
