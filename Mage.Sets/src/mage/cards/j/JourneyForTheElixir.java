package mage.cards.j;

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
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class JourneyForTheElixir extends CardImpl {

    public JourneyForTheElixir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library and graveyard for a basic land card and a card named Jiang Yanggu, reveal them, put them into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new JourneyForTheElixirEffect());
    }

    public JourneyForTheElixir(final JourneyForTheElixir card) {
        super(card);
    }

    @Override
    public JourneyForTheElixir copy() {
        return new JourneyForTheElixir(this);
    }
}

class JourneyForTheElixirEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card named Jiang Yanggu");

    static {
        filter.add(new NamePredicate("Jiang Yanggu"));
    }

    public JourneyForTheElixirEffect() {
        super(Outcome.DrawCard);
        this.staticText = "search your library and graveyard for a basic land card "
                + "and a card named Jiang Yanggu, reveal them, "
                + "put them into your hand, then shuffle your library";
    }

    public JourneyForTheElixirEffect(final JourneyForTheElixirEffect effect) {
        super(effect);
    }

    @Override
    public JourneyForTheElixirEffect copy() {
        return new JourneyForTheElixirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        boolean walkerFound = false;
        boolean landFound = false;
        Cards cardsToHand = new CardsImpl();
        if (player.chooseUse(outcome, "Search your graveyard?", source, game)) {
            TargetCardInGraveyard targetWalker = new TargetCardInGraveyard(0, 1, filter);
            targetWalker.setNotTarget(true);
            if (player.choose(outcome, targetWalker, source.getSourceId(), game)) {
                Card card = game.getCard(targetWalker.getFirstTarget());
                if (card != null) {
                    cardsToHand.add(card);
                    walkerFound = true;
                }
            }
            TargetCardInGraveyard targetLand = new TargetCardInGraveyard(0, 1, StaticFilters.FILTER_CARD_BASIC_LAND_A);
            targetLand.setNotTarget(true);
            if (player.choose(outcome, targetLand, source.getSourceId(), game)) {
                Card card = game.getCard(targetLand.getFirstTarget());
                if (card != null) {
                    cardsToHand.add(card);
                    landFound = true;
                }
            }
        }
        if (!walkerFound || !landFound) {
            TargetCardInLibrary targetWalker = new TargetCardInLibrary(0, 1, filter);
            targetWalker.setNotTarget(true);
            if (!walkerFound && player.searchLibrary(targetWalker, game, false)) {
                Card card = game.getCard(targetWalker.getFirstTarget());
                if (card != null) {
                    cardsToHand.add(card);
                }
            }
            TargetCardInLibrary targetLand = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_BASIC_LAND_A);
            targetLand.setNotTarget(true);
            if (!landFound && player.searchLibrary(targetLand, game, false)) {
                Card card = game.getCard(targetLand.getFirstTarget());
                if (card != null) {
                    cardsToHand.add(card);
                }
            }
        }
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LIBRARY_SEARCHED, player.getId(), player.getId()));
        player.revealCards(source, cardsToHand, game);
        player.moveCards(cardsToHand, Zone.HAND, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
