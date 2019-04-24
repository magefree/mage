
package mage.cards.d;

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
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class DistantMemories extends CardImpl {

    public DistantMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");


        this.getSpellAbility().addEffect(new DistantMemoriesEffect());
    }

    public DistantMemories(final DistantMemories card) {
        super(card);
    }

    @Override
    public DistantMemories copy() {
        return new DistantMemories(this);
    }
}

class DistantMemoriesEffect extends OneShotEffect {

    public DistantMemoriesEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for a card, exile it, then shuffle your library. Any opponent may have you put that card into your hand. If no player does, you draw three cards";
    }

    public DistantMemoriesEffect(final DistantMemoriesEffect effect) {
        super(effect);
    }

    @Override
    public DistantMemoriesEffect copy() {
        return new DistantMemoriesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        TargetCardInLibrary target = new TargetCardInLibrary();
        if (player.searchLibrary(target, source, game)) {
            Card card = player.getLibrary().remove(target.getFirstTarget(), game);
            if (card != null) {
                card.moveToZone(Zone.EXILED, source.getSourceId(), game, false);
                player.shuffleLibrary(source, game);

                StringBuilder sb = new StringBuilder();
                sb.append("Have ").append(player.getLogName()).append(" put ").append(card.getName());
                sb.append(" in his hand? If none of his opponents says yes, he'll draw three cards.");

                boolean putInHand = false;
                Set<UUID> opponents = game.getOpponents(source.getControllerId());
                for (UUID opponentUuid : opponents) {
                    Player opponent = game.getPlayer(opponentUuid);
                    if (opponent != null && !putInHand && opponent.chooseUse(Outcome.Neutral, sb.toString(), source, game)) {
                        putInHand = true;
                    }
                }

                if (putInHand) {
                    game.getExile().getPermanentExile().remove(card);
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                } else {
                    player.drawCards(3, game);
                }
                return true;
            }
        }
        player.shuffleLibrary(source, game);
        return false;
    }
}
