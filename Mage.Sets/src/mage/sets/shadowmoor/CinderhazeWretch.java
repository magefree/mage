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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 *
 */
public class CinderhazeWretch extends CardImpl<CinderhazeWretch> {

    public CinderhazeWretch(UUID ownerId) {
        super(ownerId, 60, "Cinderhaze Wretch", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Elemental");
        this.subtype.add("Shaman");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {tap}: Target player discards a card. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new TapSourceCost(), MyTurnCondition.getInstance());
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);

        // Put a -1/-1 counter on Cinderhaze Wretch: Untap Cinderhaze Wretch.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new PutCounterSourceCost(CounterType.M1M1.createInstance(1))));

    }

    public CinderhazeWretch(final CinderhazeWretch card) {
        super(card);
    }

    @Override
    public CinderhazeWretch copy() {
        return new CinderhazeWretch(this);
    }
}

class PutCounterSourceCost extends CostImpl<PutCounterSourceCost> {

    private int amount;
    private String name;
    private Counter counter;

    public PutCounterSourceCost(Counter counter) {
        this.counter = counter.copy();
        this.amount = counter.getCount();
        this.name = counter.getName();
        this.text = new StringBuilder("Put ").append((amount == 1 ? "a" : CardUtil.numberToText(amount)))
                .append(" ").append(name).append(" counter").append((amount != 1 ? "s" : ""))
                .append(" on {this}").toString();

    }

    public PutCounterSourceCost(PutCounterSourceCost cost) {
        super(cost);
        this.counter = cost.counter;
        this.amount = cost.amount;
        this.name = cost.name;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            permanent.addCounters(counter, game);
            this.paid = true;
        }
        return paid;
    }

    @Override
    public PutCounterSourceCost copy() {
        return new PutCounterSourceCost(this);
    }
}
