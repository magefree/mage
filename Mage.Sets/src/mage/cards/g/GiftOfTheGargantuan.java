
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public final class GiftOfTheGargantuan extends CardImpl {

    public GiftOfTheGargantuan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Look at the top four cards of your library. You may reveal a creature card and/or a land card from among them and put the revealed cards into your hand. Put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new GiftOfTheGargantuanEffect());
    }

    public GiftOfTheGargantuan(final GiftOfTheGargantuan card) {
        super(card);
    }

    @Override
    public GiftOfTheGargantuan copy() {
        return new GiftOfTheGargantuan(this);
    }
}

class GiftOfTheGargantuanEffect extends OneShotEffect {

    public GiftOfTheGargantuanEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top four cards of your library. You may reveal a creature card and/or a land card from among them and put the revealed cards into your hand. Put the rest on the bottom of your library in any order";
    }

    public GiftOfTheGargantuanEffect(final GiftOfTheGargantuanEffect effect) {
        super(effect);
    }

    @Override
    public GiftOfTheGargantuanEffect copy() {
        return new GiftOfTheGargantuanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        player.lookAtCards(source, null, cards, game);
        Cards revealedCards = new CardsImpl();
        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCreatureCard("creature card to reveal and put into your hand"));
        if (target.canChoose(source.getControllerId(), game)
                && player.choose(Outcome.DrawCard, cards, target, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                revealedCards.add(card);
            }
        }
        target = new TargetCard(Zone.LIBRARY, new FilterLandCard("land card to reveal and put into your hand"));
        if (target.canChoose(source.getControllerId(), game)
                && player.choose(Outcome.DrawCard, cards, target, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                revealedCards.add(card);
            }
        }
        if (!revealedCards.isEmpty()) {
            player.revealCards(source, revealedCards, game);
        }
        player.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}
