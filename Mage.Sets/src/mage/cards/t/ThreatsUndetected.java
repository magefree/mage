package mage.cards.t;

import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class ThreatsUndetected extends CardImpl {

    public ThreatsUndetected(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library for up to four creature cards with different powers and reveal them. An opponent chooses two of those cards. Shuffle the chosen cards into your library and put the rest into your hand.
        this.getSpellAbility().addEffect(new ThreatsUndetectedEffect());
    }

    private ThreatsUndetected(final ThreatsUndetected card) {
        super(card);
    }

    @Override
    public ThreatsUndetected copy() {
        return new ThreatsUndetected(this);
    }
}

class ThreatsUndetectedEffect extends OneShotEffect {

    public ThreatsUndetectedEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for up to four creature cards with different powers and reveal them. An opponent chooses two of those cards. Shuffle the chosen cards into your library and put the rest into your hand.";
    }

    private ThreatsUndetectedEffect(final ThreatsUndetectedEffect effect) {
        super(effect);
    }

    @Override
    public ThreatsUndetectedEffect copy() {
        return new ThreatsUndetectedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        Set<UUID> opponents = game.getOpponents(controllerId);
        Player opponent;
        if (opponents.size() == 1) {
            opponent = game.getPlayer(opponents.iterator().next());
        } else {
            TargetOpponent target = new TargetOpponent(true);
            controller.chooseTarget(Outcome.Detriment, target, source, game);
            opponent = game.getPlayer(target.getFirstTarget());
        }
        if (opponent == null) {
            return false;
        }
        ThreatsUndetectedTarget target = new ThreatsUndetectedTarget();
        controller.searchLibrary(target, source, game);
        Cards searchedCards = new CardsImpl(target.getTargets());
        if (searchedCards.isEmpty()) {
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.revealCards(source, searchedCards, game);
        TargetCard opponentTarget = new TargetCard(Math.min(searchedCards.size(), 2), Zone.LIBRARY, StaticFilters.FILTER_CARD);
        opponentTarget.withChooseHint("to be shuffled into opponent's library");
        opponent.chooseTarget(Outcome.Benefit, searchedCards, opponentTarget, source, game);
        Cards chosenCards = new CardsImpl(opponentTarget.getTargets());
        if (chosenCards.isEmpty()) {
            controller.shuffleLibrary(source, game);
        } else {
            searchedCards.removeAll(chosenCards);
            controller.shuffleCardsToLibrary(chosenCards, game, source);
        }
        controller.moveCards(searchedCards, Zone.HAND, source, game);
        return true;
    }
}

class ThreatsUndetectedTarget extends TargetCardInLibrary {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with different powers");

    public ThreatsUndetectedTarget() {
        super(0, 4, filter);
    }

    private ThreatsUndetectedTarget(final ThreatsUndetectedTarget target) {
        super(target);
    }

    @Override
    public ThreatsUndetectedTarget copy() {
        return new ThreatsUndetectedTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        int power = card.getPower().getValue();
        for (UUID targetId : this.getTargets()) {
            Card targetCard = game.getCard(targetId);
            if (targetCard != null && targetCard.getPower().getValue() == power) {
                return false;
            }
        }
        return true;
    }
}
