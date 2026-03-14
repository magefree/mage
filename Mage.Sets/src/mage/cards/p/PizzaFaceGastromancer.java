package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.RevoltWatcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class PizzaFaceGastromancer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("other target creature or artifact");
    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
        filter.add(AnotherPredicate.instance);
    }

    public PizzaFaceGastromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOOD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Pizza Face enters, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Disappear -- At the beginning of your end step, if a permanent left the battlefield under your
        // control this turn, put three +1/+1 counters on up to one other target artifact or creature.
        // If it isn't a creature, it becomes a 0/0 Mutant creature in addition to its other types.
        Ability triggeredAbility = new BeginningOfEndStepTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(), StaticValue.get(3)))
                .withInterveningIf(RevoltCondition.instance)
                .addHint(RevoltCondition.getHint())
                .setAbilityWord(AbilityWord.DISAPPEAR);
        triggeredAbility.addTarget(new TargetPermanent(0, 1, filter));
        triggeredAbility.addEffect(new PizzaFaceGastromancerEffect());
        this.addAbility(triggeredAbility, new RevoltWatcher());

        // {10}, {T}, Sacrifice Pizza Face: You gain 15 life.
        Ability activatedAbility = new SimpleActivatedAbility(new GainLifeEffect(15), new GenericManaCost(10));
        activatedAbility.addCost(new TapSourceCost());
        activatedAbility.addCost(new SacrificeSourceCost());
        this.addAbility(activatedAbility);
    }

    private PizzaFaceGastromancer(final PizzaFaceGastromancer card) {
        super(card);
    }

    @Override
    public PizzaFaceGastromancer copy() {
        return new PizzaFaceGastromancer(this);
    }
}

class PizzaFaceGastromancerEffect extends OneShotEffect {

    PizzaFaceGastromancerEffect() {
        super(Outcome.BecomeCreature);
        this.staticText = "If it isn't a creature, it becomes a 0/0 Mutant creature in addition to its other types";
    }

    private PizzaFaceGastromancerEffect(final PizzaFaceGastromancerEffect effect) {
        super(effect);
    }

    @Override
    public PizzaFaceGastromancerEffect copy() {
        return new PizzaFaceGastromancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (!permanent.isCreature(game)) {
            ContinuousEffect continuousEffect = new BecomesCreatureTargetEffect(
                new CreatureToken(0, 0, "0/0 Mutant creature", SubType.MUTANT), false, true, Duration.Custom);
            continuousEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(continuousEffect, source);
            return true;
        }
        return false;
    }
}
