package mage.cards.a;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ATaleForTheAges extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("enchanted creatures");

    static {
        filter.add(EnchantedPredicate.instance);
    }

    public ATaleForTheAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Enchanted creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, filter
        )));
    }

    private ATaleForTheAges(final ATaleForTheAges card) {
        super(card);
    }

    @Override
    public ATaleForTheAges copy() {
        return new ATaleForTheAges(this);
    }
}
