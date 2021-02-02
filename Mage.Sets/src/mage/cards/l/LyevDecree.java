

package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.DetainTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */


public final class LyevDecree extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures your opponents control");
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public LyevDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");


        // Detain up to two target creatures your opponents control.
        this.getSpellAbility().addEffect(new DetainTargetEffect());
        Target target = new TargetCreaturePermanent(0,2,filter,false);
        this.getSpellAbility().addTarget(target);
    }

    private LyevDecree(final LyevDecree card) {
        super(card);
    }

    @Override
    public LyevDecree copy() {
        return new LyevDecree(this);
    }

}
