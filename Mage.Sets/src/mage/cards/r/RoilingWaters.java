
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class RoilingWaters extends CardImpl {

    private final static FilterCreaturePermanent FILTER = new FilterCreaturePermanent("creatures your opponents control");

    static {
        FILTER.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public RoilingWaters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{U}{U}");

        // Return up to two target creatures your opponents control to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2, FILTER, false));
        // Target player draws two cards.
        Effect effect = new DrawCardTargetEffect(2);
        effect.setTargetPointer(new SecondTargetPointer());
        getSpellAbility().addEffect(effect);
        getSpellAbility().addTarget(new TargetPlayer());
    }

    public RoilingWaters(final RoilingWaters card) {
        super(card);
    }

    @Override
    public RoilingWaters copy() {
        return new RoilingWaters(this);
    }
}
