package org.mage.test.cards.single.war;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.TargetPlayer;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Jmlundeen
 */
public class PriceOfBetrayalTest extends CardTestPlayerBase {

    /*
    Price of Betrayal
    {B}
    Sorcery
    Remove up to five counters from target artifact, creature, planeswalker, or opponent.
    */
    private static final String priceOfBetrayal = "Price of Betrayal";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear

    2/2
    */
    private static final String bearCub = "Bear Cub";

    @Test
    public void testPriceOfBetrayalPermanent() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, priceOfBetrayal);
        addCard(Zone.BATTLEFIELD, playerA, bearCub);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, bearCub, CounterType.P1P1, 3);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, bearCub, CounterType.CHARGE, 1);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, priceOfBetrayal, bearCub);
        setChoiceAmount(playerA, 1); // charge counter
        setChoiceAmount(playerA, 3); // P1P1 counter

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, bearCub, 2, 2);
    }

    @Test
    public void testPriceOfBetrayalPlayer() {
        setStrictChooseMode(true);

        Ability ability  = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.ENERGY.createInstance(5)),
                new ManaCostsImpl<>("")
        );
        ability.addTarget(new TargetPlayer(1));
        addCustomCardWithAbility("add counter", playerA, ability);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, priceOfBetrayal);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target player gets", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, priceOfBetrayal, playerB);
        setChoiceAmount(playerA, 5);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertEquals("Player should have no counters", 0, playerA.getCountersCount(CounterType.ENERGY));
    }
}