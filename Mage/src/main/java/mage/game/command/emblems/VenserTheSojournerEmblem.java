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
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ..AS IS.. AND ANY EXPRESS OR IMPLIED
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
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class VenserTheSojournerEmblem extends Emblem {

    /**
     * Emblem: "Whenever you cast a spell, exile target permanent."
     */

    public VenserTheSojournerEmblem() {
        this.setName("Emblem Venser");
        Ability ability = new VenserTheSojournerSpellCastTriggeredAbility(new ExileTargetEffect(), false);
        Target target = new TargetPermanent();
        ability.addTarget(target);
        this.getAbilities().add(ability);
    }
}

class VenserTheSojournerSpellCastTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell spellCard = new FilterSpell("a spell");
    protected FilterSpell filter;

    /**
     * If true, the source that triggered the ability will be set as target to
     * effect.
     */
    protected boolean rememberSource = false;

    public VenserTheSojournerSpellCastTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.COMMAND, effect, optional);
        this.filter = spellCard;
    }

    public VenserTheSojournerSpellCastTriggeredAbility(final VenserTheSojournerSpellCastTriggeredAbility ability) {
        super(ability);
        filter = ability.filter;
        this.rememberSource = ability.rememberSource;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, game)) {
                if (rememberSource) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(spell.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell, exile target permanent.";
    }

    @Override
    public VenserTheSojournerSpellCastTriggeredAbility copy() {
        return new VenserTheSojournerSpellCastTriggeredAbility(this);
    }
}
