package org.mage.test.cards.continuous;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ConditionalCostModificationTest extends CardTestPlayerBase {

    // Dagger of the Worthy {2}
    // Equipped creature gets +2/+0 and has afflict 1.
    // Equip {2}

    @Test
    public void test_NoModification() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Dagger of the Worthy", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Mountain", true, 2);
        assertTappedCount("Mountain", false, 0);
    }

    @Test
    public void test_ModificationNormal() {
        addCustomCardWithAbility("mod", playerA, new SimpleStaticAbility(new AbilitiesCostReductionControllerEffect(EquipAbility.class, "equip")));

        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Dagger of the Worthy", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Mountain", true, 1);
        assertTappedCount("Mountain", false, 1);
    }

    @Test
    public void test_ModificationConditionalActive() {
        addCustomCardWithAbility("mod", playerA, new SimpleStaticAbility(
                new ConditionalCostModificationEffect(
                        new AbilitiesCostReductionControllerEffect(EquipAbility.class, "equip"),
                        MyTurnCondition.instance,
                        ""
                )
        ));

        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Dagger of the Worthy", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Mountain", true, 1);
        assertTappedCount("Mountain", false, 1);
    }

    @Test
    public void test_ModificationConditionalNotActive() {
        addCustomCardWithAbility("mod", playerA, new SimpleStaticAbility(
                new ConditionalCostModificationEffect(
                        new AbilitiesCostReductionControllerEffect(EquipAbility.class, "equip"),
                        NotMyTurnCondition.instance,
                        ""
                )
        ));

        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Dagger of the Worthy", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Mountain", true, 2);
        assertTappedCount("Mountain", false, 0);
    }

    @Test
    public void test_ModificationConditionalNotActiveWithOtherEffect() {
        addCustomCardWithAbility("mod", playerA, new SimpleStaticAbility(
                new ConditionalCostModificationEffect(
                        new AbilitiesCostReductionControllerEffect(EquipAbility.class, "equip"),
                        NotMyTurnCondition.instance,
                        new SpellsCostIncreasingAllEffect(1, new FilterCard(), TargetController.ANY),
                        ""
                )
        ));

        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Dagger of the Worthy", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Balduvian Bears"); // no mod, 2 cost
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB); // +1 for spell, 2 cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Mountain", true, 4);
        assertTappedCount("Mountain", false, 0);
        assertLife(playerB, 20 - 3);
    }

}
