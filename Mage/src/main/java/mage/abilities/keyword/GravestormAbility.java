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

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.watchers.common.GravestormWatcher;



/**
 *
 * @author emerald000
 */
public class GravestormAbility extends TriggeredAbilityImpl {

    public GravestormAbility() {
        super(Zone.STACK, new GravestormEffect());
    }

    private GravestormAbility(final GravestormAbility ability) {
        super(ability);
    }
    @Override
    public GravestormAbility copy() {
        return new GravestormAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            StackObject spell = game.getStack().getStackObject(this.getSourceId());
            if (spell instanceof Spell) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("GravestormSpell", spell);
                    effect.setValue("GravestormSpellRef", new MageObjectReference(spell.getId(), game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Gravestorm <i>(When you cast this spell, copy it for each permanent put into a graveyard this turn. You may choose new targets for the copies.</i>)" ;
    }
}

class GravestormEffect extends OneShotEffect {

    GravestormEffect() {
        super(Outcome.Copy);
    }

    GravestormEffect(final GravestormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObjectReference spellRef = (MageObjectReference) this.getValue("GravestormSpellRef");
        if (spellRef != null) {
            GravestormWatcher watcher = (GravestormWatcher) game.getState().getWatchers().get("GravestormWatcher");
            int gravestormCount = watcher.getGravestormCount();
            if (gravestormCount > 0) {
                Spell spell = (Spell) this.getValue("GravestormSpell");
                if (spell != null) {
                    if (!game.isSimulation()) {
                        game.informPlayers("Gravestorm: " + spell.getName() + " will be copied " + gravestormCount + " time" + (gravestormCount > 1 ? "s" : ""));
                    }
                    for (int i = 0; i < gravestormCount; i++) {
                        Spell copy = spell.copySpell();
                        copy.setControllerId(source.getControllerId());
                        copy.setCopiedSpell(true);
                        game.getStack().push(copy);
                        copy.chooseNewTargets(game, source.getControllerId());
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public GravestormEffect copy() {
        return new GravestormEffect(this);
    }
}
