package org.mage.test.cards.single.kld;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetPermanentOrPlayer;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


public class AnimationModuleTest extends CardTestPlayerBase {

    /*
    Animation Module
    {1}
    Artifact

    Whenever one or more +1/+1 counters are put on a permanent you control, you may pay {1}. If you do, create a 1/1 colorless Servo artifact creature token.

    {3}, {T}: Choose a counter on target permanent or player. Give that permanent or player another counter of that kind.
     */
    private static final String animationModule = "Animation Module";

    @Test
    public void testGivePermanentCounter() {
        setStrictChooseMode(true);

        Ability ability  = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new ManaCostsImpl<>("")
        );
        ability.addTarget(new TargetPermanentOrPlayer(1));
        addCustomCardWithAbility("add counter", playerA, ability);

        addCard(Zone.BATTLEFIELD, playerA, animationModule);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "put", animationModule);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setChoice(playerA, true); //pay to create servo

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{3}, {T}: Choose a counter", animationModule);
        setChoice(playerA, true); //pay to create servo

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, animationModule, CounterType.P1P1, 2);
        assertPermanentCount(playerA, "Servo Token", 2);
    }

    @Test
    public void testGivePlayerCounter() {
        setStrictChooseMode(true);

        Ability ability  = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.ENERGY.createInstance()),
                new ManaCostsImpl<>("")
        );
        ability.addTarget(new TargetPermanentOrPlayer(1));
        addCustomCardWithAbility("add counter", playerA, ability);

        addCard(Zone.BATTLEFIELD, playerA, animationModule);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "put", playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{3}, {T}: Choose a counter", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, CounterType.ENERGY, 2);
    }
}