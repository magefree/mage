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
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class SlumberingDragon extends CardImpl {

    public SlumberingDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add("Dragon");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Slumbering Dragon can't attack or block unless it has five or more +1/+1 counters on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SlumberingDragonEffect()));

        // Whenever a creature attacks you or a planeswalker you control, put a +1/+1 counter on Slumbering Dragon.
        this.addAbility(new AttacksAllTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PERMANENT, true));
    }

    public SlumberingDragon(final SlumberingDragon card) {
        super(card);
    }

    @Override
    public SlumberingDragon copy() {
        return new SlumberingDragon(this);
    }
}

class SlumberingDragonEffect extends RestrictionEffect {

    public SlumberingDragonEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless it has five or more +1/+1 counters on it";
    }

    public SlumberingDragonEffect(final SlumberingDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            if (permanent.getCounters(game).getCount(CounterType.P1P1) >= 5) {
                return false;
            }
            return true;
        }
        // don't apply for all other creatures!
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public SlumberingDragonEffect copy() {
        return new SlumberingDragonEffect(this);
    }
}
