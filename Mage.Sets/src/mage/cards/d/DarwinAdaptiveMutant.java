package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class DarwinAdaptiveMutant extends CardImpl {

    public DarwinAdaptiveMutant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Evolve
        this.addAbility(new EvolveAbility());

        // Remove two +1/+1 counters from Darwin: He gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
            new RemoveCountersSourceCost(CounterType.P1P1.createInstance(2))
        ));
    }

    private DarwinAdaptiveMutant(final DarwinAdaptiveMutant card) {
        super(card);
    }

    @Override
    public DarwinAdaptiveMutant copy() {
        return new DarwinAdaptiveMutant(this);
    }
}
