
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterOpponentsCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ArchfiendOfIfnir extends CardImpl {

    public ArchfiendOfIfnir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cycle or discard another card, put a -1/-1 counter on each creature your opponents control.
        this.addAbility(new CycleOrDiscardControllerTriggeredAbility(
                new AddCountersAllEffect(
                        CounterType.M1M1.createInstance(1),
                        new FilterOpponentsCreaturePermanent("creature your opponents control")
                )
        ));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ArchfiendOfIfnir(final ArchfiendOfIfnir card) {
        super(card);
    }

    @Override
    public ArchfiendOfIfnir copy() {
        return new ArchfiendOfIfnir(this);
    }
}
