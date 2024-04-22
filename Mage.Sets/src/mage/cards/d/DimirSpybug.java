package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SurveilTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class DimirSpybug extends CardImpl {

    public DimirSpybug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever you surveil, put a +1/+1 counter on Dimir Spybug.
        this.addAbility(new SurveilTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private DimirSpybug(final DimirSpybug card) {
        super(card);
    }

    @Override
    public DimirSpybug copy() {
        return new DimirSpybug(this);
    }
}
