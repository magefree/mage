package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class MaralenFaeAscendantTest extends CardTestPlayerBase {

    /**
     * Maralen, Fae Ascendant {2}{B}{G}{U}
     * Legendary Creature — Elf Faerie Noble
     * Flying
     * Whenever Maralen or another Elf or Faerie you control enters, exile the top two cards of target opponent's
     * library.
     * Once each turn, you may cast a spell with mana value less than or equal to the number of Elves and Faeries you
     * control from among cards exiled with Maralen this turn without paying its mana cost.
     */
    private static final String maralen = "Maralen, Fae Ascendant";

    @Test
    public void test_CastDoubleFacedCard() {
        addCard(Zone.BATTLEFIELD, playerA, maralen);
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Visionary");
        addCard(Zone.HAND, playerA, "Fyndhorn Elves"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerB, "Kazandu Mammoth"); // {1}{G}{G}, modal double-faced

        // exile, 3 elves
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fyndhorn Elves");
        addTarget(playerA, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // cast for free
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kazandu Mammoth");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Kazandu Mammoth", 1);
    }
}
