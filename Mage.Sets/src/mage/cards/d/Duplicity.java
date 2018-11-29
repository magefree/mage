package mage.cards.d;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class Duplicity extends CardImpl {

    public Duplicity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // When Duplicity enters the battlefield, exile the top five cards of your library face down.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DuplicityEffect(), false));

        // At the beginning of your upkeep, you may exile all cards from your hand face down. If you do, put all other cards you own exiled with Duplicity into your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DuplicityExileHandEffect(), TargetController.YOU, true));

        // At the beginning of your end step, discard a card.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new DiscardControllerEffect(1), false));

        // When you lose control of Duplicity, put all cards exiled with Duplicity into their owner's graveyard.
        this.addAbility(new LoseControlDuplicity());

    }

    public Duplicity(final Duplicity card) {
        super(card);
    }

    @Override
    public Duplicity copy() {
        return new Duplicity(this);
    }
}

class DuplicityEffect extends OneShotEffect {

    public DuplicityEffect() {
        super(Outcome.Exile);
        staticText = "exile the top five cards of your library face down";
    }

    public DuplicityEffect(final DuplicityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null
                && sourceObject != null) {
            if (controller.getLibrary().hasCards()) {
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                Set<Card> cardsToExile = controller.getLibrary().getTopCards(game, 5);
                for (Card card : cardsToExile) {
                    controller.moveCardsToExile(card, source, game, true, exileId, sourceObject.getName());
                    card.setFaceDown(true, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DuplicityEffect copy() {
        return new DuplicityEffect(this);
    }
}

class DuplicityExileHandEffect extends OneShotEffect {

    public DuplicityExileHandEffect() {
        super(Outcome.Exile);
        staticText = "you may exile all cards from your hand face down. If you do, put all other cards you own exiled with {this} into your hand";
    }

    public DuplicityExileHandEffect(final DuplicityExileHandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null
                && sourceObject != null) {
            if (!controller.getHand().isEmpty()) {
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                Set<Card> cardsFromHandToExile = controller.getHand().getCards(game);
                for (Card card : cardsFromHandToExile) {
                    controller.moveCardsToExile(card, source, game, true, exileId, sourceObject.getName());
                    card.setFaceDown(true, game);
                }
                Set<Card> cardsInExile = game.getExile().getExileZone(exileId).getCards(game);
                Set<Card> cardsToReturnToHandFromExile = new HashSet<>();
                for (Card card : cardsInExile) {
                    if (!cardsFromHandToExile.contains(card)) {
                        cardsToReturnToHandFromExile.add(card);
                    }
                }
                controller.moveCards(cardsToReturnToHandFromExile, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public DuplicityExileHandEffect copy() {
        return new DuplicityExileHandEffect(this);
    }
}

class LoseControlDuplicity extends DelayedTriggeredAbility {

    public LoseControlDuplicity() {
        super(new PutExiledCardsInOwnersGraveyard(), Duration.EndOfGame, false);
    }

    public LoseControlDuplicity(final LoseControlDuplicity ability) {
        super(ability);
    }

    @Override
    public LoseControlDuplicity copy() {
        return new LoseControlDuplicity(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId);
    }

    @Override
    public String getRule() {
        return "When you lose control of {this}, put all cards exiled with {this} into their owner's graveyard.";
    }
}

class PutExiledCardsInOwnersGraveyard extends OneShotEffect {

    public PutExiledCardsInOwnersGraveyard() {
        super(Outcome.Neutral);
        staticText = " put all cards exiled with {this} into their owner's graveyard.";
    }

    public PutExiledCardsInOwnersGraveyard(final PutExiledCardsInOwnersGraveyard effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null
                && sourceObject != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            Set<Card> cardsInExile = game.getExile().getExileZone(exileId).getCards(game);
            controller.moveCardsToGraveyardWithInfo(cardsInExile, source, game, Zone.EXILED);
            return true;
        }
        return false;
    }

    @Override
    public PutExiledCardsInOwnersGraveyard copy() {
        return new PutExiledCardsInOwnersGraveyard(this);
    }
}
