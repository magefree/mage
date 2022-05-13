
package mage.cards.s;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
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
 * @author TheElk801
 */
public final class SwordPointDiplomacy extends CardImpl {

    public SwordPointDiplomacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Reveal the top three cards of your library. For each of those cards, put that card into your hand unless any opponent pays 3 life. Then exile the rest.
        this.getSpellAbility().addEffect(new SwordPointDiplomacyEffect());
    }

    private SwordPointDiplomacy(final SwordPointDiplomacy card) {
        super(card);
    }

    @Override
    public SwordPointDiplomacy copy() {
        return new SwordPointDiplomacy(this);
    }
}

class SwordPointDiplomacyEffect extends OneShotEffect {

    SwordPointDiplomacyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top three cards of your library. For each of those cards, put that card into your hand unless any opponent pays 3 life. Then exile the rest.";
    }

    SwordPointDiplomacyEffect(final SwordPointDiplomacyEffect effect) {
        super(effect);
    }

    @Override
    public SwordPointDiplomacyEffect copy() {
        return new SwordPointDiplomacyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        int amount = Math.min(controller.getLibrary().size(), 3);
        CardsImpl cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, amount));
        controller.revealCards(sourceObject.getIdName(), cards, game);
        Set<Card> cardsList = cards.getCards(game);
        Cards cardsToHand = new CardsImpl();
        for (Card card : cardsList) {
            boolean keepIt = true;
            Cost cost = new PayLifeCost(3);
            for (UUID oppId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(oppId);
                if (opponent != null
                        && cost.canPay(source, source, opponent.getId(), game)
                        && opponent.chooseUse(Outcome.Neutral, "Pay 3 life to prevent " + controller.getLogName() + " from getting " + card.getLogName() + "?", source, game)
                        && cost.pay(source, game, source, opponent.getId(), true)) {
                    keepIt = false;
                }
            }
            if (keepIt) {
                cardsToHand.add(card);
                cards.remove(card);
            }
        }
        controller.moveCards(cardsToHand, Zone.HAND, source, game);
        controller.moveCards(cards, Zone.EXILED, source, game);
        return true;
    }
}
