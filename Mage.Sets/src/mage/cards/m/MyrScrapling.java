package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MyrScrapling extends CardImpl {

    public MyrScrapling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Myr Scrapling: Put a +1/+1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new SacrificeSourceCost()
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MyrScrapling(final MyrScrapling card) {
        super(card);
    }

    @Override
    public MyrScrapling copy() {
        return new MyrScrapling(this);
    }
}
