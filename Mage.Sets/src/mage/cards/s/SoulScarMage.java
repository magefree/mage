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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author stravant
 */
public class SoulScarMage extends CardImpl {

    public SoulScarMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        // If a source you control would deal noncombat damage to a creature an opponent controls, put that many -1/-1 counters on that creature instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SoulScarMageDamageReplacementEffect()));
    }

    public SoulScarMage(final SoulScarMage card) {
        super(card);
    }

    @Override
    public SoulScarMage copy() {
        return new SoulScarMage(this);
    }
}

class SoulScarMageDamageReplacementEffect extends ReplacementEffectImpl {

    public SoulScarMageDamageReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a source you control would deal noncombat damage to a creature an opponent controls, put that many -1/-1 counters on that creature instead.";
    }

    public SoulScarMageDamageReplacementEffect(final SoulScarMageDamageReplacementEffect effect) {
        super(effect);
    }

    @Override
    public SoulScarMageDamageReplacementEffect copy() {
        return new SoulScarMageDamageReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent toGetCounters = game.getPermanent(event.getTargetId());
        if (toGetCounters != null) {
            AddCountersTargetEffect addCounters = new AddCountersTargetEffect(CounterType.M1M1.createInstance(), new StaticValue(event.getAmount()));
            addCounters.setTargetPointer(new FixedTarget(toGetCounters.getId()));
            addCounters.apply(game, source);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CREATURE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        boolean weControlSource = game.getControllerId(event.getSourceId()).equals(source.getControllerId());
        boolean isNoncombatDamage = !((DamageCreatureEvent)event).isCombatDamage();
        return weControlSource && isNoncombatDamage;
    }
}
