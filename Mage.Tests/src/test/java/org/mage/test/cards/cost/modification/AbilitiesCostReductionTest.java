package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 118.7.
 * What a player actually needs to do to pay a cost may be changed or reduced by effects. If the mana component of a
 * cost is reduced to nothing by cost reduction effects, it's considered to be {0}. Paying a cost changed or reduced
 * by an effect counts as paying the original cost.
 * <p>
 * 118.7a
 * Effects that reduce a cost by an amount of generic mana affect only the generic mana component of that cost. They
 * can't affect the colored or colorless mana components of that cost.
 *
 * @author JayDi85
 */
public class AbilitiesCostReductionTest extends CardTestPlayerBase {

    @Test
    public void test_EquipCostLess_AuriokSteelshaper() {
        // Equip costs you pay cost {1} less.
        addCard(Zone.BATTLEFIELD, playerA, "Auriok Steelshaper");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Dagger of the Worthy"); // Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 - 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAttachedTo(playerA, "Dagger of the Worthy", "Balduvian Bears", true);
    }

    @Test
    public void test_OtherAbilityNotLess_AuriokSteelshaper() {
        // Equip costs you pay cost {1} less.
        addCard(Zone.BATTLEFIELD, playerA, "Auriok Steelshaper");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Origin Spellbomb"); // {1}, {T}, Sacrifice this artifact: Create a 1/1 Myr token.

        // no land for {1}
        checkPlayableAbility("no reduce", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}, Sacrifice", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void test_EquipCostLess_BureauHeadmaster() {
        // Equip abilities you activate cost {1} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Bureau Headmaster");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Dagger of the Worthy"); // Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 - 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAttachedTo(playerA, "Dagger of the Worthy", "Balduvian Bears", true);
    }

    @Test
    public void test_EquipCostLess_EowynLadyOfRohan() {
        // Equip abilities you activate cost {1} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Eowyn, Lady of Rohan");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Dagger of the Worthy"); // Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 - 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // stop before the beginning of combat trigger
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAttachedTo(playerA, "Dagger of the Worthy", "Balduvian Bears", true);
    }

    @Test
    public void test_EquipCostLessOnLevel2_FighterClass() {
        // {1}{R}{W}: Level 2
        // Equip abilities you activate cost {2} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Fighter Class");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1 + 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Dagger of the Worthy"); // Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{R}{W}: Level 2");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // level 2 - {2} - {2} = free
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAttachedTo(playerA, "Dagger of the Worthy", "Balduvian Bears", true);
    }

    @Test
    public void test_EquipCostLessDuringYourTurn_NahiriStormOfStone() {
        // During your turn, creatures you control have first strike and equip abilities you activate cost {1} less
        // to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Nahiri, Storm of Stone");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Dagger of the Worthy"); // Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 - 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAttachedTo(playerA, "Dagger of the Worthy", "Balduvian Bears", true);
    }

    @Test
    public void test_EquipOfOtherEquipmentCostLess_BladeholdWarWhip() {
        // Equip abilities you activate of other Equipment cost {1} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Bladehold War-Whip");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Dagger of the Worthy"); // Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 - 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAttachedTo(playerA, "Dagger of the Worthy", "Balduvian Bears", true);
    }

    @Test
    public void test_EquipOfItselfNotLess_BladeholdWarWhip() {
        // Equip abilities you activate of other Equipment cost {1} less to activate.
        // Equip {3}{R}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Bladehold War-Whip");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1 + 3 - 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears"); // bear 2/2

        // own equip is not "other Equipment"
        checkPlayableAbility("no reduce", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}{R}{W}", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void test_NinjutsuCostLess_SilverFurMaster() {
        // Ninja and Rogue creatures you control get +1/+1.
        // Ninjutsu abilities you activate cost {1} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Silver-Fur Master");
        //
        addCard(Zone.HAND, playerA, "Moonblade Shinobi"); // Ninjutsu {2}{U}, Ninja 3/2
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3 - 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        attack(1, playerA, "Grizzly Bears", playerB);
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Ninjutsu");
        setChoice(playerA, "Grizzly Bears"); // return unblocked attacker

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertLife(playerB, 20 - (3 + 1));
    }

    @Test
    public void test_UnlockCostLess_InquisitiveGlimmer() {
        // Unlock costs you pay cost {1} less.
        addCard(Zone.BATTLEFIELD, playerA, "Inquisitive Glimmer");
        //
        // Bottomless Pool {U}, Locker Room {4}{U}
        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1 + 5 - 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool", true);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // no creature to return
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{U}: Unlock the right half.");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTappedCount("Island", true, 1 + 5 - 1);
    }

    @Test
    public void test_MonstrosityCostLess_WeequayBeastmaster() {
        // Monstrosity abilities you activate cost {1} less to activate.
        // Whenever a creature you control becomes monstrous, put an additional +1/+1 counter on that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Weequay Beastmaster");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Nessian Asp"); // {6}{G}: Monstrosity 4.
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7 - 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}{G}: Monstrosity");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Nessian Asp", CounterType.P1P1, 4 + 1);
    }

    @Test
    public void test_MonstrosityCostLess_NerfHerder() {
        // Monstrosity abilities you activate cost {1} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Nerf Herder");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Nessian Asp"); // {6}{G}: Monstrosity 4.
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7 - 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}{G}: Monstrosity");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Nessian Asp", CounterType.P1P1, 4);
    }

    @Test
    public void test_MeditateCostLess_JediTraining() {
        // Meditate abilities you activate cost {1} less to activate.
        // Whenever a Jedi creature you control meditates, scry 1.
        addCard(Zone.BATTLEFIELD, playerA, "Jedi Training");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Ithorian Initiate"); // Jedi, Meditate {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 - 1);
        addCard(Zone.LIBRARY, playerA, "Swamp"); // for scry

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Meditate {1}{G}");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry: keep on top

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Ithorian Initiate", 1);
    }

    @Test
    public void test_PowerUpOfOtherCreaturesCostLess_HulkGammaGoliath() {
        // Power-up abilities of other creatures you control cost {3} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Hulk, Gamma Goliath");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Brave Brawler"); // Power-up - {4}{W}: Put two +1/+1 counters on this creature.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5 - 3);

        // turn 3 - power-up costs less on the turn the creature entered
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Power-up &mdash; {4}{W}");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Brave Brawler", CounterType.P1P1, 2);
    }

    @Test
    public void test_PowerUpOfItselfNotLess_HulkGammaGoliath() {
        // Power-up abilities of other creatures you control cost {3} less to activate.
        // Power-up - {6}{R}{G}: Put five +1/+1 counters on Hulk.
        addCard(Zone.BATTLEFIELD, playerA, "Hulk, Gamma Goliath");
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 8 - 3);

        // own ability is not "other creatures"
        // turn 3 - power-up costs less on the turn the creature entered
        checkPlayableAbility("no reduce", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Power-up &mdash; {6}{R}{G}", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }
}
