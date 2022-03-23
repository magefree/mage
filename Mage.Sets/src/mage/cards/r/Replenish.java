
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class Replenish extends CardImpl {

    public Replenish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}");

        // Return all enchantment cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReplenishEffect());
    }

    private Replenish(final Replenish card) {
        super(card);
    }

    @Override
    public Replenish copy() {
        return new Replenish(this);
    }
}

class ReplenishEffect extends OneShotEffect {

    ReplenishEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return all enchantment cards from your graveyard to the battlefield";
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
            return controller.moveCards(controller.getGraveyard().getCards(new FilterEnchantmentCard(),
                    source.getControllerId(), source, game), Zone.BATTLEFIELD, source, game);
        }
        return false;
    }
}
