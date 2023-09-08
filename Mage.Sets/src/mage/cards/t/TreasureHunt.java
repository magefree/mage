
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class TreasureHunt extends CardImpl {

    public TreasureHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Reveal cards from the top of your library until you reveal a nonland card, then put all cards revealed this way into your hand.
        this.getSpellAbility().addEffect(new TreasureHuntEffect());
    }

    private TreasureHunt(final TreasureHunt card) {
        super(card);
    }

    @Override
    public TreasureHunt copy() {
        return new TreasureHunt(this);
    }
}

class TreasureHuntEffect extends OneShotEffect {

    public TreasureHuntEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal cards from the top of your library until you reveal a nonland card, then put all cards revealed this way into your hand";
    }

    private TreasureHuntEffect(final TreasureHuntEffect effect) {
        super(effect);
    }

    @Override
    public TreasureHuntEffect copy() {
        return new TreasureHuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.getLibrary().hasCards()) {
            CardsImpl cards = new CardsImpl();
            for (Card card : controller.getLibrary().getCards(game)) {
                cards.add(card);
                if (!card.isLand(game)) {
                    break;
                }
            }
            controller.revealCards(source, cards, game);
            controller.moveCards(cards, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
