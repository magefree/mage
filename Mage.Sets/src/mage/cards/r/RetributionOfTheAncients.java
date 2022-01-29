package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RetributionOfTheAncients extends CardImpl {

    public RetributionOfTheAncients(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // {B}, Remove X +1/+1 counters from among creatures you control: Target creature gets -X/-X until end of turn.
        DynamicValue xValue = new SignInversionDynamicValue(GetXValue.instance);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn, true), new ManaCostsImpl("{B}"));
        ability.addCost(new RemoveVariableCountersTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURES, CounterType.P1P1, "X", 0));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RetributionOfTheAncients(final RetributionOfTheAncients card) {
        super(card);
    }

    @Override
    public RetributionOfTheAncients copy() {
        return new RetributionOfTheAncients(this);
    }
}

