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
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class SpellCastAllTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell spellCard = new FilterSpell("a spell");
    protected FilterSpell filter;
    protected String rule;
    protected SetTargetPointer setTargetPointer;

    public SpellCastAllTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, spellCard, optional, SetTargetPointer.NONE);
    }

    public SpellCastAllTriggeredAbility(Effect effect, FilterSpell filter, boolean optional) {
        this(effect, filter, optional, SetTargetPointer.NONE);
    }

    public SpellCastAllTriggeredAbility(Effect effect, FilterSpell filter, boolean optional, String rule) {
        this(effect, filter, optional, SetTargetPointer.NONE);
        this.rule = rule;
    }

    public SpellCastAllTriggeredAbility(Effect effect, FilterSpell filter, boolean optional, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, filter, optional, setTargetPointer);
    }

    public SpellCastAllTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public SpellCastAllTriggeredAbility(final SpellCastAllTriggeredAbility ability) {
        super(ability);
        filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
        this.rule = ability.rule;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && filter.match(spell, getSourceId(), getControllerId(), game)) {
            if (!setTargetPointer.equals(SetTargetPointer.NONE)) {
                for (Effect effect : this.getEffects()) {
                    switch (setTargetPointer) {
                        case SPELL:
                            effect.setTargetPointer(new FixedTarget(spell.getId()));
                            break;
                        case PLAYER:
                            effect.setTargetPointer(new FixedTarget(spell.getControllerId()));
                            break;
                    }

                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        if (rule != null && !rule.isEmpty()) {
            return rule;
        }
        return "Whenever a player casts " + filter.getMessage() + ", " + super.getRule();
    }

    @Override
    public SpellCastAllTriggeredAbility copy() {
        return new SpellCastAllTriggeredAbility(this);
    }
}
