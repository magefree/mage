package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AceFearlessRebel extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public AceFearlessRebel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Nitro-9 -- Whenever Ace, Fearless Rebel attacks, you may sacrifice an artifact. When you do, put a +1/+1 counter on Ace, Fearless Rebel, then it fights up to one target creature defending player controls.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), false), false
        );
        ability.addEffect(new FightTargetSourceEffect()
                .setText(", then it fights up to one target creature defending player controls"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
                ability,
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN),
                "Sacrifice an artifact?"
        )).withFlavorWord("Nitro-9"));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private AceFearlessRebel(final AceFearlessRebel card) {
        super(card);
    }

    @Override
    public AceFearlessRebel copy() {
        return new AceFearlessRebel(this);
    }
}
