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
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class EzuriClawOfProgress extends CardImpl {
    
    final private static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    final private static FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent();
    
    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filter2.add(new AnotherPredicate());
    }
    
    String rule = "Whenever a creature with power 2 or less enters the battlefield under your control, you get an experience counter.";

    public EzuriClawOfProgress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Elf");
        this.subtype.add("Warrior");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature with power 2 or less enters the battlefield under your control, you get an experience counter.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new AddCountersControllerEffect(
                CounterType.EXPERIENCE.createInstance(1), false), filter, false, rule, true));
        
        // At the beginning of combat on your turn, put X +1/+1 counters on another target creature you control, where X is the number of experience counters you have.
        Ability ability = new BeginningOfCombatTriggeredAbility(new EzuriClawOfProgressEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetControlledCreaturePermanent(filter2));
        this.addAbility(ability);
    }

    public EzuriClawOfProgress(final EzuriClawOfProgress card) {
        super(card);
    }

    @Override
    public EzuriClawOfProgress copy() {
        return new EzuriClawOfProgress(this);
    }
}

class EzuriClawOfProgressEffect extends OneShotEffect {

    public EzuriClawOfProgressEffect() {
        super(Outcome.Benefit);
        this.staticText = "put X +1/+1 counters on another target creature you control, where X is the number of experience counters you have";
    }

    public EzuriClawOfProgressEffect(final EzuriClawOfProgressEffect effect) {
        super(effect);
    }

    @Override
    public EzuriClawOfProgressEffect copy() {
        return new EzuriClawOfProgressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = controller.getCounters().getCount(CounterType.EXPERIENCE);
            target.addCounters(CounterType.P1P1.createInstance(amount), source, game);
        }
        return false;
    }
}