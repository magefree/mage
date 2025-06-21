
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class ThreeWishes extends CardImpl {

    public ThreeWishes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Exile the top three cards of your library face down. You may look at those cards for as long as they remain exiled. Until your next turn, you may play those cards. At the beginning of your next upkeep, put any of those cards you didn't play into your graveyard.
        this.getSpellAbility().addEffect(new ThreeWishesExileEffect());

    }

    private ThreeWishes(final ThreeWishes card) {
        super(card);
    }

    @Override
    public ThreeWishes copy() {
        return new ThreeWishes(this);
    }
}

class ThreeWishesExileEffect extends OneShotEffect {

    ThreeWishesExileEffect() {
        super(Outcome.DrawCard);
        staticText = "Exile the top three cards of your library face down. Until your next turn, you may play those cards. At the beginning of your next upkeep, put any of those cards you didn't play into your graveyard";
    }

    private ThreeWishesExileEffect(final ThreeWishesExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), 0);
            Set<Card> topThreeCards = controller.getLibrary().getTopCards(game, 3);
            for (Card card : topThreeCards) {
                if (CardUtil.moveCardsToExileFaceDown(game, source, controller, card, exileId, "Three Wishes", true)) {
                    CardUtil.makeCardPlayable(game, source, card, false, Duration.UntilYourNextTurn, false);
                }
            }
            DelayedTriggeredAbility delayed = new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(new ThreeWishesPutIntoGraveyardEffect());
            game.addDelayedTriggeredAbility(delayed, source);
            return true;
        }
        return false;
    }

    @Override
    public ThreeWishesExileEffect copy() {
        return new ThreeWishesExileEffect(this);
    }
}

class ThreeWishesPutIntoGraveyardEffect extends OneShotEffect {

    ThreeWishesPutIntoGraveyardEffect() {
        super(Outcome.Neutral);
        staticText = "At the beginning of your next upkeep, put any of those cards you didn't play into your graveyard";
    }

    private ThreeWishesPutIntoGraveyardEffect(final ThreeWishesPutIntoGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), 0);
            Set<Card> cardsInExile = game.getExile().getExileZone(exileId).getCards(game);
            if (cardsInExile != null) {
                controller.moveCardsToGraveyardWithInfo(cardsInExile, source, game, Zone.EXILED);
                return true;
            }
        }
        return false;
    }

    @Override
    public ThreeWishesPutIntoGraveyardEffect copy() {
        return new ThreeWishesPutIntoGraveyardEffect(this);
    }
}
