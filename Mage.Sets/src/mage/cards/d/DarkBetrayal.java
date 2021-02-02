
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DarkBetrayal extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creature");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public DarkBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Destroy target black creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Target target = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(target);
    }

    private DarkBetrayal(final DarkBetrayal card) {
        super(card);
    }

    @Override
    public DarkBetrayal copy() {
        return new DarkBetrayal(this);
    }
}
