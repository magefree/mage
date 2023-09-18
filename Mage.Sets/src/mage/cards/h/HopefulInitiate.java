package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrainingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class HopefulInitiate extends CardImpl {

    public HopefulInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Training
        this.addAbility(new TrainingAbility());

        // {2}{W}, Remove two +1/+1 counters from among creatures you control: Destroy target artifact or enchantment.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new RemoveCounterCost(
                new TargetControlledCreaturePermanent(1, 2, StaticFilters.FILTER_CONTROLLED_CREATURES, true),
                CounterType.P1P1, 2
        ).setText("Remove two +1/+1 counters from among creatures you control"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private HopefulInitiate(final HopefulInitiate card) {
        super(card);
    }

    @Override
    public HopefulInitiate copy() {
        return new HopefulInitiate(this);
    }
}
