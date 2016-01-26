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
package mage.sets.fallenempires;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LoneFox
 */
public class TidalInfluence extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public TidalInfluence(UUID ownerId) {
        super(ownerId, 57, "Tidal Influence", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "FEM";

        // Cast Tidal Influence only if no permanents named Tidal Influence are on the battlefield.
        this.getSpellAbility().addCost(new TidalInfluenceCost());
        // Tidal Influence enters the battlefield with a tide counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIDE.createInstance()),
            "with a tide counter on it."));
        // At the beginning of your upkeep, put a tide counter on Tidal Influence.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.TIDE.createInstance()),
            TargetController.YOU, false));
        // As long as there is exactly one tide counter on Tidal Influence, all blue creatures get -2/-0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostAllEffect(-2, -0, Duration.WhileOnBattlefield, filter, false),
            new SourceHasCounterCondition(CounterType.TIDE, 1, 1),
            "As long as there is exactly one tide counter on {this}, all blue creatures get -2/-0.")));
        // As long as there are exactly three tide counters on Tidal Influence, all blue creatures get +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostAllEffect(2, -0, Duration.WhileOnBattlefield, filter, false),
            new SourceHasCounterCondition(CounterType.TIDE, 3, 3),
            "As long as there are exactly three tide counter on {this}, all blue creatures get +2/-0.")));
        // Whenever there are four tide counters on Tidal Influence, remove all tide counters from it.
        this.addAbility(new TidalInfluenceTriggeredAbility(new RemoveAllCountersSourceEffect(CounterType.TIDE)));
    }

    public TidalInfluence(final TidalInfluence card) {
        super(card);
    }

    @Override
    public TidalInfluence copy() {
        return new TidalInfluence(this);
    }
}


class TidalInfluenceCost extends CostImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new NamePredicate("Tidal Influence"));
    }

    public TidalInfluenceCost() {
        this.text = "Cast Tidal Influence only if no permanents named Tidal Influence are on the battlefield";
    }

    public TidalInfluenceCost(final TidalInfluenceCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return !game.getBattlefield().contains(filter, 1, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        this.paid = true;
        return paid;
    }

    @Override
    public TidalInfluenceCost copy() {
        return new TidalInfluenceCost(this);
    }
}


class TidalInfluenceTriggeredAbility extends StateTriggeredAbility {

    public TidalInfluenceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public TidalInfluenceTriggeredAbility(final TidalInfluenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TidalInfluenceTriggeredAbility copy() {
        return new TidalInfluenceTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return new CountersCount(CounterType.TIDE).calculate(game, this, null) == 4;
    }

    @Override
    public String getRule() {
        return "Whenever there are four tide counters on {this}, " + super.getRule();
    }

}
