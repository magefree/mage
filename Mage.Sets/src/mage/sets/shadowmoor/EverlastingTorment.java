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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class EverlastingTorment extends CardImpl {

    public EverlastingTorment(UUID ownerId) {
        super(ownerId, 186, "Everlasting Torment", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{B/R}");
        this.expansionSetCode = "SHM";

        // Players can't gain life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantGainLifeAllEffect()));

        // Damage can't be prevented.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DamageCantBePreventedEffect(Duration.WhileOnBattlefield)));

        // All damage is dealt as though its source had wither.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DamageDealtAsIfSourceHadWitherEffect()));

    }

    public EverlastingTorment(final EverlastingTorment card) {
        super(card);
    }

    @Override
    public EverlastingTorment copy() {
        return new EverlastingTorment(this);
    }
}

class DamageCantBePreventedEffect extends ContinuousRuleModifyingEffectImpl {

    public DamageCantBePreventedEffect(Duration duration) {
        super(duration, Outcome.Benefit);
        staticText = "Damage can't be prevented";
    }

    public DamageCantBePreventedEffect(final DamageCantBePreventedEffect effect) {
        super(effect);
    }

    @Override
    public DamageCantBePreventedEffect copy() {
        return new DamageCantBePreventedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.PREVENT_DAMAGE);
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}

class DamageDealtAsIfSourceHadWitherEffect extends ReplacementEffectImpl {

    public DamageDealtAsIfSourceHadWitherEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "All damage is dealt as though its source had wither";
    }

    public DamageDealtAsIfSourceHadWitherEffect(final DamageDealtAsIfSourceHadWitherEffect effect) {
        super(effect);
    }

    @Override
    public DamageDealtAsIfSourceHadWitherEffect copy() {
        return new DamageDealtAsIfSourceHadWitherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damageAmount = event.getAmount();
        if (damageAmount > 0) {
            Counter counter = CounterType.M1M1.createInstance(damageAmount);
            Permanent creatureDamaged = game.getPermanent(event.getTargetId());
            if (creatureDamaged != null) {
                creatureDamaged.addCounters(counter, game);
            }
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_CREATURE;
    }

    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}
