package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThunderingMightmare extends CardImpl {

    public ThunderingMightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Thundering Mightmare is paired with another creature, each of those creatures has "Whenever an opponent casts a spell, put a +1/+1 counter on this creature."
        this.addAbility(new SimpleStaticAbility(new GainAbilityPairedEffect(new SpellCastOpponentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on this creature"),
                false
        ), "As long as {this} is paired with another creature, each of those creatures " +
                "has \"Whenever an opponent casts a spell, put a +1/+1 counter on this creature.\"")));
    }

    private ThunderingMightmare(final ThunderingMightmare card) {
        super(card);
    }

    @Override
    public ThunderingMightmare copy() {
        return new ThunderingMightmare(this);
    }
}
