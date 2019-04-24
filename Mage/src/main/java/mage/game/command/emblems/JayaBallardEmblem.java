/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.game.command.emblems;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.common.CastFromGraveyardWatcher;

/**
 *
 * @author LevelX2
 */
public final class JayaBallardEmblem extends Emblem {
    // You get an emblem with "You may cast instant and sorcery cards from your graveyard. If a card cast this way would be put into your graveyard, exile it instead."

    public JayaBallardEmblem() {
        setName("Emblem Jaya Ballard");
        this.setExpansionSetCodeForImage("DOM");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new JayaBallardCastFromGraveyardEffect());
        ability.addEffect(new JayaBallardReplacementEffect());
        this.getAbilities().add(ability);
    }
}

class JayaBallardCastFromGraveyardEffect extends AsThoughEffectImpl {

    JayaBallardCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast instant and sorcery cards from your graveyard";
    }

    JayaBallardCastFromGraveyardEffect(final JayaBallardCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public JayaBallardCastFromGraveyardEffect copy() {
        return new JayaBallardCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(objectId);
        if (card != null) {
            return (affectedControllerId.equals(source.getControllerId())
                    && StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY.match(card, game)
                    && Zone.GRAVEYARD.equals(game.getState().getZone(card.getId())));
        }
        return false;
    }
}

class JayaBallardReplacementEffect extends ReplacementEffectImpl {

    public JayaBallardReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Exile);
        staticText = "If a card cast this way would be put into a graveyard this turn, exile it instead";
    }

    public JayaBallardReplacementEffect(final JayaBallardReplacementEffect effect) {
        super(effect);
    }

    @Override
    public JayaBallardReplacementEffect copy() {
        return new JayaBallardReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.STACK, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (Zone.GRAVEYARD == ((ZoneChangeEvent) event).getToZone()) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && (card.isInstant() || card.isSorcery())) {
                // TODO: Find a way to check, that the spell from graveyard was really cast by the ability of the emblem.
                // currently every spell cast from graveyard will be exiled.
                CastFromGraveyardWatcher watcher = (CastFromGraveyardWatcher) game.getState().getWatchers().get(CastFromGraveyardWatcher.class.getSimpleName());
                return watcher != null && watcher.spellWasCastFromGraveyard(event.getTargetId(), game.getState().getZoneChangeCounter(event.getTargetId()));
            }
        }
        return false;
    }
}
