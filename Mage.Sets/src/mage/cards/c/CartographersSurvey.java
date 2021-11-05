package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryControllerEffect;
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
import mage.target.TargetCard;

/**
 *
 * @author weirddan455
 */
public final class CartographersSurvey extends CardImpl {

    public CartographersSurvey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Look at the top seven cards of your library. Put up to two land cards from among them onto the battlefield tapped. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new CartographersSurveyEffect());
    }

    private CartographersSurvey(final CartographersSurvey card) {
        super(card);
    }

    @Override
    public CartographersSurvey copy() {
        return new CartographersSurvey(this);
    }
}

class CartographersSurveyEffect extends LookLibraryControllerEffect {

    public CartographersSurveyEffect() {
        super(Outcome.PutLandInPlay, StaticValue.get(7), false, Zone.LIBRARY, false);
        this.setBackInRandomOrder(true);
        staticText = "Look at the top seven cards of your library. Put up to two land cards from among them onto the battlefield tapped. Put the rest on the bottom of your library in a random order";
    }

    private CartographersSurveyEffect(final CartographersSurveyEffect effect) {
        super(effect);
    }

    @Override
    public CartographersSurveyEffect copy() {
        return new CartographersSurveyEffect(this);
    }

    @Override
    protected void actionWithSelectedCards(Cards cards, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCard target = new TargetCard(0, 2, Zone.LIBRARY, StaticFilters.FILTER_CARD_LANDS);
            controller.choose(outcome, cards, target, game);
            Cards pickedCards = new CardsImpl(target.getTargets());
            if (!pickedCards.isEmpty()) {
                cards.removeAll(pickedCards);
                controller.moveCards(pickedCards.getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);
            }
        }
    }
}
