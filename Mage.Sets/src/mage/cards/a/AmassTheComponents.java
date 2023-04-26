
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public final class AmassTheComponents extends CardImpl {

    public AmassTheComponents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");


        // Draw three cards, then put a card from your hand on the bottom of your library.
        this.getSpellAbility().addEffect(new AmassTheComponentsEffect());

    }

    private AmassTheComponents(final AmassTheComponents card) {
        super(card);
    }

    @Override
    public AmassTheComponents copy() {
        return new AmassTheComponents(this);
    }
}

class AmassTheComponentsEffect extends OneShotEffect {

    public AmassTheComponentsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw three cards, then put a card from your hand on the bottom of your library";
    }

    public AmassTheComponentsEffect(final AmassTheComponentsEffect effect) {
        super(effect);
    }

    @Override
    public AmassTheComponentsEffect copy() {
        return new AmassTheComponentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        player.drawCards(3, source, game);
        if (!player.getHand().isEmpty()) {
            FilterCard filter = new FilterCard("card from your hand to put on the bottom of your library");
            TargetCard target = new TargetCard(Zone.HAND, filter);

            if (player.choose(Outcome.Detriment, player.getHand(), target, source, game)) {
                Card card = player.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    return player.putCardsOnBottomOfLibrary(card, game, source, true);
                }
            }
        }
        return true;
    }
}
