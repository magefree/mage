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
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author TheElk801
 */
public class LiciaSanguineTribune extends CardImpl {

    public LiciaSanguineTribune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{W}{B}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Vampire");
        this.subtype.add("Soldier");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Licia, Sanguine Tribune costs 1 less to cast for each 1 life you gained this turn.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new LiciaSanguineTribuneCostReductionEffect()), new PlayerGainedLifeWatcher());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Pay 5 life: Put three +1/+1 counters on Licia. Activate this ability only on your turn and only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)), new PayLifeCost(5), 1, MyTurnCondition.instance));
    }

    public LiciaSanguineTribune(final LiciaSanguineTribune card) {
        super(card);
    }

    @Override
    public LiciaSanguineTribune copy() {
        return new LiciaSanguineTribune(this);
    }
}

class LiciaSanguineTribuneCostReductionEffect extends CostModificationEffectImpl {

    LiciaSanguineTribuneCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "{this} costs {1} less to cast for each 1 life you have gained this turn.";
    }

    LiciaSanguineTribuneCostReductionEffect(LiciaSanguineTribuneCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            DynamicValue reductionAmount = new YouGainLifeCount();
            CardUtil.reduceCost(abilityToModify, reductionAmount.calculate(game, source, this));
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public LiciaSanguineTribuneCostReductionEffect copy() {
        return new LiciaSanguineTribuneCostReductionEffect(this);
    }
}

class YouGainLifeCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return this.calculate(game, sourceAbility.getControllerId());
    }

    public int calculate(Game game, UUID controllerId) {
        PlayerGainedLifeWatcher watcher = (PlayerGainedLifeWatcher) game.getState().getWatchers().get(PlayerGainedLifeWatcher.class.getSimpleName());
        if (watcher != null) {
            return watcher.getLiveGained(controllerId);
        }
        return 0;
    }

    @Override
    public YouGainLifeCount copy() {
        return new YouGainLifeCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the total life you gained this turn";
    }
}
