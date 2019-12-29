package mage.cards.d;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadOfWinter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterControlledPermanent();

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.SNOW)));
        filter2.add(new SupertypePredicate(SuperType.SNOW));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2, -1);

    public DeadOfWinter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // All nonsnow creatures get -X/-X until end of turn, where X is the number of snow permanents you control.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                xValue, xValue, Duration.EndOfTurn, filter, false,
                "All nonsnow creatures get -X/-X until end of turn," +
                        " where X is the number of snow permanents you control.", true
        ));
    }

    private DeadOfWinter(final DeadOfWinter card) {
        super(card);
    }

    @Override
    public DeadOfWinter copy() {
        return new DeadOfWinter(this);
    }
}
