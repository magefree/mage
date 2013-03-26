/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.effects;

import mage.Constants.Duration;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EntersBattlefieldEffect extends ReplacementEffectImpl<EntersBattlefieldEffect> {

    protected Effects baseEffects = new Effects();
    protected String text;
    protected Condition condition;
    protected boolean optional;

    public static final String SOURCE_CAST_SPELL_ABILITY = "sourceCastSpellAbility";

    public EntersBattlefieldEffect(Effect baseEffect) {
        this(baseEffect, "");
    }

    public EntersBattlefieldEffect(Effect baseEffect, String text) {
        this(baseEffect, null, text, true, false);
    }

    public EntersBattlefieldEffect(Effect baseEffect, String text, boolean optional) {
        this(baseEffect, null, text, true, optional);
    }

    public EntersBattlefieldEffect(Effect baseEffect, Condition condition, String text) {
        this(baseEffect, condition, text, true, false);
    }

    public EntersBattlefieldEffect(Effect baseEffect, Condition condition, String text, boolean selfScope, boolean optional) {
        super(Duration.OneUse, baseEffect.getOutcome(), selfScope);
        this.baseEffects.add(baseEffect);
        this.text = text;
        this.condition = condition;
        this.optional = optional;
    }

    public EntersBattlefieldEffect(EntersBattlefieldEffect effect) {
        super(effect);
        this.baseEffects = effect.baseEffects.copy();
        this.text = effect.text;
        this.condition = effect.condition;
        this.optional = effect.optional;
    }

    public void addEffect(Effect effect) {
        baseEffects.add(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ENTERS_THE_BATTLEFIELD) {
            if (event.getTargetId().equals(source.getSourceId())) {
                if (condition == null || condition.apply(game, source)) {
                    return true;
                }
            }
        }
        return false;
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
            if (!controller.chooseUse(outcome, new StringBuilder("Use effect of ").append(object.getName()).append("?").toString(), game)) {
                return false;
            }
        }
        Spell spell = game.getStack().getSpell(event.getSourceId());
        for (Effect effect: baseEffects) {
            if (source.activate(game, false)) {
                if (effect instanceof ContinuousEffect) {
                    game.addEffect((ContinuousEffect) effect, source);
                }
                else {
                    // noxx: commented it out because of resulting in a bug
                    // with CopyEffect (PhantasmalImageTest.java)
                    /*if (spell != null)
                        effect.apply(game, spell.getSpellAbility());
                    else
                        effect.apply(game, source);*/
                    if (spell != null) {
                        effect.setValue(SOURCE_CAST_SPELL_ABILITY, spell.getSpellAbility());
                    }
                    effect.apply(game, source);
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return (text == null || text.isEmpty()) ? baseEffects.getText(mode) : text;
    }

    @Override
    public EntersBattlefieldEffect copy() {
        return new EntersBattlefieldEffect(this);
    }

}
