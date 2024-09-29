package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class WhiteGloveGourmandTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.w.WhiteGloveGourmand White Glove Gourmand}  {2}{W}{B}
     * Creature — Human Noble
     * When White Glove Gourmand enters the battlefield, create two 1/1 white Human Soldier creature tokens.
     * At the beginning of your end step, if another Human died under your control this turn, create a Food token.
     * 2/2
     */
    private static final String gourmand = "White Glove Gourmand";

    @Test
    public void test_Trigger_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, gourmand);
        addCard(Zone.HAND, playerA, "Shrivel"); // All creatures get -1/-1 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gourmand, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shrivel");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Food Token", 1);
    }

    @Test
    public void test_Trigger_Changeling() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Avian Changeling");
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 6);
        addCard(Zone.HAND, playerA, gourmand);
        addCard(Zone.HAND, playerA, "Doom Blade");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", "Avian Changeling", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gourmand);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Food Token", 1);
    }

    @Test
    public void test_NoTrigger_OpponentHuman_Die() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Avian Changeling");
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 6);
        addCard(Zone.HAND, playerA, gourmand);
        addCard(Zone.HAND, playerA, "Doom Blade");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", "Avian Changeling", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gourmand);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Food Token", 0);
    }

    @Test
    public void test_NoTrigger_NonHuman() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 6);
        addCard(Zone.HAND, playerA, gourmand);
        addCard(Zone.HAND, playerA, "Doom Blade");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", "Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gourmand);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Food Token", 0);
    }

    @Test
    public void test_SelfDie_And_Reanimate() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, gourmand);
        addCard(Zone.HAND, playerA, "Go for the Throat");
        addCard(Zone.HAND, playerA, "Reanimate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Go for the Throat", gourmand, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", gourmand);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Food Token", 1);
    }
}
