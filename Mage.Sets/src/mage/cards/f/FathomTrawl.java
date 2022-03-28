
package mage.cards.f;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Derpthemeus
 */
public final class FathomTrawl extends CardImpl {

    public FathomTrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Reveal cards from the top of your library until you reveal three nonland cards. Put the nonland cards revealed this way into your hand, then put the rest of the revealed cards on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new FathomTrawlEffect());
    }

    private FathomTrawl(final FathomTrawl card) {
        super(card);
    }

    @Override
    public FathomTrawl copy() {
        return new FathomTrawl(this);
    }

    class FathomTrawlEffect extends OneShotEffect {

        public FathomTrawlEffect() {
            super(Outcome.DrawCard);
            this.staticText = "Reveal cards from the top of your library until you reveal three nonland cards. Put the nonland cards revealed this way into your hand, then put the rest of the revealed cards on the bottom of your library in any order";
        }

        public FathomTrawlEffect(final FathomTrawlEffect effect) {
            super(effect);
        }

        @Override
        public FathomTrawlEffect copy() {
            return new FathomTrawlEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            MageObject sourceObject = game.getObject(source);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null || sourceObject == null) {
                return false;
            }
            Cards cards = new CardsImpl();
            Cards nonlandCards = new CardsImpl();
            Cards landCards = new CardsImpl();
            for (Card card : controller.getLibrary().getCards(game)) {
                if (card != null) {
                    cards.add(card);
                    if (!card.isLand(game)) {
                        nonlandCards.add(card);
                        if (nonlandCards.size() == 3) {
                            break;
                        }
                    } else {
                        landCards.add(card);
                    }
                } else {
                    break;
                }
            }
            controller.revealCards(sourceObject.getName(), cards, game);
            controller.moveCards(nonlandCards, Zone.HAND, source, game);
            controller.putCardsOnBottomOfLibrary(landCards, game, source, true);
            return true;
        }
    }
}
