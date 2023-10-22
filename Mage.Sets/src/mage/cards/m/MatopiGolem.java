package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.RegenerateSourceWithReflexiveEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MatopiGolem extends CardImpl {

    public MatopiGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}: Regenerate Matopi Golem. When it regenerates this way, put a -1/-1 counter on it.
        this.addAbility(new SimpleActivatedAbility(
                new RegenerateSourceWithReflexiveEffect(new ReflexiveTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.M1M1.createInstance()),
                        false
                ), false),
                new GenericManaCost(1)
        ));
    }

    private MatopiGolem(final MatopiGolem card) {
        super(card);
    }

    @Override
    public MatopiGolem copy() {
        return new MatopiGolem(this);
    }
}