
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author LoneFox
 */
public final class GuanYus1000LiMarch extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creatures");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public GuanYus1000LiMarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");

        // Destroy all tapped creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter, false));
    }

    private GuanYus1000LiMarch(final GuanYus1000LiMarch card) {
        super(card);
    }

    @Override
    public GuanYus1000LiMarch copy() {
        return new GuanYus1000LiMarch(this);
    }
}
