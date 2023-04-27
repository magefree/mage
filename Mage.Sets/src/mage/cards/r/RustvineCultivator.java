package mage.cards.r;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetLandPermanent;

/**
 * @author TheElk801
 */
public final class RustvineCultivator extends CardImpl {

    public RustvineCultivator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Put an oil counter on Rustvine Cultivator.
        this.addAbility(new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.OIL.createInstance()), new TapSourceCost()));

        // {T}, Remove an oil counter from Rustvine Cultivator: Untap target land.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.OIL.createInstance()));
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private RustvineCultivator(final RustvineCultivator card) {
        super(card);
    }

    @Override
    public RustvineCultivator copy() {
        return new RustvineCultivator(this);
    }
}
