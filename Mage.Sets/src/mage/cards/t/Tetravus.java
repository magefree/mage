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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.StaticAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.TetraviteToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author MarcoMarin
 */
public class Tetravus extends CardImpl {

    public Tetravus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add("Construct");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Tetravus enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "{this} enters the battlefield with three +1/+1 counters on it"));
        // At the beginning of your upkeep, you may remove any number of +1/+1 counters from Tetravus. If you do, create that many 1/1 colorless Tetravite artifact creature tokens. They each have flying and "This creature can't be enchanted."
        this.addAbility(new ConditionalTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(new CreateTokenEffect(new TetraviteToken()),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1))), TargetController.YOU, true),
                new SourceHasCounterCondition(CounterType.P1P1, 1), "At the beginning of your upkeep, you may remove any number of +1/+1 counters from Tetravus. If you do, create that many 1/1 colorless Tetravite artifact creature tokens. They each have flying and \"This creature can't be enchanted.\""));
        // At the beginning of your upkeep, you may exile any number of tokens created with Tetravus. If you do, put that many +1/+1 counters on Tetravus.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                new ExileTargetCost(new TargetControlledPermanent(new FilterControlledPermanent("Tetravite")))), TargetController.YOU, true));

    }

    public Tetravus(final Tetravus card) {
        super(card);
    }

    @Override
    public Tetravus copy() {
        return new Tetravus(this);
    }
}

class CantBeEnchantedAbility extends StaticAbility {

    public CantBeEnchantedAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public CantBeEnchantedAbility(final CantBeEnchantedAbility ability) {
        super(ability);
    }

    @Override
    public CantBeEnchantedAbility copy() {
        return new CantBeEnchantedAbility(this);
    }

    public boolean canTarget(MageObject source, Game game) {
        if (source.isEnchantment()
                && source.hasSubtype(SubType.AURA, game)) {
            return false;
        }
        return true;
    }

}
