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
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        this.getSpellAbility().addEffect(new DistantMemoriesEffect());
    }

    private DistantMemories(final DistantMemories card) {
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
        this.staticText = "Search your library for a card, exile it, then shuffle. "
                + "Any opponent may have you put that card into "
                + "your hand. If no player does, you draw three cards";
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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TargetCardInLibrary target = new TargetCardInLibrary();
        if (controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
                controller.shuffleLibrary(source, game);

                StringBuilder sb = new StringBuilder();
                sb.append("Have ").append(controller.getLogName()).append(" put ").append(card.getName());
                sb.append(" in their hand? If none of their opponents says yes, they will draw three cards.");

                boolean putInHand = false;
                Set<UUID> opponents = game.getOpponents(source.getControllerId());
                for (UUID opponentUuid : opponents) {
                    Player opponent = game.getPlayer(opponentUuid);
                    if (opponent != null) {
                        if (opponent.chooseUse(Outcome.Detriment, sb.toString(), source, game)) {
                            putInHand = true;
                            game.informPlayers(opponent.getLogName() + " decides to put the selected card into the player's hand.");
                        } else {
                            game.informPlayers(opponent.getLogName() + " decides to leave the card in exile.");
                        }
                    }
                }

                if (putInHand) {
                    controller.moveCards(card, Zone.HAND, source, game);
                } else {
                    controller.drawCards(3, source, game);
                }
                return true;
            }
        }
        controller.shuffleLibrary(source, game);
        return false;
    }
}
