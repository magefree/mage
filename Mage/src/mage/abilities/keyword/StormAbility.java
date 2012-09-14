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

package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.watchers.common.CastSpellLastTurnWatcher;



/**
 *
 * @author Plopman
 */
public class StormAbility extends TriggeredAbilityImpl<StormAbility> {

    public StormAbility() {
        super(Constants.Zone.STACK, new StormEffect());
    }

    private StormAbility(final StormAbility ability) {
        super(ability);
    }
    @Override
    public StormAbility copy() {
        return new StormAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getSourceId().equals(this.sourceId)) {
            StackObject spell = game.getStack().getStackObject(this.sourceId);
            if (spell instanceof Spell) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("StormSpell", spell);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Storm";
    }
}

class StormEffect extends OneShotEffect<StormEffect> {

    public StormEffect() {
        super(Constants.Outcome.Copy);
    }

    public StormEffect(final StormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) this.getValue("StormSpell");
        if (spell != null) {
            CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get("CastSpellLastTurnWatcher");

            for (int i = 0; i < watcher.getSpellOrder(spell) - 1; i++) {
                Spell copy = spell.copySpell();
                copy.setControllerId(source.getControllerId());
                copy.setCopiedSpell(true);
                game.getStack().push(copy);
                copy.chooseNewTargets(game, source.getControllerId());
            }
            return true;
        }
        return false;
    }

    @Override
    public StormEffect copy() {
        return new StormEffect(this);
    }
}
