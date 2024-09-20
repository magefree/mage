package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class SignalTheClans extends CardImpl {

    public SignalTheClans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{G}");

        // Search your library for three creature cards and reveal them. If you reveal three cards with different names, choose one of them at random and put that card into your hand. Shuffle the rest into your library.
        this.getSpellAbility().addEffect(new SignalTheClansEffect());
    }

    private SignalTheClans(final SignalTheClans card) {
        super(card);
    }

    @Override
    public SignalTheClans copy() {
        return new SignalTheClans(this);
    }
}

class SignalTheClansEffect extends SearchEffect {

    SignalTheClansEffect() {
        super(new TargetCardInLibrary(3, StaticFilters.FILTER_CARD_CREATURE), Outcome.DrawCard);
        staticText = "Search your library for three creature cards and reveal them. If you reveal three cards with different names, choose one of them at random and put that card into your hand. Shuffle the rest into your library";
    }

    private SignalTheClansEffect(final SignalTheClansEffect effect) {
        super(effect);
    }

    @Override
    public SignalTheClansEffect copy() {
        return new SignalTheClansEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        //Search your library for three creature cards
        controller.searchLibrary(target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        //Reveal them
        controller.revealCards(source, cards, game);
        //If you reveal three cards with different names
        if (CardUtil.differentlyNamedAmongCollection(cards.getCards(game), game) >= 3) {
            //Choose one of them at random and put that card into your hand
            controller.moveCards(cards.getRandom(game), Zone.HAND, source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        // Shuffle the rest into your library
        if (!cards.isEmpty()) {
            controller.shuffleCardsToLibrary(cards, game, source);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }

}
