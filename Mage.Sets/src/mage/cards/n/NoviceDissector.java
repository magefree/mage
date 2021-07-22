package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NoviceDissector extends CardImpl {

    public NoviceDissector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}, Sacrifice another creature: Put a +1/+1 counter on target creature. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        )));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NoviceDissector(final NoviceDissector card) {
        super(card);
    }

    @Override
    public NoviceDissector copy() {
        return new NoviceDissector(this);
    }
}
