package mage.cards.k;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author emerald000
 */
public final class KillShot extends CardImpl {

    public KillShot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Destroy target attacking creature.
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private KillShot(final KillShot card) {
        super(card);
    }

    @Override
    public KillShot copy() {
        return new KillShot(this);
    }
}
