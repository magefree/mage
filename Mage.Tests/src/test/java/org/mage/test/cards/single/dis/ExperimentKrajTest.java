package org.mage.test.cards.single.dis;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.Assert.assertEquals;

public class ExperimentKrajTest extends CardTestPlayerBase {

    /*
    Experiment Kraj
    {2}{G}{G}{U}{U}
    Legendary Creature — Ooze Mutant

    Experiment Kraj has all activated abilities of each other creature with a +1/+1 counter on it.

    {T}: Put a +1/+1 counter on target creature.
     */
    private static final String experimentKraj = "Experiment Kraj";
    /*
    Stoneforge Mystic
    {1}{W}
    Creature — Kor Artificer

    When this creature enters, you may search your library for an Equipment card, reveal it, put it into your hand, then shuffle.

    {1}{W}, {T}: You may put an Equipment card from your hand onto the battlefield.
     */
    private static final String stoneforgeMystic = "Stoneforge Mystic";
    /*
    Noble Hierarch
    {G}
    Creature — Human Druid

    Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)

    {T}: Add {G}, {W}, or {U}.
     */
    private static final String nobleHierarch = "Noble Hierarch";

    @Test
    public void testExperimentKraj() {
        setStrictChooseMode(true);

        Ability ability = new SimpleActivatedAbility(
                Zone.ALL,
                new UntapAllControllerEffect(StaticFilters.FILTER_CONTROLLED_A_CREATURE),
                new ManaCostsImpl<>("")
        );
        addCustomCardWithAbility("Untap creatures", playerA, ability);

        addCard(Zone.BATTLEFIELD, playerA, experimentKraj);
        addCard(Zone.BATTLEFIELD, playerA, stoneforgeMystic);
        addCard(Zone.BATTLEFIELD, playerB, nobleHierarch);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put a +1/+1", stoneforgeMystic);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "untap all");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put a +1/+1", nobleHierarch);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertEquals("Kraj should have 5 activated abilities", 5, getPermanent(experimentKraj).getAbilities(currentGame)
                .stream()
                .filter(Ability::isActivatedAbility)
                .count());
    }
}
