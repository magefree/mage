package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class LeadBellyChimera extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Chimera creature");

    static {
        filter.add(SubType.CHIMERA.getPredicate());
    }

    public LeadBellyChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Sacrifice Lead-Belly Chimera: Put a +2/+2 counter on target Chimera creature. It gains trample.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P2P2.createInstance()), new SacrificeSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield)
                .setText("It gains trample. <i>(This effect lasts indefinitely.)</i>"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        addAbility(ability);
    }

    private LeadBellyChimera(final LeadBellyChimera card) {
        super(card);
    }

    @Override
    public LeadBellyChimera copy() {
        return new LeadBellyChimera(this);
    }
}
