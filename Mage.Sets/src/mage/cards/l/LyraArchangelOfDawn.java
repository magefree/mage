package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LyraArchangelOfDawn extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ANGEL);

    public LyraArchangelOfDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you gain life, put a +1/+1 counter on each Angel you control.
        this.addAbility(new GainLifeControllerTriggeredAbility(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        ));
    }

    private LyraArchangelOfDawn(final LyraArchangelOfDawn card) {
        super(card);
    }

    @Override
    public LyraArchangelOfDawn copy() {
        return new LyraArchangelOfDawn(this);
    }
}
