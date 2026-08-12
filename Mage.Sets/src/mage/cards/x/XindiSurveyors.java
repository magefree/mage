package mage.cards.x;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class XindiSurveyors extends CardImpl {

    public XindiSurveyors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.XINDI);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Landfall -- Whenever a land you control enters, put a +1/+1 counter on this creature.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private XindiSurveyors(final XindiSurveyors card) {
        super(card);
    }

    @Override
    public XindiSurveyors copy() {
        return new XindiSurveyors(this);
    }
}
