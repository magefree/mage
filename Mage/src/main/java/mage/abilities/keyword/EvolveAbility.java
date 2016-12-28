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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * FAQ 2013/01/11
 *
 * 702.98. Evolve
 *
 * 702.98a Evolve is a triggered ability. "Evolve" means "Whenever a creature
 * enters the battlefield under your control, if that creature's power is
 * greater than this creature's power and/or that creature's toughness is
 * greater than this creature's toughness, put a +1/+1 counter on this
 * creature."
 *
 * 702.98b If a creature has multiple instances of evolve, each triggers
 * separately
 *
 * Rulings
 *
 * When comparing the stats of the two creatures, you always compare power to
 * power and toughness to toughness. Whenever a creature enters the battlefield
 * under your control, check its power and toughness against the power and
 * toughness of the creature with evolve. If neither stat of the new creature is
 * greater, evolve won't trigger at all. For example, if you control a 2/3
 * creature with evolve and a 2/2 creature enters the battlefield under your
 * control, you won't have the opportunity to cast a spell like Giant Growth to
 * make the 2/2 creature large enough to cause evolve to trigger. If evolve
 * triggers, the stat comparison will happen again when the ability tries to
 * resolve. If neither stat of the new creature is greater, the ability will do
 * nothing. If the creature that entered the battlefield leaves the battlefield
 * before evolve tries to resolve, use its last known power and toughness to
 * compare the stats. If a creature enters the battlefield with +1/+1 counters
 * on it, consider those counters when determining if evolve will trigger. For
 * example, a 1/1 creature that enters the battlefield with two +1/+1 counters
 * on it will cause the evolve ability of a 2/2 creature to trigger. If multiple
 * creatures enter the battlefield at the same time, evolve may trigger multiple
 * times, although the stat comparison will take place each time one of those
 * abilities tries to resolve. For example, if you control a 2/2 creature with
 * evolve and two 3/3 creatures enter the battlefield, evolve will trigger
 * twice. The first ability will resolve and put a +1/+1 counter on the creature
 * with evolve. When the second ability tries to resolve, neither the power nor
 * the toughness of the new creature is greater than that of the creature with
 * evolve, so that ability does nothing. When comparing the stats as the evolve
 * ability resolves, it's possible that the stat that's greater changes from
 * power to toughness or vice versa. If this happens, the ability will still
 * resolve and you'll put a +1/+1 counter on the creature with evolve. For
 * example, if you control a 2/2 creature with evolve and a 1/3 creature enters
 * the battlefield under your control, it toughness is greater so evolve will
 * trigger. In response, the 1/3 creature gets +2/-2. When the evolve trigger
 * tries to resolve, its power is greater. You'll put a +1/+1 counter on the
 * creature with evolve.
 *
 * @author LevelX2
 */
public class EvolveAbility extends TriggeredAbilityImpl {

    public EvolveAbility() {
        super(Zone.BATTLEFIELD, new EvolveEffect());
    }

    public EvolveAbility(EvolveAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            Permanent triggeringCreature = game.getPermanent(event.getTargetId());
            if (triggeringCreature != null
                    && triggeringCreature.getCardType().contains(CardType.CREATURE)
                    && triggeringCreature.getControllerId().equals(this.controllerId)) {
                Permanent sourceCreature = game.getPermanent(sourceId);
                if (sourceCreature != null && isPowerOrThoughnessGreater(sourceCreature, triggeringCreature)) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
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
        return newCreature.getToughness().getValue() > sourceCreature.getToughness().getValue();
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

class EvolveEffect extends OneShotEffect {

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
        Permanent triggeringCreature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (triggeringCreature != null) {
            Permanent sourceCreature = game.getPermanent(source.getSourceId());
            if (sourceCreature != null && EvolveAbility.isPowerOrThoughnessGreater(sourceCreature, triggeringCreature)) {
                sourceCreature.addCounters(CounterType.P1P1.createInstance(), source, game);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.EVOLVED_CREATURE, sourceCreature.getId(), source.getSourceId(), source.getControllerId()));
            }
            return true;
        }
        return false;
    }
}
