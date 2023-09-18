package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 * @author LevelX2
 */
public final class BlademaneBaku extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(RemovedCountersForCostValue.instance, 2);

    public BlademaneBaku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        
        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Blademane Baku.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));

        // {1}, Remove X ki counters from Blademane Baku: For each counter removed, Blademane Baku gets +2/+0 until end of turn.
        Effect effect = new BoostSourceEffect(xValue, StaticValue.get(0), Duration.EndOfTurn);
        effect.setText("for each counter removed, {this} gets +2/+0 until end of turn");
        Ability ability = new SimpleActivatedAbility(effect, new GenericManaCost(1));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.KI));
        this.addAbility(ability);
    }

    private BlademaneBaku(final BlademaneBaku card) {
        super(card);
    }

    @Override
    public BlademaneBaku copy() {
        return new BlademaneBaku(this);
    }
}
