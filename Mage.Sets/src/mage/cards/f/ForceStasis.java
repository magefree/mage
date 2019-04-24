package mage.cards.f;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class ForceStasis extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
        filter.add(new CardTypePredicate(CardType.INSTANT));
        filter.add(new CardTypePredicate(CardType.SORCERY));
    }

    public ForceStasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");
        

        // Choose one -
        //   Tap target creature. It doesn't untap during its controller's next untap step.
        getSpellAbility().addEffect(new TapTargetEffect());
        getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect()
                .setText("It doesn't untap during its controller's next untap step"));
        getSpellAbility().addTarget(new TargetCreaturePermanent());

        //   Return target instant or sorcery spell you don't control to its owner's hand.
        Mode mode = new Mode();
        mode.getEffects().add(new ReturnToHandTargetEffect());
        mode.getTargets().add(new TargetSpell(filter));
        this.getSpellAbility().addMode(mode);
    }

    public ForceStasis(final ForceStasis card) {
        super(card);
    }

    @Override
    public ForceStasis copy() {
        return new ForceStasis(this);
    }
}
