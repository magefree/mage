package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunstarLightsmith extends CardImpl {

    public SunstarLightsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast your second spell each turn, put a +1/+1 counter on this creature and draw a card.
        Ability ability = new CastSecondSpellTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private SunstarLightsmith(final SunstarLightsmith card) {
        super(card);
    }

    @Override
    public SunstarLightsmith copy() {
        return new SunstarLightsmith(this);
    }
}
