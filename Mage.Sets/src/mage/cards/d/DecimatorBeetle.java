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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class DecimatorBeetle extends CardImpl {

    private final UUID originalId;

    public DecimatorBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.subtype.add("Insect");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Decimator Beetle enters the battlefield, put a -1/-1 counter on target creature you control.
        Effect effect = new AddCountersTargetEffect(CounterType.M1M1.createInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever Decimator Beetle attacks, remove a -1/-1 counter from target creature you control and put a -1/-1 counter on up to one target creature defending player controls.
        Ability ability2 = new AttacksTriggeredAbility(new DecimatorBeetleEffect(), false);
        ability2.addTarget(new TargetControlledCreaturePermanent());
        ability2.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature defending player controls")));
        this.addAbility(ability2);
        this.originalId = ability2.getOriginalId();
    }

    public DecimatorBeetle(final DecimatorBeetle card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            ability.getTargets().clear();
            ability.addTarget(new TargetControlledCreaturePermanent());
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
            UUID defenderId = game.getCombat().getDefenderId(ability.getSourceId());
            filter.add(new ControllerIdPredicate(defenderId));
            TargetCreaturePermanent target = new TargetCreaturePermanent(0, 1, filter, false);
            ability.addTarget(target);
        }
    }

    @Override
    public DecimatorBeetle copy() {
        return new DecimatorBeetle(this);
    }
}

class DecimatorBeetleEffect extends OneShotEffect {

    public DecimatorBeetleEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "remove a -1/-1 counter from target creature you control and put a -1/-1 counter on up to one target creature defending player controls";
    }

    public DecimatorBeetleEffect(DecimatorBeetleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null
                && targetCreature.getCounters(game).containsKey(CounterType.M1M1)) {
            Effect effect = new RemoveCounterTargetEffect(CounterType.M1M1.createInstance(1));
            effect.setTargetPointer(targetPointer);
            effect.apply(game, source);
        }
        targetCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (targetCreature != null) {
            Effect effect = new AddCountersTargetEffect(CounterType.M1M1.createInstance(1));
            effect.setTargetPointer(new FixedTarget(source.getTargets().get(1).getFirstTarget()));
            effect.apply(game, source);
        }

        return true;
    }

    @Override
    public DecimatorBeetleEffect copy() {
        return new DecimatorBeetleEffect(this);
    }
}
