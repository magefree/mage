

package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */


public final class MendingTouch extends CardImpl {

    public MendingTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        // Regenerate target creature.
        this.getSpellAbility().addEffect(new RegenerateTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MendingTouch(final MendingTouch card) {
        super(card);
    }

    @Override
    public MendingTouch copy() {
        return new MendingTouch(this);
    }

}
