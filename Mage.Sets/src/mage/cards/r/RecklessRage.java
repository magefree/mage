
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 * @author JayDi85
 */
public final class RecklessRage extends CardImpl {

    public RecklessRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Reckless Rage deals 4 damage to target creature you don't control and 2 damage to target creature you control.
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DamageTargetEffect(4).setUseOnlyTargetPointer(true));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageTargetEffect(2).setUseOnlyTargetPointer(true)
                .setText("and 2 damage to target creature you control")
                .setTargetPointer(new SecondTargetPointer()));
    }

    public RecklessRage(final RecklessRage card) {
        super(card);
    }

    @Override
    public RecklessRage copy() {
        return new RecklessRage(this);
    }
}
