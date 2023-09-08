
package mage.cards.r;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class RiseOfTheDarkRealms extends CardImpl {

    public RiseOfTheDarkRealms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{7}{B}{B}");

        // Put all creature cards from all graveyards onto the battlefield under your control.
        this.getSpellAbility().addEffect(new RiseOfTheDarkRealmsEffect());
    }

    private RiseOfTheDarkRealms(final RiseOfTheDarkRealms card) {
        super(card);
    }

    @Override
    public RiseOfTheDarkRealms copy() {
        return new RiseOfTheDarkRealms(this);
    }
}

class RiseOfTheDarkRealmsEffect extends OneShotEffect {

    public RiseOfTheDarkRealmsEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put all creature cards from all graveyards onto the battlefield under your control";
    }

    private RiseOfTheDarkRealmsEffect(final RiseOfTheDarkRealmsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> creatureCards = new LinkedHashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card : player.getGraveyard().getCards(game)) {
                        if (card.isCreature(game)) {
                            creatureCards.add(card);
                        }
                    }
                }
            }
            controller.moveCards(creatureCards, Zone.BATTLEFIELD, source, game, false, false, false, null);
            return true;
        }
        return false;
    }

    @Override
    public RiseOfTheDarkRealmsEffect copy() {
        return new RiseOfTheDarkRealmsEffect(this);
    }

}
