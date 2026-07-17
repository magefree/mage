package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.abilities.keyword.SneakAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LeonardoCuttingEdge extends CardImpl {

    public LeonardoCuttingEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sneak {W}
        this.addAbility(new SneakAbility(this, "{W}"));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you gain life, put a +1/+1 counter on Leonardo.
        this.addAbility(new GainLifeControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private LeonardoCuttingEdge(final LeonardoCuttingEdge card) {
        super(card);
    }

    @Override
    public LeonardoCuttingEdge copy() {
        return new LeonardoCuttingEdge(this);
    }
}
