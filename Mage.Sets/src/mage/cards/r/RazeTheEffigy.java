package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RazeTheEffigy extends CardImpl {

    public RazeTheEffigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Choose one—
        // • Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // • Target attacking creature gets +2/+2 until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(2, 2));
        mode.addTarget(new TargetAttackingCreature());
        this.getSpellAbility().addMode(mode);
    }

    private RazeTheEffigy(final RazeTheEffigy card) {
        super(card);
    }

    @Override
    public RazeTheEffigy copy() {
        return new RazeTheEffigy(this);
    }
}
