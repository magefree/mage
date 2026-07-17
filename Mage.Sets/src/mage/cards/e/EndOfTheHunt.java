package mage.cards.e;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.MaxManaValueControlledCreatureOrPlaneswalkerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class EndOfTheHunt extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent(
            "creature or planeswalker they control with the greatest mana value " +
                    "among creatures and planeswalkers they control"
    );

    static {
        filter.add(MaxManaValueControlledCreatureOrPlaneswalkerPredicate.instance);
    }

    public EndOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent exiles a creature or planeswalker they control with the highest mana value among creatures and planeswalkers they control.
        this.getSpellAbility().addEffect(new EndOfTheHuntEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private EndOfTheHunt(final EndOfTheHunt card) {
        super(card);
    }

    @Override
    public EndOfTheHunt copy() {
        return new EndOfTheHunt(this);
    }
}


class EndOfTheHuntEffect extends OneShotEffect {

    private enum EndOfTheHuntPredicate implements ObjectSourcePlayerPredicate<Permanent> {
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
        filter.add(EndOfTheHuntPredicate.instance);
    }

    EndOfTheHuntEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent exiles a creature or planeswalker they control with " +
                "the greatest mana value among creatures and planeswalkers they control";
    }

    private EndOfTheHuntEffect(final EndOfTheHuntEffect effect) {
        super(effect);
    }

    @Override
    public EndOfTheHuntEffect copy() {
        return new EndOfTheHuntEffect(this);
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
        return opponent.moveCards(permanent, Zone.EXILED, source, game);
    }
}
