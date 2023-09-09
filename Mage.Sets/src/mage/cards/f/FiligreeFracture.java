
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class FiligreeFracture extends CardImpl {

    public FiligreeFracture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Destroy target artifact or enchantment. If that permanent was blue or black, draw a card.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addEffect(new FiligreeFractureEffect());
    }

    private FiligreeFracture(final FiligreeFracture card) {
        super(card);
    }

    @Override
    public FiligreeFracture copy() {
        return new FiligreeFracture(this);
    }
}

class FiligreeFractureEffect extends OneShotEffect {

    public FiligreeFractureEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact or enchantment. If that permanent was blue or black, draw a card";
    }

    private FiligreeFractureEffect(final FiligreeFractureEffect effect) {
        super(effect);
    }

    @Override
    public FiligreeFractureEffect copy() {
        return new FiligreeFractureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player != null && permanent != null) {
            permanent.destroy(source, game, true);
            game.getState().processAction(game);
            if (permanent.getColor(game).isBlack() || permanent.getColor(game).isBlue()) {
                player.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }
}
