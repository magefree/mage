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

package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 *
 *
 * 702.82. Unearth
 *
 * 702.82a Unearth is an activated ability that functions while the card with unearth
 * is in a graveyard. "Unearth [cost]" means "[Cost]: Return this card from your graveyard
 * to the battlefield. It gains haste. Exile it at the beginning of the next end step.
 * If it would leave the battlefield, exile it instead of putting it anywhere else.
 * Activate this ability only any time you could cast a sorcery."
 *
 */
public class UnearthAbility extends ActivatedAbilityImpl<UnearthAbility> {

    public UnearthAbility(ManaCosts costs) {
        super(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(), costs);
        this.timing = TimingRule.SORCERY;
        this.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addEffect(new CreateDelayedTriggeredAbilityEffect(new UnearthDelayedTriggeredAbility()));
        this.addEffect(new UnearthLeavesBattlefieldEffect());
    }

    public UnearthAbility(final UnearthAbility ability) {
        super(ability);
    }

    @Override
    public UnearthAbility copy() {
        return new UnearthAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Unearth ").append(this.getManaCosts().getText());
        sb.append(" <i>(").append(this.getManaCosts().getText());
        sb.append(": Return this card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step or if it would leave the battlefield. Unearth only as a sorcery.)");
        return sb.toString();
    }

}

class UnearthDelayedTriggeredAbility extends DelayedTriggeredAbility<UnearthDelayedTriggeredAbility> {

    public UnearthDelayedTriggeredAbility() {
        super(new ExileSourceEffect());
    }

    public UnearthDelayedTriggeredAbility(final UnearthDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UnearthDelayedTriggeredAbility copy() {
        return new UnearthDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.END_TURN_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Exile {this} at the beginning of the next end step";
    }

}

class UnearthLeavesBattlefieldEffect extends ReplacementEffectImpl<UnearthLeavesBattlefieldEffect> {

    public UnearthLeavesBattlefieldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "When {this} leaves the battlefield, exile it";
    }

    public UnearthLeavesBattlefieldEffect(final UnearthLeavesBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public UnearthLeavesBattlefieldEffect copy() {
        return new UnearthLeavesBattlefieldEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() != Zone.EXILED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileSourceEffect effect = new ExileSourceEffect();
        return effect.apply(game, source);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }
}
