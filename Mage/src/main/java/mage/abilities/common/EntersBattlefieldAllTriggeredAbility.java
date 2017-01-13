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

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class EntersBattlefieldAllTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    protected String rule;
    protected boolean controlledText;
    protected SetTargetPointer setTargetPointer;

    /**
     * zone = BATTLEFIELD optional = false
     *
     * @param effect
     * @param filter
     */
    public EntersBattlefieldAllTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter, false);
    }

    public EntersBattlefieldAllTriggeredAbility(Effect effect, FilterPermanent filter, String rule) {
        this(Zone.BATTLEFIELD, effect, filter, false, rule);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE, null, false);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, String rule) {
        this(zone, effect, filter, optional, rule, false);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, String rule, boolean controlledText) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE, rule, controlledText);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, String rule) {
        this(zone, effect, filter, optional, setTargetPointer, rule, false);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, String rule, boolean controlledText) {
        super(zone, effect, optional);
        this.filter = filter;
        this.rule = rule;
        this.controlledText = controlledText;
        this.setTargetPointer = setTargetPointer;
    }

    public EntersBattlefieldAllTriggeredAbility(final EntersBattlefieldAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.rule = ability.rule;
        this.controlledText = ability.controlledText;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game)) {
            if (!setTargetPointer.equals(SetTargetPointer.NONE)) {
                for (Effect effect : this.getEffects()) {
                    switch (setTargetPointer) {
                        case PERMANENT:
                            effect.setTargetPointer(new FixedTarget(permanent, game));
                            break;
                        case PLAYER:
                            effect.setTargetPointer(new FixedTarget(permanent.getControllerId()));
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
        StringBuilder sb = new StringBuilder("Whenever ").append(filter.getMessage());
        sb.append(" enters the battlefield");
        if (controlledText) {
            sb.append(" under your control, ");
        } else {
            sb.append(", ");
        }
        sb.append(super.getRule());
        return sb.toString();
    }

    @Override
    public EntersBattlefieldAllTriggeredAbility copy() {
        return new EntersBattlefieldAllTriggeredAbility(this);
    }
}
