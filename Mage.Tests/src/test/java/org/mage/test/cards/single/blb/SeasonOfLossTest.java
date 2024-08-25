package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class SeasonOfLossTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SeasonOfLoss Season of Loss} {4}{B}
     * Sorcery
     * Choose up to five {P} worth of modes. You may choose the same mode more than once.
     * {P} -- Each player sacrifices a creature.
     * {P}{P} -- Draw a card for each creature you controlled that died this turn.
     * {P}{P}{P} -- Each opponent loses X life, where X is the number of creature cards in your graveyard.
     */
    private static final String seasonOfLoss = "Season of Loss";

    @Test
    public void test_Choose113() {
        // Test that life loss effect sees creatures in graveyard from sacrifice effect

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, seasonOfLoss);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, seasonOfLoss);
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "3");
        setChoice(playerA, "Memnite"); // for 1
        setChoice(playerB, "Memnite");
        setChoice(playerA, "Memnite");
        setChoice(playerB, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Memnite", 3);
        assertGraveyardCount(playerA, "Memnite", 2);

        assertPermanentCount(playerB, "Memnite", 3);
        assertGraveyardCount(playerB, "Memnite", 2);

        assertLife(playerB, currentGame.getStartingLife() - 2);

    }


    @Test
    public void test_Choose2111() {
        // Test that modes 1 and 2 will fire in that order when choices are made in reverse

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, seasonOfLoss);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, seasonOfLoss);
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setChoice(playerA, "Memnite"); // for 1
        setChoice(playerB, "Memnite");
        setChoice(playerA, "Memnite");
        setChoice(playerB, "Memnite");
        setChoice(playerA, "Memnite");
        setChoice(playerB, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Memnite", 2);
        assertGraveyardCount(playerA, "Memnite", 3);

        assertPermanentCount(playerB, "Memnite", 2);
        assertGraveyardCount(playerB, "Memnite", 3);

        assertHandCount(playerA, 3);

    }
}
