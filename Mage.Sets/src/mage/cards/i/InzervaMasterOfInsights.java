package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.command.emblems.InzervaMasterOfInsightsEmblem;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author PurpleCrowbar
 */
public final class InzervaMasterOfInsights extends CardImpl {

    public InzervaMasterOfInsights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{2/U}{2/R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INZERVA);
        this.setStartingLoyalty(4);

        // +2: Draw two cards, then discard a card.
        this.addAbility(new LoyaltyAbility(new DrawDiscardControllerEffect(2, 1), 2));

        // −2: Look at the top two cards of each other player's library, then put any number of them on the bottom of that library and the rest on top in any order. Scry 2.
        LoyaltyAbility ability = new LoyaltyAbility(new InzervaMasterOfInsightsEffect(), -2);
        ability.addEffect(new ScryEffect(2, false));
        this.addAbility(ability);

        // −4: You get an emblem with "Your opponents play with their hands revealed" and "Whenever an opponent draws a card, this emblem deals 1 damage to them."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new InzervaMasterOfInsightsEmblem()), -4));
    }

    private InzervaMasterOfInsights(final InzervaMasterOfInsights card) {
        super(card);
    }

    @Override
    public InzervaMasterOfInsights copy() {
        return new InzervaMasterOfInsights(this);
    }
}

class InzervaMasterOfInsightsEffect extends OneShotEffect {

    InzervaMasterOfInsightsEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top two cards of each other player's library, then put any number of them on the bottom of that library and the rest on top in any order";
    }

    private InzervaMasterOfInsightsEffect(final InzervaMasterOfInsightsEffect effect) {
        super(effect);
    }

    @Override
    public InzervaMasterOfInsightsEffect copy() {
        return new InzervaMasterOfInsightsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player opponent = game.getPlayer(playerId);
            if (playerId.equals(controller.getId()) || opponent == null) {
                continue;
            }
            Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, 2));
            if (cards.isEmpty()) {
                continue;
            }

            // put to bottom
            TargetCard targeBottom = new TargetCard(0, cards.size(), Zone.LIBRARY, new FilterCard("cards to PUT on the BOTTOM of " + opponent.getName() + "'s library"));
            targeBottom.setRequired(false);
            controller.choose(Outcome.Detriment, cards, targeBottom, source, game);
            Cards cardsToBottom = new CardsImpl(targeBottom.getTargets().stream()
                .filter(cards::contains)
                .collect(Collectors.toList()
            ));
            if (controller.putCardsOnBottomOfLibrary(cardsToBottom, game, source, true)) {
                // put to top
                cards.removeAll(cardsToBottom);
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
            }
        }
        return true;
    }
}
