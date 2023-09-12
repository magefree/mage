
package mage.cards.o;

import java.util.LinkedHashSet;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class OpenTheVaults extends CardImpl {

    public OpenTheVaults(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");

        // Return all artifact and enchantment cards from all graveyards to the battlefield under their owners' control.
        this.getSpellAbility().addEffect(new OpenTheVaultsEffect());
    }

    private OpenTheVaults(final OpenTheVaults card) {
        super(card);
    }

    @Override
    public OpenTheVaults copy() {
        return new OpenTheVaults(this);
    }
}

class OpenTheVaultsEffect extends OneShotEffect {

    public OpenTheVaultsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return all artifact and enchantment cards from all graveyards to the battlefield under their owners' control";
    }

    private OpenTheVaultsEffect(final OpenTheVaultsEffect effect) {
        super(effect);
    }

    @Override
    public OpenTheVaultsEffect copy() {
        return new OpenTheVaultsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            LinkedHashSet<Card> cardsToReturn = new LinkedHashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                Cards graveyard = player.getGraveyard();
                for (UUID cardId : graveyard) {
                    Card card = game.getCard(cardId);
                    if (card != null
                            && (card.isEnchantment(game) || card.isArtifact(game))) {
                        cardsToReturn.add(card);
                    }
                }
            }
            controller.moveCards(cardsToReturn, Zone.BATTLEFIELD, source, game, false, false, true, null);
            return true;
        }
        return false;
    }
}
