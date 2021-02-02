
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author L_J
 */
public final class DreadCharge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Black creatures you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("except by black creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter2.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DreadCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // Black creatures you control can't be blocked this turn except by black creatures.
        this.getSpellAbility().addEffect(new CantBeBlockedByCreaturesAllEffect(filter, filter2, Duration.EndOfTurn));
    }

    private DreadCharge(final DreadCharge card) {
        super(card);
    }

    @Override
    public DreadCharge copy() {
        return new DreadCharge(this);
    }
}
