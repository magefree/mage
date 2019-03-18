
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author Plopman
 */
public final class CrucibleOfFire extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dragon creatures");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }
    
    public CrucibleOfFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");


        // Dragon creatures you control get +3/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(3, 3, Duration.WhileOnBattlefield, filter, false)));
    }

    public CrucibleOfFire(final CrucibleOfFire card) {
        super(card);
    }

    @Override
    public CrucibleOfFire copy() {
        return new CrucibleOfFire(this);
    }
}
