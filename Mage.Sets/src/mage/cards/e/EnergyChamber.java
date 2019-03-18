
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class EnergyChamber extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("target artifact creature");
    private static final FilterPermanent filter2 = new FilterPermanent("noncreature artifact");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter2.add(new CardTypePredicate(CardType.ARTIFACT));
        filter2.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public EnergyChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // At the beginning of your upkeep, choose one - Put a +1/+1 counter on target artifact creature;
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance(), Outcome.BoostCreature), TargetController.YOU, false);
        ability.addTarget(new TargetPermanent(filter));

        // or put a charge counter on target noncreature artifact.
        Mode mode = new Mode();
        mode.addEffect(new AddCountersTargetEffect(CounterType.CHARGE.createInstance(), Outcome.BoostCreature));
        mode.addTarget(new TargetPermanent(filter2));
        ability.addMode(mode);

        this.addAbility(ability);

    }

    public EnergyChamber(final EnergyChamber card) {
        super(card);
    }

    @Override
    public EnergyChamber copy() {
        return new EnergyChamber(this);
    }
}
