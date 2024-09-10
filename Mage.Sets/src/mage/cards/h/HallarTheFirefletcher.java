package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HallarTheFirefletcher extends CardImpl {

    public HallarTheFirefletcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast a spell, if that spell was kicked, put a +1/+1 counter on Hallar, the Firefletcher, then Hallar deals damage equal to the number of +1/+1 counters on it to each opponent.
        TriggeredAbility ability = new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on {this}"),
                StaticFilters.FILTER_SPELL_KICKED_A, false
        );
        ability.addEffect(new DamagePlayersEffect(Outcome.Benefit, new CountersSourceCount(CounterType.P1P1), TargetController.OPPONENT)
                .setText(", then {this} deals damage equal to the number of +1/+1 counters on it to each opponent")
        );
        ability.setTriggerPhrase("Whenever you cast a spell, if that spell was kicked, ");
        this.addAbility(ability);
    }

    private HallarTheFirefletcher(final HallarTheFirefletcher card) {
        super(card);
    }

    @Override
    public HallarTheFirefletcher copy() {
        return new HallarTheFirefletcher(this);
    }
}
