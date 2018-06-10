
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;

/**
 * @author dustinroepsch
 */
public final class HeatStroke extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.or(new BlockedPredicate(), new BlockingPredicate()));
    }

    public HeatStroke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At end of combat, destroy each creature that blocked or was blocked this turn.
        this.addAbility(new EndOfCombatTriggeredAbility(new DestroyAllEffect(filter)
                .setText("destroy each creature that blocked or was blocked this turn"), false));
    }

    public HeatStroke(final HeatStroke card) {
        super(card);
    }

    @Override
    public HeatStroke copy() {
        return new HeatStroke(this);
    }
}
