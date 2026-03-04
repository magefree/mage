package mage.cards.t;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardWithDifferentNameInLibrary;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class TurtlesForever extends CardImpl {

    public TurtlesForever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Search your library and/or outside the game for exactly four legendary creature cards you own
        // with different names, then reveal those cards. An opponent chooses two of them.
        // Put the chosen cards into your hand and shuffle the rest into your library.
        this.getSpellAbility().addEffect(new TurtlesForeverEffect());
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private TurtlesForever(final TurtlesForever card) {
        super(card);
    }

    @Override
    public TurtlesForever copy() {
        return new TurtlesForever(this);
    }
}

class TurtlesForeverEffect extends OneShotEffect {

    private static final FilterCard libraryFilter = new FilterCreatureCard("legendary creature cards with different names");
    private static final FilterCard sideboardFilter = new FilterCreatureCard("legendary creature card with a different name from outside the game");

    static {
        libraryFilter.add(SuperType.LEGENDARY.getPredicate());
        sideboardFilter.add(SuperType.LEGENDARY.getPredicate());
    }

    TurtlesForeverEffect() {
        super(Outcome.DrawCard);
        this.staticText = "search your library and/or outside the game for exactly four legendary creature cards "
                + "you own with different names, then reveal those cards. An opponent chooses two of them. "
                + "Put the chosen cards into your hand and shuffle the rest into your library";
    }

    private TurtlesForeverEffect(final TurtlesForeverEffect effect) {
        super(effect);
    }

    @Override
    public TurtlesForeverEffect copy() {
        return new TurtlesForeverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TargetCardInLibrary libraryTarget = new TargetCardWithDifferentNameInLibrary(0, 4, libraryFilter);
        libraryTarget.withNotTarget(true);
        libraryTarget.withChooseHint("Step 1 of 2: Search library");
        controller.searchLibrary(libraryTarget, source, game);
        Cards cards = new CardsImpl(libraryTarget.getTargets());
        cards.retainZone(Zone.LIBRARY, game);

        int remainingCards = 4 - cards.size();
        if (remainingCards > 0) {
            Cards sideboardCards = controller.getSideboard();
            Set<Card> filteredSideboard = sideboardCards.getCards(sideboardFilter, game);
            if (!filteredSideboard.isEmpty()) {
                Cards availableSideboard = new CardsImpl();
                for (Card card : filteredSideboard) {
                    if (card.isOwnedBy(controller.getId())) {
                        availableSideboard.add(card);
                    }
                }
                if (!availableSideboard.isEmpty()) {
                    TargetCard sideboardTarget = new TargetCard(0, remainingCards, Zone.OUTSIDE, sideboardFilter) {
                        @Override
                        public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
                            if (!super.canTarget(playerId, id, source, game)) {
                                return false;
                            }
                            Card card = game.getCard(id);
                            Set<Card> disallowedCards = this.getTargets().stream()
                                    .map(game::getCard)
                                    .collect(Collectors.toSet());
                            Set<Card> checkList = new HashSet<>();
                            checkList.addAll(disallowedCards);
                            checkList.addAll(cards.getCards(game));
                            return card != null && checkList.stream()
                                    .filter(Objects::nonNull)
                                    .noneMatch(c -> CardUtil.haveSameNames(c, card));
                        }
                    };
                    sideboardTarget.withNotTarget(true);
                    sideboardTarget.withChooseHint("Step 2 of 2: Search outside the game");
                    controller.choose(Outcome.DrawCard, availableSideboard, sideboardTarget, source, game);
                    cards.addAll(sideboardTarget.getTargets());
                }
            }
        }

        if (cards.size() != 4) {
            controller.shuffleLibrary(source, game);
            return true;
        }

        controller.revealCards(source, cards, game);

        TargetOpponent targetOpponent = new TargetOpponent();
        targetOpponent.withNotTarget(true);
        controller.choose(outcome, targetOpponent, source, game);
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        Cards toHand = new CardsImpl();
        if (opponent != null) {
            FilterCard choiceFilter = new FilterCard("cards to put into the controller's hand");
            TargetCard chosenCards = new TargetCard(2, Zone.ALL, choiceFilter);
            chosenCards.withNotTarget(true);
            opponent.choose(outcome, cards, chosenCards, source, game);
            toHand.addAll(chosenCards.getTargets());
        }

        controller.moveCards(toHand, Zone.HAND, source, game);

        cards.removeAll(toHand);
        controller.putCardsOnTopOfLibrary(cards, game, source, false);
        controller.shuffleLibrary(source, game);

        return true;
    }
}
