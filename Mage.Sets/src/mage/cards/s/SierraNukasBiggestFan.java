package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Cguy7777
 */
public final class SierraNukasBiggestFan extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.QUEST);

    public SierraNukasBiggestFan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // The Nuka-Cola Challenge -- Whenever one or more creatures you control deal combat damage to a player,
        // put a quest counter on Sierra, Nuka's Biggest Fan and create a Food token.
        Ability ability = new DealCombatDamageControlledTriggeredAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()));
        ability.addEffect(new CreateTokenEffect(new FoodToken()).concatBy("and"));
        this.addAbility(ability.withFlavorWord("The Nuka-Cola Challenge"));

        // Whenever you sacrifice a Food, target creature you control gets +X/+X until end of turn,
        // where X is the number of quest counters on Sierra.
        Ability ability2 = new SacrificePermanentTriggeredAbility(
                new BoostTargetEffect(xValue, xValue)
                        .setText("target creature you control gets +X/+X until end of turn, where X is the number of quest counters on {this}"),
                StaticFilters.FILTER_CONTROLLED_FOOD);
        ability2.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability2);
    }

    private SierraNukasBiggestFan(final SierraNukasBiggestFan card) {
        super(card);
    }

    @Override
    public SierraNukasBiggestFan copy() {
        return new SierraNukasBiggestFan(this);
    }
}
