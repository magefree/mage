package mage.cards.b;

import java.util.UUID;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class BreakUnderPressure extends CardImpl {

    public BreakUnderPressure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Target opponent sacrifices a creature or planeswalker with the greatest mana value among creatures and planeswalkers they control. You gain 2 life.
        this.getSpellAbility().addEffect(new BreakUnderPressureEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
    }

    private BreakUnderPressure(final BreakUnderPressure card) {
        super(card);
    }

    @Override
    public BreakUnderPressure copy() {
        return new BreakUnderPressure(this);
    }
}


class BreakUnderPressureEffect extends OneShotEffect {

    private enum BreakUnderPressurePredicate implements ObjectSourcePlayerPredicate<Permanent> {
        instance;

        @Override
        public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
            return input
                .getObject()
                .getManaValue()
                >= game
                .getBattlefield()
                .getActivePermanents(
                    StaticFilters.FILTER_CONTROLLED_PERMANENT_CREATURE_OR_PLANESWALKER,
                    input.getPlayerId(), input.getSource(), game
                )
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
        }
    }

    private static final FilterPermanent filter = new FilterControlledPermanent(
        "creature or planeswalker you control with the greatest mana value"
    );

    static {
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.PLANESWALKER.getPredicate()
        ));
        filter.add(BreakUnderPressurePredicate.instance);
    }

    BreakUnderPressureEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent sacrifices a creature or planeswalker they control with " +
            "the greatest mana value among creatures and planeswalkers they control";
    }

    private BreakUnderPressureEffect(final BreakUnderPressureEffect effect) {
        super(effect);
    }

    @Override
    public BreakUnderPressureEffect copy() {
        return new BreakUnderPressureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent == null || game.getBattlefield().count(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_CREATURE_OR_PLANESWALKER, opponent.getId(), source, game
        ) < 1) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        opponent.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return permanent.sacrifice(source, game);
    }
}
