package mage.cards.j;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Cguy7777
 */
public final class JasonBrightGlowingProphet extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(SubType.ZOMBIE.getPredicate(), SubType.MUTANT.getPredicate()));
        filter.add(JasonBrightGlowingProphetPredicate.instance);
    }

    public JasonBrightGlowingProphet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a Zombie or Mutant you control dies, if its power was different from its base power, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(new DrawCardSourceControllerEffect(1), false, filter)
                .setTriggerPhrase("Whenever a Zombie or Mutant you control dies, if its power was different from its base power, "));

        // Come Fly With Me -- {2}, Sacrifice a creature: Put a +1/+1 counter on target creature you control. It gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.addEffect(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn, "it gains flying until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Come Fly With Me"));
    }

    private JasonBrightGlowingProphet(final JasonBrightGlowingProphet card) {
        super(card);
    }

    @Override
    public JasonBrightGlowingProphet copy() {
        return new JasonBrightGlowingProphet(this);
    }
}

enum JasonBrightGlowingProphetPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        MageInt power = input.getPower();
        return power.getValue() != power.getModifiedBaseValue();
    }
}
