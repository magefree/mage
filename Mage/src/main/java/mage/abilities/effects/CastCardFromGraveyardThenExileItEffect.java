package mage.abilities.effects;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

public class CastCardFromGraveyardThenExileItEffect extends OneShotEffect {

    public CastCardFromGraveyardThenExileItEffect() {
        super(Outcome.Benefit);
    }

    CastCardFromGraveyardThenExileItEffect(final CastCardFromGraveyardThenExileItEffect effect) {
        super(effect);
    }

    @Override
    public CastCardFromGraveyardThenExileItEffect copy() {
        return new CastCardFromGraveyardThenExileItEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            ContinuousEffect effect = new CastCardFromGraveyardEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
            game.addEffect(effect, source);
            effect = new ExileReplacementEffect(card.getId());
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class CastCardFromGraveyardEffect extends AsThoughEffectImpl {

    CastCardFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.staticText = "You may cast target card from your graveyard";
    }

    CastCardFromGraveyardEffect(final CastCardFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CastCardFromGraveyardEffect copy() {
        return new CastCardFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return objectId.equals(this.getTargetPointer().getFirst(game, source)) && affectedControllerId.equals(source.getControllerId());
    }
}

class ExileReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    ExileReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
        this.staticText = "If that card would be put into your graveyard this turn, exile it instead";
    }

    ExileReplacementEffect(final ExileReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public ExileReplacementEffect copy() {
        return new ExileReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(this.cardId);
        if (controller != null && card != null) {
            return controller.moveCards(card, Zone.EXILED, source, game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTargetId().equals(this.cardId);
    }
}