
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public final class NaturesResurgence extends CardImpl {

    public NaturesResurgence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}{G}");


        // Each player draws a card for each creature card in their graveyard.
        this.getSpellAbility().addEffect(new NaturesResurgenceEffect());
    }

    private NaturesResurgence(final NaturesResurgence card) {
        super(card);
    }

    @Override
    public NaturesResurgence copy() {
        return new NaturesResurgence(this);
    }
}

class NaturesResurgenceEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard("creature");
    
    public NaturesResurgenceEffect() {
        super(Outcome.DrawCard);
        staticText = "Each player draws a card for each creature card in their graveyard";
    }

    private NaturesResurgenceEffect(final NaturesResurgenceEffect effect) {
        super(effect);
    }
    
    @Override
    public NaturesResurgenceEffect copy() {
        return new NaturesResurgenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (sourcePlayer != null) {
            for (UUID playerId : game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int amount = player.getGraveyard().count(filter, game);
                    player.drawCards(amount, source, game);
                }
            }
        }
        return true;
    }
}
