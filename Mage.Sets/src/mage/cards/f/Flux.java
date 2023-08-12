
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class Flux extends CardImpl {

    public Flux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Each player discards any number of cards, then draws that many cards.
        this.getSpellAbility().addEffect(new FluxEffect());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Flux(final Flux card) {
        super(card);
    }

    @Override
    public Flux copy() {
        return new Flux(this);
    }
}

class FluxEffect extends OneShotEffect {

    FluxEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Each player discards any number of cards, then draws that many cards";
    }

    FluxEffect(final FluxEffect effect) {
        super(effect);
    }

    @Override
    public FluxEffect copy() {
        return new FluxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int numToDiscard = player.getAmount(0, player.getHand().size(), "Discard how many cards?", game);
                player.discard(numToDiscard, false, false, source, game);
                player.drawCards(numToDiscard, source, game);
            }
        }
        return true;
    }
}
