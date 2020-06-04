package org.mage.test.cards.split;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class CastSplitCardsWithCostModificationTest extends CardTestPlayerBase {

    @Test
    public void test_CostReduction_Simple() {
        // {2}{W}{U}
        // As Council of the Absolute enters the battlefield, choose a noncreature, nonland card name.
        // Your opponents can’t cast spells with the chosen name.
        // Spells with the chosen name you cast cost {2} less to cast.
        addCard(Zone.HAND, playerA, "Council of the Absolute", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        addCard(Zone.HAND, playerA, "Blastfire Bolt", 1); // {5}{R}, 5 damage
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6 - 2); // -2 for cost reduction
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // cast Council
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Council of the Absolute");
        setChoice(playerA, "Blastfire Bolt");

        // cast bolt
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blastfire Bolt", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Balduvian Bears", 1);
    }

    @Test
    public void test_CostReduction_SplitLeft() {
        // {2}{W}{U}
        // As Council of the Absolute enters the battlefield, choose a noncreature, nonland card name.
        // Your opponents can’t cast spells with the chosen name.
        // Spells with the chosen name you cast cost {2} less to cast.
        addCard(Zone.HAND, playerA, "Council of the Absolute", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        // Armed {1}{R} Target creature gets +1/+1 and gains double strike until end of turn.
        // Dangerous {3}{G} All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Armed // Dangerous", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 - 1); // -1 from cost reduction
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4 - 2); // check not working right cost reduction
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        // cast Council
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Council of the Absolute");
        setChoice(playerA, "Armed");

        // cast Armed
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("after reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Armed", true);
        checkPlayableAbility("after reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dangerous", false);
        checkPlayableAbility("after reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast fused Armed // Dangerous", false);
        showAvaileableAbilities("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Armed", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Armed // Dangerous", 1);
    }

    @Test
    public void test_CostReduction_SplitRight() {
        // {2}{W}{U}
        // As Council of the Absolute enters the battlefield, choose a noncreature, nonland card name.
        // Your opponents can’t cast spells with the chosen name.
        // Spells with the chosen name you cast cost {2} less to cast.
        addCard(Zone.HAND, playerA, "Council of the Absolute", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        // Armed {1}{R} Target creature gets +1/+1 and gains double strike until end of turn.
        // Dangerous {3}{G} All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Armed // Dangerous", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4 - 2); // -2 from cost reduction
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        // cast Council
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Council of the Absolute");
        setChoice(playerA, "Dangerous");

        // cast Dangerous
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("after reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Armed", false);
        checkPlayableAbility("after reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dangerous", true);
        checkPlayableAbility("after reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast fused Armed // Dangerous", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dangerous", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Armed // Dangerous", 1);
    }

    @Test
    @Ignore // TODO: cost modification don't work for fused spells, only for one of the part, see https://github.com/magefree/mage/issues/6603
    public void test_CostReduction_SplitFused_ReduceRight() {
        // {2}{W}{U}
        // As Council of the Absolute enters the battlefield, choose a noncreature, nonland card name.
        // Your opponents can’t cast spells with the chosen name.
        // Spells with the chosen name you cast cost {2} less to cast.
        addCard(Zone.HAND, playerA, "Council of the Absolute", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        // Armed {1}{R} Target creature gets +1/+1 and gains double strike until end of turn.
        // Dangerous {3}{G} All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Armed // Dangerous", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2); // no cost reduction
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4 - 2); // -2 from cost reduction
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        // cast Council
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Council of the Absolute");
        setChoice(playerA, "Dangerous");

        // cast fused
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("after reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Armed", true);
        checkPlayableAbility("after reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dangerous", true);
        checkPlayableAbility("after reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast fused Armed // Dangerous", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Armed // Dangerous");
        addTarget(playerA, "Balduvian Bears");
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Armed // Dangerous", 1);
    }

    @Test
    @Ignore // TODO: cost modification don't work for fused spells, only for one of the part, see https://github.com/magefree/mage/issues/6603
    public void test_CostReduction_SplitFused_ReduceLeft() {
        // {2}{W}{U}
        // As Council of the Absolute enters the battlefield, choose a noncreature, nonland card name.
        // Your opponents can’t cast spells with the chosen name.
        // Spells with the chosen name you cast cost {2} less to cast.
        addCard(Zone.HAND, playerA, "Council of the Absolute", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        // Armed {1}{R} Target creature gets +1/+1 and gains double strike until end of turn.
        // Dangerous {3}{G} All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Armed // Dangerous", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 - 1); // -1 from cost reduction
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4 - 1); // -1 from cost reduction ON FUSED
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        // cast Council
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Council of the Absolute");
        setChoice(playerA, "Armed");

        // cast fused
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("after reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Armed", true);
        checkPlayableAbility("after reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dangerous", true);
        checkPlayableAbility("after reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast fused Armed // Dangerous", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Armed // Dangerous");
        addTarget(playerA, "Balduvian Bears");
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Armed // Dangerous", 1);
    }
}
