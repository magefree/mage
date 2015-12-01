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
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import static mage.game.events.GameEvent.EventType.DAMAGE_CREATURE;
import static mage.game.events.GameEvent.EventType.DAMAGE_PLANESWALKER;
import static mage.game.events.GameEvent.EventType.DAMAGE_PLAYER;

/**
 *
 * @author LevelX2
 */

public class AssignNoCombatDamageSourceEffect extends ReplacementEffectImpl {

    private boolean partOfOptionalEffect;

    public AssignNoCombatDamageSourceEffect(Duration duration) {
        this(duration, false);
    }

    public AssignNoCombatDamageSourceEffect(Duration duration, boolean partOfOptionalEffect) {
        super(duration, Outcome.PreventDamage);
        this.partOfOptionalEffect = partOfOptionalEffect;
        staticText = setText();
    }

    public AssignNoCombatDamageSourceEffect(final AssignNoCombatDamageSourceEffect effect) {
        super(effect);
        this.partOfOptionalEffect = effect.partOfOptionalEffect;
    }

    @Override
    public AssignNoCombatDamageSourceEffect copy() {
        return new AssignNoCombatDamageSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
            case DAMAGE_PLANESWALKER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        return event.getSourceId().equals(source.getSourceId()) && damageEvent.isCombatDamage();
    }

    private String setText() {
        String text = "";
        if(partOfOptionalEffect) {
            text = "If you do, ";
        }
        text += "{this} assigns no combat damage";
        switch(duration) {
            case EndOfTurn:
                text += " this turn";
                break;
            case EndOfCombat:
                text += " this combat";
                break;
            default:
                if (duration.toString().length() > 0) {
                    text += " " + duration.toString();
                }
        }
        return text;
    }
}
