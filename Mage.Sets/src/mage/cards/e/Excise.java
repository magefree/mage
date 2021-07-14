
package mage.cards.e;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Excise extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creature");
    static {
        filter.add(AttackingPredicate.instance);
    }

    public Excise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{W}");

        // Excise target nonwhite attacking creature unless its controller pays {X}. 
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new ExileTargetEffect(), ManacostVariableValue.REGULAR));
    }

    private Excise(final Excise card) {
        super(card);
    }

    @Override
    public Excise copy() {
        return new Excise(this);
    }
}
