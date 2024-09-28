package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.t.TergridGodOfFright Tergrid, God of Fright // Tergrid's Lantern}
 * {3}{B}{B}
 * Legendary Creature — God
 * P/T 4/5
 * Menace
 * Whenever an opponent sacrifices a nontoken permanent or discards a permanent card, you may put that card from a graveyard onto the battlefield under your control.
 *
 * {3}{B}
 * Legendary Artifact
 * {T}: Target player loses 3 life unless they sacrifice a nonland permanent or discard a card.
 * {3}{B}: Untap Tergrid’s Lantern.
 *
 * @author jimga150
 */
public class TergridsLanternTest extends CardTestPlayerBase {

    private static final String tergrid = "Tergrid, God of Fright // Tergrid's Lantern";
    private static final String tergridFirstSide = "Tergrid, God of Fright";
    private static final String tergridSecondSide = "Tergrid's Lantern";

    @Test
    public void testLoseLife() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.HAND, playerA, tergrid);
        addCard(Zone.HAND, playerB, "Memnarch");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tergridSecondSide, true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player");
        addTarget(playerA, playerB);
        setChoice(playerB, "No"); // Sac or discard to avoid life loss?

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, currentGame.getStartingLife() - 3);
        assertPermanentCount(playerB, "Memnite", 1);
        assertHandCount(playerB, "Memnarch", 1);
    }

    @Test
    public void testSacCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.HAND, playerA, tergrid);
        addCard(Zone.HAND, playerB, "Memnarch");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tergridSecondSide, true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player");
        addTarget(playerA, playerB);
        setChoice(playerB, "Yes"); // Sac or discard to avoid life loss?
        setChoice(playerB, "Yes"); // Yes - Sacrifice, No - Discard
        setChoice(playerB, "Memnite"); // To sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, currentGame.getStartingLife());
        assertPermanentCount(playerB, "Memnite", 0);
        assertGraveyardCount(playerB, "Memnite", 1);
        assertHandCount(playerB, "Memnarch", 1);
    }

    @Test
    public void testDiscard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.HAND, playerA, tergrid);
        addCard(Zone.HAND, playerB, "Memnarch");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tergridSecondSide, true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player");
        addTarget(playerA, playerB);
        setChoice(playerB, "Yes"); // Sac or discard to avoid life loss?
        setChoice(playerB, "No"); // Yes - Sacrifice, No - Discard
        setChoice(playerB, "Memnarch"); // To discard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, currentGame.getStartingLife());
        assertPermanentCount(playerB, "Memnite", 1);
        assertGraveyardCount(playerB, "Memnarch", 1);
        assertHandCount(playerB, "Memnarch", 0);
    }

}
