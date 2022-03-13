
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class SplendidReclamation extends CardImpl {

    public SplendidReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Return all land cards from your graveyard to the battlefield tapped.
        this.getSpellAbility().addEffect(new ReplenishEffect());
    }

    private SplendidReclamation(final SplendidReclamation card) {
        super(card);
    }

    @Override
    public SplendidReclamation copy() {
        return new SplendidReclamation(this);
    }
}

class ReplenishEffect extends OneShotEffect {

    ReplenishEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return all land cards from your graveyard to the battlefield tapped";
    }

    ReplenishEffect(final ReplenishEffect effect) {
        super(effect);
    }

    @Override
    public ReplenishEffect copy() {
        return new ReplenishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.moveCards(controller.getGraveyard().getCards(new FilterLandCard(),
                    source.getControllerId(), source, game), Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        return false;
    }
}
