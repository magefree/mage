package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class InsidiousDreams extends CardImpl {

    public InsidiousDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // As an additional cost to cast Insidious Dreams, discard X cards.
        this.getSpellAbility().addCost(new DiscardXTargetCost(StaticFilters.FILTER_CARD_CARDS, true));

        // Search your library for X cards. Then shuffle your library and put those cards on top of it in any order.
        this.getSpellAbility().addEffect(new InsidiousDreamsEffect());
    }

    private InsidiousDreams(final InsidiousDreams card) {
        super(card);
    }

    @Override
    public InsidiousDreams copy() {
        return new InsidiousDreams(this);
    }
}

class InsidiousDreamsEffect extends OneShotEffect {

    public InsidiousDreamsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for up to X cards. Then shuffle and put those cards on top of it in any order";
    }

    public InsidiousDreamsEffect(final InsidiousDreamsEffect effect) {
        super(effect);
    }

    @Override
    public InsidiousDreamsEffect copy() {
        return new InsidiousDreamsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(
                0, GetXValue.instance.calculate(game, source, this), StaticFilters.FILTER_CARD_CARDS
        );
        controller.searchLibrary(target, source, game);
        Cards chosen = new CardsImpl();
        target.getTargets().stream().forEach(cardId -> controller.getLibrary().getCard(cardId, game));
        controller.shuffleLibrary(source, game);
        controller.putCardsOnTopOfLibrary(chosen, game, source, true);
        return true;
    }
}
