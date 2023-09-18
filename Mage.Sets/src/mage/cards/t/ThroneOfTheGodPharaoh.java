package mage.cards.t;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
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

import java.util.UUID;

/**
 * @author Styxo
 */
public final class ThroneOfTheGodPharaoh extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public ThroneOfTheGodPharaoh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);

        // At the beginning of your end step, each opponent loses life equal to the number of tapped creatures you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new LoseLifeOpponentsEffect(xValue).setText(
                        "each opponent loses life equal to the number of tapped creatures you control"
                ), TargetController.YOU, false
        ));
    }

    private ThroneOfTheGodPharaoh(final ThroneOfTheGodPharaoh card) {
        super(card);
    }

    @Override
    public ThroneOfTheGodPharaoh copy() {
        return new ThroneOfTheGodPharaoh(this);
    }
}
