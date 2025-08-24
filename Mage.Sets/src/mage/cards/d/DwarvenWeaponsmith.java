package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class DwarvenWeaponsmith extends CardImpl {

    public DwarvenWeaponsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Sacrifice an artifact: Put a +1/+1 counter on target creature. Activate this ability only during your upkeep.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new TapSourceCost(), IsStepCondition.getMyUpkeep()
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DwarvenWeaponsmith(final DwarvenWeaponsmith card) {
        super(card);
    }

    @Override
    public DwarvenWeaponsmith copy() {
        return new DwarvenWeaponsmith(this);
    }
}
