
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author nantuko
 */
public final class VisionsOfBeyond extends CardImpl {

    public VisionsOfBeyond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Draw a card. If a graveyard has twenty or more cards in it, draw three cards instead.
        this.getSpellAbility().addEffect(new VisionsOfBeyondEffect());
    }

    private VisionsOfBeyond(final VisionsOfBeyond card) {
        super(card);
    }

    @Override
    public VisionsOfBeyond copy() {
        return new VisionsOfBeyond(this);
    }
}

class VisionsOfBeyondEffect extends OneShotEffect {

    public VisionsOfBeyondEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw a card. If a graveyard has twenty or more cards in it, draw three cards instead";
    }

    private VisionsOfBeyondEffect(final VisionsOfBeyondEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        int count = 1;
        for (UUID playerId: game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.getGraveyard().size() >= 20) {
                    count = 3;
                    break;
                }
            }
        }
        sourcePlayer.drawCards(count, source, game);
        return true;
    }

    @Override
    public VisionsOfBeyondEffect copy() {
        return new VisionsOfBeyondEffect(this);
    }
}
