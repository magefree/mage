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
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DevourEffect;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class BloodsporeThrinax extends CardImpl {

    public BloodsporeThrinax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Devour 1
        this.addAbility(new DevourAbility(DevourEffect.DevourFactor.Devour1));
        
        // Each other creature you control enters the battlefield with an additional X +1/+1 counters on it, where X is the number of +1/+1 counters on Bloodspire Thrinax.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BloodsporeThrinaxEntersBattlefieldEffect()));
    }

    public BloodsporeThrinax(final BloodsporeThrinax card) {
        super(card);
    }

    @Override
    public BloodsporeThrinax copy() {
        return new BloodsporeThrinax(this);
    }
}

class BloodsporeThrinaxEntersBattlefieldEffect extends ReplacementEffectImpl {

    public BloodsporeThrinaxEntersBattlefieldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other creature you control enters the battlefield with an additional X +1/+1 counters on it, where X is the number of +1/+1 counters on {this}";
    }

    public BloodsporeThrinaxEntersBattlefieldEffect(BloodsporeThrinaxEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null && creature.getControllerId().equals(source.getControllerId())
                && creature.isCreature()
                && !event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (sourceCreature != null && creature != null) {
            int amount = sourceCreature.getCounters(game).getCount(CounterType.P1P1);
            if (amount > 0) {
                creature.addCounters(CounterType.P1P1.createInstance(amount), source, game);
            }
        }
        return false;
    }

    @Override
    public BloodsporeThrinaxEntersBattlefieldEffect copy() {
        return new BloodsporeThrinaxEntersBattlefieldEffect(this);
    }
}
