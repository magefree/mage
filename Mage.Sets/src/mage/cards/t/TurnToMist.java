
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class TurnToMist extends CardImpl {

    public TurnToMist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W/U}");


        // Exile target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ExileReturnBattlefieldNextEndStepTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TurnToMist(final TurnToMist card) {
        super(card);
    }

    @Override
    public TurnToMist copy() {
        return new TurnToMist(this);
    }
}
