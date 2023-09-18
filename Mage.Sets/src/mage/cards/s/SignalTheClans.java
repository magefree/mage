package mage.cards.s;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
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

    public SignalTheClansEffect() {
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
        if (controller.searchLibrary(target, source, game)) {
            boolean shuffleDone = false;
            if (!target.getTargets().isEmpty()) {
                Cards cards = new CardsImpl(target.getTargets());
                //Reveal them
                controller.revealCards(source, cards, game);
                Card cardsArray[] = cards.getCards(game).toArray(new Card[0]);
                //If you reveal three cards with different names
                if (Stream.of(cardsArray).map(MageObject::getName).collect(Collectors.toSet()).size() == 3) {
                    //Choose one of them at random and put that card into your hand
                    Card randomCard = cards.getRandom(game);
                    controller.moveCards(randomCard, Zone.HAND, source, game);
                    cards.remove(randomCard);
                }
                // Shuffle the rest into your library
                if (!cards.isEmpty()) {
                    controller.shuffleCardsToLibrary(cards, game, source);
                    shuffleDone = true;
                }
            }
            if (!shuffleDone) {
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }

}
