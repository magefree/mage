
package mage.cards.c;

import java.util.UUID;
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
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801 & L_J
 */
public final class CruelFate extends CardImpl {

    public CruelFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Look at the top five cards of target opponent's library. Put one of those cards into that player's graveyard and the rest on top of their library in any order.
        this.getSpellAbility().addEffect(new CruelFateEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    public CruelFate(final CruelFate card) {
        super(card);
    }

    @Override
    public CruelFate copy() {
        return new CruelFate(this);
    }
}

class CruelFateEffect extends OneShotEffect {

    public CruelFateEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top five cards of target opponent's library. Put one of those cards into that player's graveyard and the rest on top of their library in any order";
    }

    public CruelFateEffect(final CruelFateEffect effect) {
        super(effect);
    }

    @Override
    public CruelFateEffect copy() {
        return new CruelFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard != null) {
            Player controller = game.getPlayer(source.getControllerId());
            Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (targetOpponent != null && controller != null) {
                Cards cards = new CardsImpl(targetOpponent.getLibrary().getTopCards(game, 5));
                controller.lookAtCards(source, null, cards, game);

                // card to put into opponent's graveyard
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put into target opponent's graveyard"));
                if (targetOpponent.isInGame()) {
                    if (cards.size() > 1) {
                        controller.choose(Outcome.Detriment, cards, target, game);
                        Card card = cards.get(target.getFirstTarget(), game);
                        if (card != null) {
                            cards.remove(card);
                            controller.moveCards(card, Zone.GRAVEYARD, source, game);
                        }
                    } else if (cards.size() == 1) {
                        Card card = cards.get(cards.iterator().next(), game);
                        controller.moveCards(card, Zone.GRAVEYARD, source, game);
                        card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                        cards.clear();
                    }
                }
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
                return true;
            }
        }
        return false;
    }
}
