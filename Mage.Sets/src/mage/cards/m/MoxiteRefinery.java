package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoxiteRefinery extends CardImpl {

    public MoxiteRefinery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, {T}, Remove X counters from an artifact or creature you control: Choose one. Activate only as a sorcery.
        // * Put X charge counters on target artifact.
        Ability ability = new SimpleActivatedAbility(new AddCountersTargetEffect(
                CounterType.CHARGE.createInstance(), GetXValue.instance
        ), new GenericManaCost(2)).setTiming(TimingRule.SORCERY);
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveVariableCountersTargetCost(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE,
                null, "X", 0, true, null
        ));
        ability.addTarget(new TargetArtifactPermanent());
        ability.getModes().setChooseText("choose one. Activate only as a sorcery.");

        // * Put X +1/+1 counters on target creature.
        ability.addMode(new Mode(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(), GetXValue.instance
        )).addTarget(new TargetCreaturePermanent()));
        this.addAbility(ability);
    }

    private MoxiteRefinery(final MoxiteRefinery card) {
        super(card);
    }

    @Override
    public MoxiteRefinery copy() {
        return new MoxiteRefinery(this);
    }
}
