package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class StarvingRevenantTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.StarvingRevenant} <br>
     * Starving Revenant {2}{B}{B} <br>
     * Creature — Spirit Horror <br>
     * When Starving Revenant enters the battlefield, surveil 2. Then for each card you put on top of your library, you draw a card and you lose 3 life. <br>
     * Descend 8 — Whenever you draw a card, if there are eight or more permanent cards in your graveyard, target opponent loses 1 life and you gain 1 life. <br>
     * 4/4
     */
    private static final String revenant = "Starving Revenant";

    @Test
    public void surveil_both_graveyard() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, revenant);
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, revenant);
        addTarget(playerA, "Plains^Island");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerA, 2);
        assertHandCount(playerA, 0);
    }

    @Test
    public void surveil_one_on_top() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, revenant);
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, revenant);
        addTarget(playerA, "Plains");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 3);
        assertGraveyardCount(playerA, 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void surveil_both_on_top() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, revenant);
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, revenant);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        setChoice(playerA, "Plains"); // Plains put on top first

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 3 * 2);
        assertGraveyardCount(playerA, 0);
        assertHandCount(playerA, 2);
    }
}
