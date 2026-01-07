package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HovelHurler extends CardImpl {

    public HovelHurler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // This creature enters with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)),
            null, "This creature enters with two -1/-1 counters on it.", "")
        );

        // {R/W}{R/W}, Remove a counter from this creature: Another target creature you control gets +1/+0 and gains flying until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance()), new ManaCostsImpl<>("{R/W}{R/W}"));
        ability.addEffect(new BoostTargetEffect(1, 0));
        ability.addCost(new RemoveCountersSourceCost(1));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private HovelHurler(final HovelHurler card) {
        super(card);
    }

    @Override
    public HovelHurler copy() {
        return new HovelHurler(this);
    }
}
