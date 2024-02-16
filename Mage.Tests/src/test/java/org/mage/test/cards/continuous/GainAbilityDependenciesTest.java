package org.mage.test.cards.continuous;

import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GainAbilityDependenciesTest extends CardTestPlayerBase {

    @Test
    public void test_GenerationByFilters() {
        // auto-dependency must find subtype predicate and add dependecy on it
        FilterPermanent filterEmpty = new FilterPermanent("empty");
        FilterPermanent filterSubtype = new FilterPermanent(SubType.HUMAN, "single");
        FilterPermanent filterOr = new FilterPermanent("or");
        filterOr.add(Predicates.or(
                SubType.HUMAN.getPredicate(),
                SubType.ORC.getPredicate()));
        FilterPermanent filterTree = new FilterPermanent("tree");
        filterTree.add(Predicates.and(
                new NamePredicate("test"),
                Predicates.or(
                        SubType.HUMAN.getPredicate(),
                        SubType.ORC.getPredicate())
        ));
        FilterPermanent filterNotTree = new FilterPermanent("tree");
        filterNotTree.add(Predicates.not(
                Predicates.or(
                        SubType.HUMAN.getPredicate(),
                        SubType.ORC.getPredicate())
        ));

        ContinuousEffectImpl effectEmpty = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filterEmpty);
        ContinuousEffectImpl effectSubtype = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filterSubtype);
        ContinuousEffectImpl effectOr = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filterOr);
        ContinuousEffectImpl effectTree = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filterTree);
        ContinuousEffectImpl effectNotTree = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filterNotTree);

        Assert.assertFalse("must haven't depends with empty filter", effectEmpty.getDependedToTypes().contains(DependencyType.AddingCreatureType));
        Assert.assertTrue("must have depend from subtype predicate", effectSubtype.getDependedToTypes().contains(DependencyType.AddingCreatureType));
        Assert.assertTrue("must have depend from or predicate", effectOr.getDependedToTypes().contains(DependencyType.AddingCreatureType));
        Assert.assertTrue("must have depend from tree predicate", effectTree.getDependedToTypes().contains(DependencyType.AddingCreatureType));
        Assert.assertTrue("must have depend from not-tree predicate", effectNotTree.getDependedToTypes().contains(DependencyType.AddingCreatureType));
    }

    /**
     * I had an elephant token equipped with Amorphous Axe attacking and a Tempered Sliver in play. The token did combat
     * damage to a player but it didnt get the +1/+1 counter it hsould be getting.
     * <p>
     * More details: https://github.com/magefree/mage/issues/6147
     */
    @Test
    public void test_SliverGain() {
        // Equipped creature gets +3/+0 and is every creature type.
        // Equip {3}
        addCard(Zone.BATTLEFIELD, playerA, "Amorphous Axe");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        // Create a 3/3 green Elephant creature token.
        addCard(Zone.HAND, playerA, "Elephant Ambush"); // {2}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        //
        // Sliver creatures you control have “Whenever this creature deals combat damage to a player, put a +1/+1 counter on it.”
        addCard(Zone.BATTLEFIELD, playerA, "Tempered Sliver");

        // cast token
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elephant Ambush");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("token must exist", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elephant Token", 1);

        // equip
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", "Elephant Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // attack with +1 token
        attack(3, playerA, "Elephant Token", playerB);
        checkPermanentCounters("must have counter", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Elephant Token", CounterType.P1P1, 1);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }
}
