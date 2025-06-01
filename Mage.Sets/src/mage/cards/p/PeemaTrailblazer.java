package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author sobiech
 */
public final class PeemaTrailblazer extends CardImpl {

    public PeemaTrailblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever this creature deals combat damage to a player, you get that many {E}.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new GetEnergyCountersControllerEffect(SavedDamageValue.MANY)));

        // Exhaust -- Pay six {E}: Put two +1/+1 counters on this creature. Then draw cards equal to the greatest power among creatures you control.
        final Ability ability = new ExhaustAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new PayEnergyCost(6)
        );
        ability.addEffect(new DrawCardSourceControllerEffect(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES)
                .setText("Then draw cards equal to the greatest power among creatures you control"));
        ability.addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint());
        this.addAbility(ability);
    }

    private PeemaTrailblazer(final PeemaTrailblazer card) {
        super(card);
    }

    @Override
    public PeemaTrailblazer copy() {
        return new PeemaTrailblazer(this);
    }
}
