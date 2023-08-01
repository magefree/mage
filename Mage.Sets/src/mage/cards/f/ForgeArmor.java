
package mage.cards.f;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostConvertedMana;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class ForgeArmor extends CardImpl {

    public ForgeArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // As an additional cost to cast Forge Armor, sacrifice an artifact.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN)));
        // Put X +1/+1 counters on target creature, where X is the sacrificed artifact's converted mana cost.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(), new SacrificeCostConvertedMana("artifact")));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ForgeArmor(final ForgeArmor card) {
        super(card);
    }

    @Override
    public ForgeArmor copy() {
        return new ForgeArmor(this);
    }
}
