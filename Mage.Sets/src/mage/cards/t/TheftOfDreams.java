
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class TheftOfDreams extends CardImpl {

    public TheftOfDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Draw a card for each tapped creature target opponent controls.
        this.getSpellAbility().addEffect(new TheftOfDreamsEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private TheftOfDreams(final TheftOfDreams card) {
        super(card);
    }

    @Override
    public TheftOfDreams copy() {
        return new TheftOfDreams(this);
    }
}

class TheftOfDreamsEffect extends OneShotEffect {

    public TheftOfDreamsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw a card for each tapped creature target opponent controls";
    }

    public TheftOfDreamsEffect(final TheftOfDreamsEffect effect) {
        super(effect);
    }

    @Override
    public TheftOfDreamsEffect copy() {
        return new TheftOfDreamsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (opponent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(TappedPredicate.TAPPED);
            filter.add(new ControllerIdPredicate(opponent.getId()));
            return new DrawCardSourceControllerEffect(game.getBattlefield().count(filter, source.getControllerId(), source, game)).apply(game, source);
        }
        return false;
    }
}
