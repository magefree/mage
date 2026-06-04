package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AbominationTerrifyingTitan extends CardImpl {

    public AbominationTerrifyingTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Power-up -- {5}{R/G}{R/G}: Put a +1/+1 counter on Abomination. He fights up to one target creature an opponent controls.
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            new ManaCostsImpl<>("{5}{R/G}{R/G}")
        );
        ability.addEffect(new FightTargetsEffect()
            .setText("He fights up to one target creature an opponent controls"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE));
        this.addAbility(ability);
    }

    private AbominationTerrifyingTitan(final AbominationTerrifyingTitan card) {
        super(card);
    }

    @Override
    public AbominationTerrifyingTitan copy() {
        return new AbominationTerrifyingTitan(this);
    }
}
