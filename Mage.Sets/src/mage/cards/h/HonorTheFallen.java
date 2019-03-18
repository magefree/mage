

package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */

public final class HonorTheFallen extends CardImpl {

    public HonorTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Exile all creature cards from all graveyards. You gain 1 life for each card exiled this way.
        this.getSpellAbility().addEffect(new HonorTheFallenEffect());
    }

    public HonorTheFallen(final HonorTheFallen card) {
        super(card);
    }

    @Override
    public HonorTheFallen copy() {
        return new HonorTheFallen(this);
    }

}

class HonorTheFallenEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    public HonorTheFallenEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all creature cards from all graveyards. You gain 1 life for each card exiled this way";
    }

    public HonorTheFallenEffect(final HonorTheFallenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int exiledCards = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card: player.getGraveyard().getCards(game)) {
                        if (filter.match(card, game)) {
                            if (card.moveToExile(null, "", source.getSourceId(), game)) {
                                exiledCards++;
                            }
                        }
                    }
                }
            }
            controller.gainLife(exiledCards, game, source);
            return true;
        }
        return false;
    }

    @Override
    public HonorTheFallenEffect copy() {
        return new HonorTheFallenEffect(this);
    }

}
