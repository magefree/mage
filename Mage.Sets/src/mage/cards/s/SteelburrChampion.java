package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.OffspringAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteelburrChampion extends CardImpl {

    public SteelburrChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Offspring {1}{W}
        this.addAbility(new OffspringAbility("{1}{W}"));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever an opponent casts a noncreature spell, put a +1/+1 counter on this creature.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on this creature"),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private SteelburrChampion(final SteelburrChampion card) {
        super(card);
    }

    @Override
    public SteelburrChampion copy() {
        return new SteelburrChampion(this);
    }
}
