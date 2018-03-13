/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Eirkei
 */
public class PermanentsEnterBattlefieldTappedEffect extends ReplacementEffectImpl {
    protected FilterPermanent filter;
    
    public PermanentsEnterBattlefieldTappedEffect() {
        this(new FilterPermanent());
    }
    
    public PermanentsEnterBattlefieldTappedEffect(FilterPermanent filter) { 
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        this.filter = filter;
        this.setText();
    }

    public PermanentsEnterBattlefieldTappedEffect(final PermanentsEnterBattlefieldTappedEffect effect) {
        super(effect);
        
        if (effect.filter != null){
            this.filter = effect.filter.copy();
        }
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        
        if (target != null) {
            target.tap(game);
        }
        
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();

        return permanent != null && filter.match(permanent, source.getSourceId(), event.getPlayerId(), game);
    }

    @Override
    public PermanentsEnterBattlefieldTappedEffect copy() {
        return new PermanentsEnterBattlefieldTappedEffect(this);
    }
    
    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage());
        sb.append(" enter the battlefield tapped");        
        staticText = sb.toString();
    }
}
