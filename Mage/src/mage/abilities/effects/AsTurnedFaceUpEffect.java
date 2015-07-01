/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.abilities.effects;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class AsTurnedFaceUpEffect extends ReplacementEffectImpl {

    protected Effects baseEffects = new Effects();
    protected boolean optional;        
    
    public AsTurnedFaceUpEffect(Effect baseEffect, boolean optional) {
        super(Duration.WhileOnBattlefield, baseEffect.getOutcome(), true);
        this.baseEffects.add(baseEffect);
        this.optional = optional;        
    }
    
    public AsTurnedFaceUpEffect(final AsTurnedFaceUpEffect effect) {
        super(effect);
        this.baseEffects = effect.baseEffects.copy();
        this.optional = effect.optional;
    }
    
    public void addEffect(Effect effect) {
        baseEffects.add(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURNFACEUP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (optional) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject object = game.getObject(source.getSourceId());
            if (controller == null || object == null) {
                return false;
            }
            if (!controller.chooseUse(outcome, new StringBuilder("Use effect of ").append(object.getLogName()).append("?").toString(), source, game)) {
                return false;
            }
        }
        for (Effect effect: baseEffects) {
            if (source.activate(game, false)) {
                if (effect instanceof ContinuousEffect) {
                    game.addEffect((ContinuousEffect) effect, source);
                }
                else {
                    effect.apply(game, source);
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "As {this} is turned face up, " + baseEffects.getText(mode);
    }
    
    @Override
    public AsTurnedFaceUpEffect copy() {
        return new AsTurnedFaceUpEffect(this);
    }
    
    
}
