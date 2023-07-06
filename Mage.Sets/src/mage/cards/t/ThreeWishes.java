
package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
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
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ThreeWishesLookAtCardEffect()));

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

    public ThreeWishesExileEffect() {
        super(Outcome.DrawCard);
        staticText = "Exile the top three cards of your library face down. Until your next turn, you may play those cards. At the beginning of your next upkeep, put any of those cards you didn't play into your graveyard";
    }

    public ThreeWishesExileEffect(final ThreeWishesExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), 0);
            Set<Card> topThreeCards = controller.getLibrary().getTopCards(game, 3);
            for (Card card : topThreeCards) {
                if (controller.moveCardsToExile(card, source, game, true, exileId, "Three Wishes")) {
                    card.setFaceDown(true, game);
                    CardUtil.makeCardPlayable(game, source, card, Duration.UntilYourNextTurn, null);
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

    public ThreeWishesPutIntoGraveyardEffect() {
        super(Outcome.Neutral);
        staticText = "At the beginning of your next upkeep, put any of those cards you didn't play into your graveyard";
    }

    public ThreeWishesPutIntoGraveyardEffect(final ThreeWishesPutIntoGraveyardEffect effect) {
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

class ThreeWishesLookAtCardEffect extends AsThoughEffectImpl {

    public ThreeWishesLookAtCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.Custom, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this} as long as they remain exiled";
    }

    public ThreeWishesLookAtCardEffect(final ThreeWishesLookAtCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ThreeWishesLookAtCardEffect copy() {
        return new ThreeWishesLookAtCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                MageObject sourceObject = game.getObject(source);
                if (sourceObject == null) {
                    return false;
                }
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), 0);
                ExileZone exile = game.getExile().getExileZone(exileId);
                return exile != null
                        && exile.contains(card.getId());
            }
        }
        return false;
    }
}