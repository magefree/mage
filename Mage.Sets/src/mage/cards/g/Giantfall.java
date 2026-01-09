package mage.cards.g;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Giantfall extends CardImpl {

    public Giantfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one --
        // * Target creature you control deals damage equal to its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        // * Destroy target artifact.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetArtifactPermanent()));
    }

    private Giantfall(final Giantfall card) {
        super(card);
    }

    @Override
    public Giantfall copy() {
        return new Giantfall(this);
    }
}
