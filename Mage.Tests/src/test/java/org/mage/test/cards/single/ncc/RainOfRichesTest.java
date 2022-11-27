package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.r.RainOfRiches Rain of Riches}
 * When Rain of Riches enters the battlefield, create two Treasure tokens.
 * The first spell you cast each turn that mana from a Treasure was spent to
 * cast has cascade.
 * (When you cast the spell, exile cards from the top of your library until you
 * exile a nonland card that costs less. You may cast it without paying its mana
 * cost. Put the exiled cards on the bottom of your library in a random order.)
 *
 * @author alexander-novo
 */
public class RainOfRichesTest extends CardTestPlayerBase {

    private static final String rain = "Rain of Riches"; // {3}{R}{R}
    private static final String strike = "Strike It Rich";
    private static final String treasure = "Treasure Token";
    private static final String mountain = "Mountain";
    private static final String goyf = "Tarmogoyf";
    private static final String turtle = "Aegis Turtle";

    /**
     * Check that treasures are created on ETB
     */
    @Test
    public void testETBTreasures() {
        addCard(Zone.BATTLEFIELD, playerA, mountain, 5);
        addCard(Zone.HAND, playerA, rain, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rain);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, treasure, 2);
    }

    /**
     * Test to make sure spells properly get cascade
     */
    @Test
    public void testCascade() {
        addCard(Zone.BATTLEFIELD, playerA, mountain, 4);
        addCard(Zone.BATTLEFIELD, playerA, rain, 1);
        addCard(Zone.HAND, playerA, strike, 4);
        addCard(Zone.HAND, playerA, goyf);
        addCard(Zone.HAND, playerA, goyf);
        addCard(Zone.LIBRARY, playerA, turtle);
        addCard(Zone.LIBRARY, playerA, turtle);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, strike, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, strike, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, strike, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, strike, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goyf, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goyf);

        execute();
        assertPermanentCount(playerA, treasure, 0);
        assertPermanentCount(playerA, turtle, 1);
    }
}