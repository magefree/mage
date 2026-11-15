package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Cards put into a graveyard this turn must be found for double-faced cards too: a permanent or a spell of
 * a double-faced card is a side object, but a graveyard keeps the main card
 * <p>
 * 712.8a
 * While a double-faced card is outside the game or in a zone other than the battlefield or stack, it has only the
 * characteristics of its front face.
 *
 * @author JayDi85
 */
public class CardsPutIntoGraveyardWatcherTest extends CardTestPlayerBase {

    @Test
    public void test_FromBattlefield_FaithsReward() {
        // Return to the battlefield all permanent cards in your graveyard that were put there from the battlefield
        // this turn.
        addCard(Zone.HAND, playerA, "Faith's Reward"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Delver of Secrets"); // transforming double-faced, 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Kazandu Mammoth"); // modal double-faced, 3/3

        setChoice(playerA, false); // Delver: no reveal on upkeep

        // kill
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Delver of Secrets");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Kazandu Mammoth");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("dead", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Delver of Secrets", 1);
        checkGraveyardCount("dead", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kazandu Mammoth", 1);

        // return
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Faith's Reward");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Delver of Secrets", 1);
        assertPermanentCount(playerA, "Kazandu Mammoth", 1);
    }

    @Test
    public void test_NotFromBattlefield_Banon() {
        // Pray — Once during each of your turns, you may cast a creature spell from among cards in your graveyard
        // that were put there from anywhere other than the battlefield this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Banon, the Returners' Leader");
        addCard(Zone.HAND, playerA, "Kazandu Mammoth"); // {1}{G}{G}, modal double-faced
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3 + 3);
        addCard(Zone.HAND, playerA, "Cancel"); // {1}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // counter own spell - from the stack to the graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kazandu Mammoth");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cancel", "Kazandu Mammoth", "Kazandu Mammoth");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("countered", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kazandu Mammoth", 1);

        // cast from graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kazandu Mammoth");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Kazandu Mammoth", 1);
    }
}
