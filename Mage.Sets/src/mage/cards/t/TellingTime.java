
package mage.cards.t;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
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
public final class TellingTime extends CardImpl {

    public TellingTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Look at the top three cards of your library.
        // Put one of those cards into your hand, one on top of your library, and one on the bottom of your library.
        this.getSpellAbility().addEffect(new TellingTimeEffect());
    }

    private TellingTime(final TellingTime card) {
        super(card);
    }

    @Override
    public TellingTime copy() {
        return new TellingTime(this);
    }
}

class TellingTimeEffect extends OneShotEffect {

    public TellingTimeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top three cards of your library. Put one of those cards into your hand, one on top of your library, and one on the bottom of your library.";
    }

    public TellingTimeEffect(final TellingTimeEffect effect) {
        super(effect);
    }

    @Override
    public TellingTimeEffect copy() {
        return new TellingTimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 3));
        controller.lookAtCards(sourceObject.getIdName(), cards, game);
        if (cards.isEmpty()) {
            return true;
        }
        Card card = pickCard(controller, cards, "card to put in your hand", source, game);
        if (card != null) {
            controller.moveCards(card, Zone.HAND, source, game);
            cards.remove(card);
        }
        if (cards.isEmpty()) {
            return true;
        }

        card = pickCard(controller, cards, "card to put on top of your library", source, game);
        if (card != null) {
            controller.moveCards(card, Zone.LIBRARY, source, game);
            cards.remove(card);
        }
        if (!cards.isEmpty()) {
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }

    private Card pickCard(Player player, Cards cards, String message, Ability source, Game game) {
        if (cards.isEmpty()) {
            return null;
        }
        if (cards.size() == 1) {
            Card card = cards.getRandom(game);
            cards.remove(card);
            return card;
        }

        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard(message));
        if (player.choose(Outcome.Benefit, cards, target, source, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                return card;
            }
        }

        return null;
    }
}
