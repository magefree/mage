package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class AjanisPridemate extends CardImpl {

    public AjanisPridemate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you gain life, put a +1/+1 counter on Ajani's Pridemate.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));
    }

    private AjanisPridemate(final AjanisPridemate card) {
        super(card);
    }

    @Override
    public AjanisPridemate copy() {
        return new AjanisPridemate(this);
    }

}
