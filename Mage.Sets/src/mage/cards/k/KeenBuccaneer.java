package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeenBuccaneer extends CardImpl {

    public KeenBuccaneer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.OCTOPUS);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Exhaust -- {1}{U}: Draw a card, then discard a card. Put a +1/+1 counter on this creature.
        Ability ability = new ExhaustAbility(
                new DrawDiscardControllerEffect(1, 1), new ManaCostsImpl<>("{1}{U}")
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
    }

    private KeenBuccaneer(final KeenBuccaneer card) {
        super(card);
    }

    @Override
    public KeenBuccaneer copy() {
        return new KeenBuccaneer(this);
    }
}
