
package mage.cards.m;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class MoonlightBargain extends CardImpl {

    public MoonlightBargain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");

        // Look at the top five cards of your library. For each card, put that card into your graveyard unless you pay 2 life. Then put the rest into your hand.
        getSpellAbility().addEffect(new MoonlightBargainEffect());
    }

    private MoonlightBargain(final MoonlightBargain card) {
        super(card);
    }

    @Override
    public MoonlightBargain copy() {
        return new MoonlightBargain(this);
    }
}

class MoonlightBargainEffect extends OneShotEffect {

    public MoonlightBargainEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top five cards of your library. For each card, put that card into your graveyard unless you pay 2 life. Then put the rest into your hand";
    }

    public MoonlightBargainEffect(final MoonlightBargainEffect effect) {
        super(effect);
    }

    @Override
    public MoonlightBargainEffect copy() {
        return new MoonlightBargainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Set<Card> topFive = controller.getLibrary().getTopCards(game, 5);
            Cards lookAtCards = new CardsImpl();
            lookAtCards.addAllCards(topFive);
            controller.lookAtCards(sourceObject.getIdName(), lookAtCards, game);
            Cards toHand = new CardsImpl();
            for (Card card : topFive) {
                PayLifeCost cost = new PayLifeCost(2);
                if (cost.canPay(source, source, source.getControllerId(), game)) {
                    if (controller.chooseUse(outcome, "Put " + card.getIdName() + " into your graveyard unless you pay 2 life", sourceObject.getIdName(),
                            "Pay 2 life and put into hand", "Put into your graveyard", source, game)) {
                        if (cost.pay(source, game, source, source.getControllerId(), false)) {
                            toHand.add(card);
                        } else {
                            controller.moveCards(card, Zone.GRAVEYARD, source, game);
                        }
                    } else {
                        controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    }
                }
            }
            controller.moveCards(toHand, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
