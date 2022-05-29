package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;

/**
 * @author noxx
 */
public final class BloodflowConnoisseur extends CardImpl {

    public BloodflowConnoisseur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice a creature: Put a +1/+1 counter on Bloodflow Connoisseur.
        Cost abilityCost = new SacrificeTargetCost(
                new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        );
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                abilityCost
        );
        this.addAbility(ability);
    }

    private BloodflowConnoisseur(final BloodflowConnoisseur card) {
        super(card);
    }

    @Override
    public BloodflowConnoisseur copy() {
        return new BloodflowConnoisseur(this);
    }
}
