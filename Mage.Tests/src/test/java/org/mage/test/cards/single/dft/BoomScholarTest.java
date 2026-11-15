package org.mage.test.cards.single.dft;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ExhaustManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class BoomScholarTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BoomScholar Boom Scholar} {1}{R}{G}
     * Creature - Goblin Advisor 3/3
     * Exhaust abilities of other permanents you control cost {2} less to activate.
     * Exhaust - {4}{R}{G}: Creatures and Vehicles you control gain trample until end of turn. Put two +1/+1 counters
     * on this creature. (Activate each exhaust ability only once.)
     */
    private static final String scholar = "Boom Scholar";

    @Test
    public void test_ExhaustAbility_CostReduced() {
        addCard(Zone.BATTLEFIELD, playerA, scholar);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Refueler"); // Exhaust - {1}{G}: Put a +1/+1 counter on this creature.
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // {1}{G} - {2} = {G}
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust &mdash; {1}{G}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Elvish Refueler", CounterType.P1P1, 1);
    }

    @Test
    public void test_ExhaustManaAbility_CostReduced() {
        // no real card yet: Loot, the Pathfinder costs {G} only
        Ability ability = new ExhaustManaAbility(new BasicManaEffect(Mana.RedMana(3)), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        addCustomCardWithAbility("mana exhaust", playerA, ability);
        //
        addCard(Zone.BATTLEFIELD, playerA, scholar);

        // {2} - {2} = free
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust &mdash; {2}");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }

    @Test
    public void test_NonExhaustManaAbility_NotReduced() {
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(Mana.RedMana(3)), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        addCustomCardWithAbility("mana", playerA, ability);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, scholar);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Add {R}{R}{R}");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertTappedCount("Swamp", true, 2);
    }
}
