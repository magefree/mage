package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */
public class CelebrationTest extends CardTestPlayerBase {
    @Test
    public void testBasic1() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Armory Mice");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Armory Mice");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPowerToughness(playerA, "Armory Mice", 3, 1);
        assertGraveyardCount(playerA, 0);
    }
    @Test
    public void testBasic2() {
        addCard(Zone.HAND, playerA, "Armory Mice");
        addCard(Zone.HAND, playerA, "Black Lotus");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Black Lotus", true);
        setChoice(playerA, "White");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Armory Mice");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPowerToughness(playerA, "Armory Mice", 3, 3);
        assertGraveyardCount(playerA, 1);
    }
    @Test
    public void testContinuousModifier1() {
        addCard(Zone.BATTLEFIELD, playerA, "Ashaya, Soul of the Wild");
        addCard(Zone.HAND, playerA, "Armory Mice");
        addCard(Zone.HAND, playerA, "Black Lotus");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Black Lotus", true);
        setChoice(playerA, "White");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Armory Mice");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPowerToughness(playerA, "Armory Mice", 3, 1);
        assertGraveyardCount(playerA, 1);
    }
    @Test
    @Ignore
    public void testContinuousModifier2() {
        addCard(Zone.BATTLEFIELD, playerA, "Ashaya, Soul of the Wild");
        addCard(Zone.HAND, playerA, "Armory Mice");
        addCard(Zone.HAND, playerA, "Swords to Plowshares");
        addCard(Zone.HAND, playerA, "Black Lotus");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Black Lotus", true);
        setChoice(playerA, "White");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Armory Mice", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swords to Plowshares", "Ashaya, Soul of the Wild", true);


        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPowerToughness(playerA, "Armory Mice", 3, 1);
        assertGraveyardCount(playerA, 2);
    }
}
