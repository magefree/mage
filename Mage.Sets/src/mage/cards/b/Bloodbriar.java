
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SacrificeAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class Bloodbriar extends CardImpl {

    private final static FilterPermanent filter = new FilterPermanent("another permanent");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
    }

    public Bloodbriar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.PLANT, SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you sacrifice another permanent, put a +1/+1 counter on Bloodbriar.
        this.addAbility(new SacrificeAllTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, TargetController.YOU, false));
    }

    public Bloodbriar(final Bloodbriar card) {
        super(card);
    }

    @Override
    public Bloodbriar copy() {
        return new Bloodbriar(this);
    }
}
