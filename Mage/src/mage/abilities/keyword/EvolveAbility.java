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

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * FAQ 2013/01/11
 *
 * 702.98. Evolve
 * 
 * 702.98a Evolve is a triggered ability. "Evolve" means "Whenever a creature enters
 * the battlefield under your control, if that creature's power is greater than this
 * creature's power and/or that creature's toughness is greater than this creature's
 * toughness, put a +1/+1 counter on this creature."
 *
 * 702.98b If a creature has multiple instances of evolve, each triggers separately
 * 
 * @author LevelX2
 */


public class EvolveAbility extends TriggeredAbilityImpl<EvolveAbility> {

    public EvolveAbility() {
        super(Zone.BATTLEFIELD, new EvolveEffect());
    }

    public EvolveAbility(EvolveAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            Permanent triggeringCreature = game.getPermanent(event.getTargetId());
            if (((ZoneChangeEvent) event).getToZone() == Zone.BATTLEFIELD
                    && triggeringCreature != null
                    && triggeringCreature.getCardType().contains(CardType.CREATURE)
                    && triggeringCreature.getControllerId().equals(this.controllerId)) {
                Permanent sourceCreature = game.getPermanent(sourceId);
                if (sourceCreature != null && isPowerOrThoughnessGreater(sourceCreature, triggeringCreature)) {
                    this.getEffects().get(0).setValue("triggeringCreature", event.getTargetId());
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return true;
    }

    public static boolean isPowerOrThoughnessGreater(Permanent sourceCreature, Permanent newCreature) {
        if (newCreature.getPower().getValue() > sourceCreature.getPower().getValue()) {
            return true;
        }
        if (newCreature.getToughness().getValue() > sourceCreature.getToughness().getValue()) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Evolve <i>(Whenever a creature enters the battlefield under your control, if that creature has greater power or toughness than this creature, put a +1/+1 counter on this creature.)</i>";
    }

    @Override
    public EvolveAbility copy() {
        return new EvolveAbility(this);
    }
}

class EvolveEffect extends OneShotEffect<EvolveEffect> {

    public EvolveEffect() {
        super(Outcome.BoostCreature);
    }

    public EvolveEffect(final EvolveEffect effect) {
        super(effect);
    }

    @Override
    public EvolveEffect copy() {
        return new EvolveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID triggeringCreatureId = (UUID) getValue("triggeringCreature");
        Permanent triggeringCreature = game.getPermanent(triggeringCreatureId);
        if (triggeringCreature != null) {
            Permanent sourceCreature = game.getPermanent(source.getSourceId());
            if (sourceCreature != null && EvolveAbility.isPowerOrThoughnessGreater(sourceCreature, triggeringCreature)) {
                sourceCreature.addCounters(CounterType.P1P1.createInstance(), game);
                return true;
            }
        }
        return false;
    }
}

