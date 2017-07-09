/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.replacement;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class DiesReplacementEffect extends ReplacementEffectImpl {

    private final MageObjectReference objectRef;

    public DiesReplacementEffect(MageObjectReference objectRef, Duration duration) {
        super(duration, Outcome.Exile);
        this.objectRef = objectRef;
        staticText = "If that creature would die " + (duration.equals(Duration.EndOfTurn) ? "this turn" : "") + ", exile it instead";
    }

    public DiesReplacementEffect(final DiesReplacementEffect effect) {
        super(effect);
        this.objectRef = effect.objectRef;
    }

    @Override
    public DiesReplacementEffect copy() {
        return new DiesReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null) {
            return controller.moveCardToExileWithInfo(permanent, null, "", source.getSourceId(), game, Zone.BATTLEFIELD, true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        return zce.isDiesEvent()
                && objectRef.equals(new MageObjectReference(zce.getTarget(), game));
    }

}
