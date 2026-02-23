package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import static mage.filter.StaticFilters.FILTER_ANOTHER_TARGET_CREATURE;

/**
 *
 * @author spjspj
 */
public final class TheScorpionGod extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature with a -1/-1 counter on it");

    static {
        filter.add(CounterType.M1M1.getPredicate());
    }

    public TheScorpionGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Whenever a creature with a -1/-1 counter on it dies, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false, filter
        ));

        // {1}{B}{R}: Put a -1/-1 counter on another target creature.
        Ability ability = new SimpleActivatedAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance()), new ManaCostsImpl<>("{1}{B}{R}"));
        ability.addTarget(new TargetPermanent(FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);

        // When The Scorpion God dies, return it to its owner's hand at the beginning of the next end step.
        this.addAbility(new DiesSourceTriggeredAbility(new TheScorpionGodEffect()));
    }

    private TheScorpionGod(final TheScorpionGod card) {
        super(card);
    }

    @Override
    public TheScorpionGod copy() {
        return new TheScorpionGod(this);
    }
}

class TheScorpionGodEffect extends OneShotEffect {

    private static final String effectText = "return it to its owner's hand at the beginning of the next end step";

    TheScorpionGodEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    private TheScorpionGodEffect(final TheScorpionGodEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Create delayed triggered ability
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return {this} to its owner's hand");
        effect.setTargetPointer(new FixedTarget(source.getSourceId(), source.getStackMomentSourceZCC()));
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }

    @Override
    public TheScorpionGodEffect copy() {
        return new TheScorpionGodEffect(this);
    }
}
