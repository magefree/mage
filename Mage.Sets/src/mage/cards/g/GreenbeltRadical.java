package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreenbeltRadical extends CardImpl {

    public GreenbeltRadical(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Disguise {5}{G}{G}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{5}{G}{G}")));

        // When Greenbelt Radical is turned face up, put a +1/+1 counter on each creature you control. Creatures you control gain trample until end of turn.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        this.addAbility(ability);
    }

    private GreenbeltRadical(final GreenbeltRadical card) {
        super(card);
    }

    @Override
    public GreenbeltRadical copy() {
        return new GreenbeltRadical(this);
    }
}
