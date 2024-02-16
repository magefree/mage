package org.mage.test.cards.split;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class CastSplitCardsWithCostModificationTest extends CardTestPlayerBase {

    private void prepareReduceEffect(String cardNameToReduce, int reduceAmount) {
        FilterCard filter = new FilterCard();
        filter.add(new NamePredicate(cardNameToReduce));
        addCustomCardWithAbility("reduce", playerA, new SimpleStaticAbility(
                new SpellsCostReductionAllEffect(filter, reduceAmount))
        );
    }

    @Test
    public void test_Playable_Left() {
        // cost reduce for easy test
        prepareReduceEffect("Armed", 3);

        // Armed {1}{R} Target creature gets +1/+1 and gains double strike until end of turn.
        // Dangerous {3}{G} All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Armed // Dangerous", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Armed", true);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dangerous", false);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast fused Armed // Dangerous", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Armed", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Armed // Dangerous", 1);
    }

    @Test
    public void test_Playable_Right() {
        // cost reduce for easy test
        prepareReduceEffect("Dangerous", 3);

        // Armed {1}{R} Target creature gets +1/+1 and gains double strike until end of turn.
        // Dangerous {3}{G} All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Armed // Dangerous", 1);
        //addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Armed", false);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dangerous", true);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast fused Armed // Dangerous", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dangerous", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Armed // Dangerous", 1);
    }

    @Test
    public void test_Playable_Fused_Left() {
        // cost reduce for easy test
        prepareReduceEffect("Armed", 4);

        // Armed {1}{R} Target creature gets +1/+1 and gains double strike until end of turn.
        // Dangerous {3}{G} All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Armed // Dangerous", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Armed", true);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dangerous", false);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast fused Armed // Dangerous", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Armed // Dangerous");
        addTarget(playerA, "Balduvian Bears");
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Armed // Dangerous", 1);
    }

    @Test
    public void test_Playable_Fused_Right() {
        // cost reduce for easy test
        prepareReduceEffect("Dangerous", 4);

        // Armed {1}{R} Target creature gets +1/+1 and gains double strike until end of turn.
        // Dangerous {3}{G} All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Armed // Dangerous", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Armed", true); // no reduced, but have basic lands ({G}{R})
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dangerous", true);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast fused Armed // Dangerous", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Armed // Dangerous");
        addTarget(playerA, "Balduvian Bears");
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Armed // Dangerous", 1);
    }

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
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Council of the Absolute");
        setChoice(playerA, "Blastfire Bolt");

        // cast bolt
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blastfire Bolt", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

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
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Armed", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

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

        assertGraveyardCount(playerA, "Armed // Dangerous", 1);
    }

    @Test
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

        assertGraveyardCount(playerA, "Armed // Dangerous", 1);
    }

    @Test
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

        assertGraveyardCount(playerA, "Armed // Dangerous", 1);
    }
}
