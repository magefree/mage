package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SlugToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToxrillTheCorrosive extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("a creature you don't control with a slime counter on it");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent(SubType.SLUG, "a Slug");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        filter.add(CounterType.SLIME.getPredicate());
    }

    public ToxrillTheCorrosive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SLUG);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // At the beginning of each end step, put a slime counter on each creature you don't control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersAllEffect(
                CounterType.SLIME.createInstance(), StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL
        ), TargetController.ANY, false));

        // Creatures you don't control get -1/-1 for each slime counter on them.
        this.addAbility(new SimpleStaticAbility(new ToxrillTheCorrosiveEffect()));

        // Whenever a creature you don't control with a slime counter on it dies, create a 1/1 black Slug creature token.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new SlugToken()), false, filter
        ));

        // {U}{B}, Sacrifice a Slug: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{U}{B}")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        this.addAbility(ability);
    }

    private ToxrillTheCorrosive(final ToxrillTheCorrosive card) {
        super(card);
    }

    @Override
    public ToxrillTheCorrosive copy() {
        return new ToxrillTheCorrosive(this);
    }
}

class ToxrillTheCorrosiveEffect extends ContinuousEffectImpl {

    ToxrillTheCorrosiveEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.UnboostCreature);
        staticText = "creatures you don't control get -1/-1 for each slime counter on them";
    }

    private ToxrillTheCorrosiveEffect(final ToxrillTheCorrosiveEffect effect) {
        super(effect);
    }

    @Override
    public ToxrillTheCorrosiveEffect copy() {
        return new ToxrillTheCorrosiveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL,
                source.getControllerId(), source, game
        )) {
            int counter = permanent.getCounters(game).getCount(CounterType.SLIME);
            permanent.getPower().increaseBoostedValue(-counter);
            permanent.getToughness().increaseBoostedValue(-counter);
        }
        return true;
    }
}
