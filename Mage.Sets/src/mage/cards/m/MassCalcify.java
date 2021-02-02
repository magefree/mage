
package mage.cards.m;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public final class MassCalcify extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonwhite creatures");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }

    public MassCalcify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{W}{W}");


        // Destroy all nonwhite creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private MassCalcify(final MassCalcify card) {
        super(card);
    }

    @Override
    public MassCalcify copy() {
        return new MassCalcify(this);
    }
}
