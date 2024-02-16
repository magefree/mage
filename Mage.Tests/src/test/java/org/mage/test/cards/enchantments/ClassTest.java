package org.mage.test.cards.enchantments;

import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ClassTest extends CardTestPlayerBase {

    private static final String wizard = "Wizard Class";
    private static final String merfolk = "Merfolk of the Pearl Trident";
    private static final String druid = "Druid Class";
    private static final String forest = "Forest";
    private static final String wastes = "Wastes";

    private void assertClassLevel(String cardName, int level) {
        Permanent permanent = getPermanent(cardName);
        Assert.assertEquals(
                cardName + " should be level " + level +
                        " but was level " + permanent.getClassLevel(),
                level, permanent.getClassLevel()
        );
    }

    @Test
    public void test_WizardClass__FirstLevel() {
        addCard(Zone.BATTLEFIELD, playerA, wizard);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertClassLevel(wizard, 1);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_WizardClass__SecondLevel() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, wizard);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{U}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertClassLevel(wizard, 2);
        assertHandCount(playerA, 2);
    }

    @Test
    public void test_WizardClass__ThirdLevel() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.BATTLEFIELD, playerA, wizard);
        addCard(Zone.BATTLEFIELD, playerA, merfolk);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{U}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{U}");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertClassLevel(wizard, 3);
        assertHandCount(playerA, 3);
        assertCounterCount(merfolk, CounterType.P1P1, 1);
    }

    @Test
    public void test_DruidClass__FirstLevel() {
        addCard(Zone.BATTLEFIELD, playerA, druid);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertClassLevel(druid, 1);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_DruidClass__SecondLevel() {
        addCard(Zone.BATTLEFIELD, playerA, forest, 3);
        addCard(Zone.BATTLEFIELD, playerA, druid);
        addCard(Zone.HAND, playerA, forest, 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{G}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, forest);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, forest);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertClassLevel(druid, 2);
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, forest, 5);
        assertLife(playerA, 20 + 1 + 1);
    }

    @Test
    public void test_DruidClass__ThirdLevel() {
        addCard(Zone.BATTLEFIELD, playerA, forest, 6);
        addCard(Zone.BATTLEFIELD, playerA, druid);
        addCard(Zone.HAND, playerA, forest);
        addCard(Zone.HAND, playerA, wastes);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{G}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, forest);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, wastes);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{G}");
        addTarget(playerA, wastes);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertClassLevel(druid, 3);
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, forest, 7);
        assertLife(playerA, 20 + 1 + 1);
        assertType(wastes, CardType.CREATURE, true);
        assertPowerToughness(playerA, wastes, 8, 8);
        assertAbility(playerA, wastes, HasteAbility.getInstance(), true);
    }
}
