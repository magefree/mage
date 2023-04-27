package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class VogarNecropolisTyrant extends CardImpl {

    public VogarNecropolisTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE, SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever another creature dies during your turn, put a +1/+1 counter on Vogar, Necropolis Tyrant.
        this.addAbility(new ConditionalTriggeredAbility(
                new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true),
                MyTurnCondition.instance, "Whenever another creature dies during your turn, put a +1/+1 counter on {this}."
        ).addHint(MyTurnHint.instance));

        // When Vogar dies, draw a card for each +1/+1 counter on it.
        this.addAbility(new DiesSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(new CountersSourceCount(CounterType.P1P1))
                        .setText("draw a card for each +1/+1 counter on it"), false
        ));
    }

    private VogarNecropolisTyrant(final VogarNecropolisTyrant card) {
        super(card);
    }

    @Override
    public VogarNecropolisTyrant copy() {
        return new VogarNecropolisTyrant(this);
    }
}
