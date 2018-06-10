
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class VineshaperMystic extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Merfolk you control");

    static {
        filter.add(new SubtypePredicate(SubType.MERFOLK));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public VineshaperMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Vineshaper Mystic enters the battlefield, put a +1/+1 counter on each of up to two target Merfolk you control.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("put a +1/+1 counter on each of up to two target Merfolk you control");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetPermanent(0, 2, filter, false));
        this.addAbility(ability);
    }

    public VineshaperMystic(final VineshaperMystic card) {
        super(card);
    }

    @Override
    public VineshaperMystic copy() {
        return new VineshaperMystic(this);
    }
}
