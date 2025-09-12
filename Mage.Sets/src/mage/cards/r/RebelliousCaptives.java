package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RebelliousCaptives extends CardImpl {

    public RebelliousCaptives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Exhaust -- {6}: Put two +1/+1 counters on this creature, then earthbend 2.
        Ability ability = new ExhaustAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new GenericManaCost(6)
        );
        ability.addEffect(new EarthbendTargetEffect(2).concatBy(", then"));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);
    }

    private RebelliousCaptives(final RebelliousCaptives card) {
        super(card);
    }

    @Override
    public RebelliousCaptives copy() {
        return new RebelliousCaptives(this);
    }
}
