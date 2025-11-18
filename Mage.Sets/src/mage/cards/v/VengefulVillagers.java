package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VengefulVillagers extends CardImpl {

    public VengefulVillagers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever this creature attacks, choose target creature an opponent controls. Tap it, then you may sacrifice an artifact or creature. If you do, put a stun counter on the chosen creature.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect("choose target creature an opponent controls. Tap it"));
        ability.addEffect(new DoIfCostPaid(
                new AddCountersTargetEffect(CounterType.STUN.createInstance())
                        .setText("put a stun counter on the chosen creature"),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE)
        ).concatBy(", then"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private VengefulVillagers(final VengefulVillagers card) {
        super(card);
    }

    @Override
    public VengefulVillagers copy() {
        return new VengefulVillagers(this);
    }
}
