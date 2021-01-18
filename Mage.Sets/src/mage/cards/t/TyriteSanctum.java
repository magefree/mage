package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TyriteSanctum extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("legendary creature");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.GOD, "God");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public TyriteSanctum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: Target legendary creature becomes a God in addition to its other types. Put a +1/+1 counter on it.
        Ability ability = new SimpleActivatedAbility(new AddCardSubTypeTargetEffect(
                SubType.GOD, Duration.Custom
        ).setText("target legendary creature becomes a God in addition to its other types."), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()
        ).setText("Put a +1/+1 counter on it"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {4}, {T}, Sacrifice Tyrite Sanctum: Put an indestructible counter on target God.
        ability = new SimpleActivatedAbility(new AddCountersTargetEffect(
                CounterType.INDESTRUCTIBLE.createInstance()
        ), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private TyriteSanctum(final TyriteSanctum card) {
        super(card);
    }

    @Override
    public TyriteSanctum copy() {
        return new TyriteSanctum(this);
    }
}
