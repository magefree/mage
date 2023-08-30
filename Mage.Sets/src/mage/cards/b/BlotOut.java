package mage.cards.b;

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
import mage.filter.common.FilterControlledCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlotOut extends CardImpl {

    public BlotOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Target opponent exiles a creature or planeswalker they control with the greatest mana value among creatures and planeswalkers they control.
        this.getSpellAbility().addEffect(new BlotOutEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private BlotOut(final BlotOut card) {
        super(card);
    }

    @Override
    public BlotOut copy() {
        return new BlotOut(this);
    }
}

class BlotOutEffect extends OneShotEffect {

    private enum BlotOutPredicate implements ObjectSourcePlayerPredicate<Permanent> {
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

    private static final FilterPermanent filter = new FilterControlledCreatureOrPlaneswalkerPermanent(
            "creature or planeswalker you control with the greatest mana value"
    );

    static {
        filter.add(BlotOutPredicate.instance);
    }

    BlotOutEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent exiles a creature or planeswalker they control with " +
                "the greatest mana value among creatures and planeswalkers they control";
    }

    private BlotOutEffect(final BlotOutEffect effect) {
        super(effect);
    }

    @Override
    public BlotOutEffect copy() {
        return new BlotOutEffect(this);
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
        target.setNotTarget(true);
        opponent.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return opponent.moveCards(permanent, Zone.EXILED, source, game);
    }
}
