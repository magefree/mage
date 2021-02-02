package mage.cards.g;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.ExileZone;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2, jeffwadsworth & L_J
 */
public final class GusthasScepter extends CardImpl {

    public GusthasScepter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // {T}: Exile a card from your hand face down. You may look at it for as long as it remains exiled.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GusthasScepterExileEffect(), new TapSourceCost()));

        // {T}: Return a card you own exiled with Gustha’s Scepter to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInGusthasScepterExile(this.getId()));
        this.addAbility(ability);

        // When you lose control of Gustha’s Scepter, put all cards exiled with Gustha’s Scepter into their owner’s graveyard.
        this.addAbility(new GusthasScepterLoseControlAbility());

    }

    private GusthasScepter(final GusthasScepter card) {
        super(card);
    }

    @Override
    public GusthasScepter copy() {
        return new GusthasScepter(this);
    }
}

class GusthasScepterExileEffect extends OneShotEffect {

    public GusthasScepterExileEffect() {
        super(Outcome.DrawCard);
        staticText = "Exile a card from your hand face down. You may look at it for as long as it remains exiled";
    }

    public GusthasScepterExileEffect(final GusthasScepterExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInHand(new FilterCard("card to exile"));
            if (controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                MageObject sourceObject = game.getObject(source.getSourceId());
                if (card != null && sourceObject != null) {
                    UUID exileId = source.getSourceId();
                    if (card.moveToExile(exileId,
                            sourceObject.getIdName(),
                            source,
                            game)) {
                        card.setFaceDown(true, game);
                        game.addEffect(new GusthasScepterLookAtCardEffect(card.getId()), source);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public GusthasScepterExileEffect copy() {
        return new GusthasScepterExileEffect(this);
    }
}

class TargetCardInGusthasScepterExile extends TargetCardInExile {

    public TargetCardInGusthasScepterExile(UUID cardId) {
        super(1, 1, new FilterCard("card exiled with Gustha's Scepter"), null);
    }

    public TargetCardInGusthasScepterExile(final TargetCardInGusthasScepterExile target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        Card sourceCard = game.getCard(sourceId);
        if (sourceCard != null) {
            UUID exileId = sourceId;
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null && !exile.isEmpty()) {
                possibleTargets.addAll(exile);
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        Card sourceCard = game.getCard(sourceId);
        if (sourceCard != null) {
            UUID exileId = sourceId;
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null && !exile.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null
                && game.getState().getZone(card.getId()) == Zone.EXILED) {
            ExileZone exile = null;
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourceCard != null) {
                UUID exileId = source.getSourceId();
                exile = game.getExile().getExileZone(exileId);
            }
            if (exile != null
                    && exile.contains(id)) {
                return filter.match(card, source.getControllerId(), game);
            }
        }
        return false;
    }

    @Override
    public TargetCardInGusthasScepterExile copy() {
        return new TargetCardInGusthasScepterExile(this);
    }
}

class GusthasScepterLookAtCardEffect extends AsThoughEffectImpl {

    private final UUID cardId;

    public GusthasScepterLookAtCardEffect(UUID cardId) {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        this.cardId = cardId;
        staticText = "You may look at it for as long as it remains exiled";
    }

    public GusthasScepterLookAtCardEffect(final GusthasScepterLookAtCardEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GusthasScepterLookAtCardEffect copy() {
        return new GusthasScepterLookAtCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(cardId) && affectedControllerId.equals(source.getControllerId())) {
            MageObject sourceObject = source.getSourceObject(game);
            if (sourceObject != null) {
                UUID exileId = source.getSourceId();
                ExileZone exileZone = game.getExile().getExileZone(exileId);
                if (exileZone != null
                        && exileZone.contains(cardId)) {
                    Player controller = game.getPlayer(source.getControllerId());
                    Card card = game.getCard(cardId);
                    if (controller != null
                            && card != null
                            && game.getState().getZone(cardId) == Zone.EXILED) {
                        return true;
                    }
                } else {
                    discard();
                }
            }
        }
        return false;
    }
}

class GusthasScepterLoseControlAbility extends DelayedTriggeredAbility {

    public GusthasScepterLoseControlAbility() {
        super(new GusthasScepterPutExiledCardsInOwnersGraveyard(), Duration.EndOfGame, false);
    }

    public GusthasScepterLoseControlAbility(final GusthasScepterLoseControlAbility ability) {
        super(ability);
    }

    @Override
    public GusthasScepterLoseControlAbility copy() {
        return new GusthasScepterLoseControlAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_CONTROL) {
            return event.getPlayerId().equals(controllerId)
                    && event.getTargetId().equals(this.getSourceId());
        } else if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            if (event.getTargetId().equals(this.getSourceId())) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                return (zEvent.getFromZone() == Zone.BATTLEFIELD);
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you lose control of {this}, put all cards exiled with {this} into their owner's graveyard.";
    }
}

class GusthasScepterPutExiledCardsInOwnersGraveyard extends OneShotEffect {

    public GusthasScepterPutExiledCardsInOwnersGraveyard() {
        super(Outcome.Neutral);
        staticText = " put all cards exiled with {this} into their owner's graveyard";
    }

    public GusthasScepterPutExiledCardsInOwnersGraveyard(final GusthasScepterPutExiledCardsInOwnersGraveyard effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID exileId = source.getSourceId();
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            if (exileZone != null) {
                return controller.moveCards(exileZone.getCards(game), Zone.GRAVEYARD, source, game);
            }
        }
        return false;
    }

    @Override
    public GusthasScepterPutExiledCardsInOwnersGraveyard copy() {
        return new GusthasScepterPutExiledCardsInOwnersGraveyard(this);
    }
}
