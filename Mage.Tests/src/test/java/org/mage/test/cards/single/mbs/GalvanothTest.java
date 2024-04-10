package org.mage.test.cards.single.mbs;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class GalvanothTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.g.Galvanoth Galvanoth} {3}{R}{R}
     * Creature — Beast
     * At the beginning of your upkeep, you may look at the top card of your library. You may cast it without paying its mana cost if it’s an instant or sorcery spell.
     * 3/3
     */
    private static final String galvanoth = "Galvanoth";

    @Test
    public void test_Divination_Cast() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, "Divination");
        addCard(Zone.BATTLEFIELD, playerA, galvanoth);
        
        setChoice(playerA, true); // yes to look
        setChoice(playerA, true); // yes to cast

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Divination", 1);
        assertHandCount(playerA, 2);
    }

    @Test
    public void test_Divination_No_Cast() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, "Divination");
        addCard(Zone.BATTLEFIELD, playerA, galvanoth);

        setChoice(playerA, true); // yes to look
        setChoice(playerA, false); // no to cast

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
    }

    @Test
    public void test_Creature_NotCastable() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerA, galvanoth);

        setChoice(playerA, true); // yes to look

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, galvanoth, 1);
    }
}
