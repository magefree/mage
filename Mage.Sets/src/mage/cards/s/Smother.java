
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class Smother extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public Smother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");


        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
    }

    private Smother(final Smother card) {
        super(card);
    }

    @Override
    public Smother copy() {
        return new Smother(this);
    }
}
