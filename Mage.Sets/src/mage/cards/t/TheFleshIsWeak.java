package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFleshIsWeak extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creatures");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public TheFleshIsWeak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{U}{B}");

        // When The Flesh Is Weak enters the battlefield, put a +1/+1 counter on each creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        )));

        // Creatures you control with +1/+1 counters on them are artifacts in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new TheFleshIsWeakEffect()));

        // Nonartifact creatures get -1/-1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                -1, -1, Duration.WhileOnBattlefield, filter, false
        )));
    }

    private TheFleshIsWeak(final TheFleshIsWeak card) {
        super(card);
    }

    @Override
    public TheFleshIsWeak copy() {
        return new TheFleshIsWeak(this);
    }
}

class TheFleshIsWeakEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    TheFleshIsWeakEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "creatures you control with +1/+1 counters on them are artifacts in addition to their other types";
    }

    private TheFleshIsWeakEffect(final TheFleshIsWeakEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            perm.addCardType(game, CardType.ARTIFACT);
        }
        return true;
    }

    @Override
    public TheFleshIsWeakEffect copy() {
        return new TheFleshIsWeakEffect(this);
    }
}