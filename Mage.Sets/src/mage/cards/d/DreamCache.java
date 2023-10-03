
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Quercitron
 */
public final class DreamCache extends CardImpl {

    public DreamCache(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Draw three cards, then put two cards from your hand both on top of your library or both on the bottom of your library.
        this.getSpellAbility().addEffect(new DreamCacheEffect());
    }

    private DreamCache(final DreamCache card) {
        super(card);
    }

    @Override
    public DreamCache copy() {
        return new DreamCache(this);
    }
}

class DreamCacheEffect extends OneShotEffect {

    public DreamCacheEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw three cards, then put two cards from your hand both on top of your library or both on the bottom of your library.";
    }

    private DreamCacheEffect(final DreamCacheEffect effect) {
        super(effect);
    }

    @Override
    public DreamCacheEffect copy() {
        return new DreamCacheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(3, source, game);
            boolean putOnTop = controller.chooseUse(Outcome.Neutral, "Put cards on top?", source, game);
            TargetCardInHand target = new TargetCardInHand(2, 2, new FilterCard());
            controller.chooseTarget(Outcome.Detriment, target, source, game);
            Cards cardsToLibrary = new CardsImpl(target.getTargets());
            if (!cardsToLibrary.isEmpty()) {
                if (putOnTop) {
                    controller.putCardsOnTopOfLibrary(cardsToLibrary, game, source, false);
                } else {
                    controller.putCardsOnBottomOfLibrary(cardsToLibrary, game, source, false);
                }
            }
            return true;
        }
        return false;
    }

}
