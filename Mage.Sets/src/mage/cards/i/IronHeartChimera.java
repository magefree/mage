package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
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
public final class IronHeartChimera extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Chimera creature");

    static {
        filter.add(SubType.CHIMERA.getPredicate());
    }

    public IronHeartChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Sacrifice Iron-Heart Chimera: Put a +2/+2 counter on target Chimera creature. It gains vigilance.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P2P2.createInstance()), new SacrificeSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield)
                .setText("It gains vigilance. <i>(This effect lasts indefinitely.)</i>"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        addAbility(ability);
    }

    private IronHeartChimera(final IronHeartChimera card) {
        super(card);
    }

    @Override
    public IronHeartChimera copy() {
        return new IronHeartChimera(this);
    }
}
