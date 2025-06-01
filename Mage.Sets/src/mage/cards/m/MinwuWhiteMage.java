package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MinwuWhiteMage extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.CLERIC);

    public MinwuWhiteMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you gain life, put a +1/+1 counter on each Cleric you control.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        ));
    }

    private MinwuWhiteMage(final MinwuWhiteMage card) {
        super(card);
    }

    @Override
    public MinwuWhiteMage copy() {
        return new MinwuWhiteMage(this);
    }
}
