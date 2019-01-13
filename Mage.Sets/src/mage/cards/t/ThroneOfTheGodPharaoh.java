
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author Styxo
 */
public final class ThroneOfTheGodPharaoh extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("tapped creature you control");

    static {
        filter.add(TappedPredicate.instance);
    }

    public ThroneOfTheGodPharaoh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        addSuperType(SuperType.LEGENDARY);

        // At the beginning of your end step, each opponent loses life equal to the number of tapped creatures you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new LoseLifeOpponentsEffect(new PermanentsOnBattlefieldCount(filter)), TargetController.YOU, false));
    }

    public ThroneOfTheGodPharaoh(final ThroneOfTheGodPharaoh card) {
        super(card);
    }

    @Override
    public ThroneOfTheGodPharaoh copy() {
        return new ThroneOfTheGodPharaoh(this);
    }
}
