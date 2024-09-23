package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class EchoingTruth extends CardImpl {

    public EchoingTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent and all other permanents with the same name as that permanent to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandAllNamedPermanentsEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private EchoingTruth(final EchoingTruth card) {
        super(card);
    }

    @Override
    public EchoingTruth copy() {
        return new EchoingTruth(this);
    }
}

class ReturnToHandAllNamedPermanentsEffect extends OneShotEffect {

    ReturnToHandAllNamedPermanentsEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return target nonland permanent and all other permanents " +
                "with the same name as that permanent to their owners' hands";
    }

    private ReturnToHandAllNamedPermanentsEffect(final ReturnToHandAllNamedPermanentsEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandAllNamedPermanentsEffect copy() {
        return new ReturnToHandAllNamedPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || targetPermanent == null) {
            return false;
        }
        Set<Card> set = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(permanent -> permanent.sharesName(targetPermanent, game))
                .collect(Collectors.toSet());
        set.add(targetPermanent);
        return player.moveCards(set, Zone.HAND, source, game);
    }
}
