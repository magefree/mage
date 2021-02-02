
package mage.cards.h;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author daagar
 */
public final class HolyLight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Nonwhite creatures");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }

    public HolyLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Non white creatures get -1/-1 until end of turn.
        getSpellAbility().addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false));

    }

    private HolyLight(final HolyLight card) {
        super(card);
    }

    @Override
    public HolyLight copy() {
        return new HolyLight(this);
    }
}
