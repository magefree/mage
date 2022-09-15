package mage.cards.k;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KillMaimBurn extends CardImpl {

    public KillMaimBurn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{R}");

        // Choose one or more --
        // * Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // * Destroy target creature.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetCreaturePermanent()));

        // * Kill! Maim! Burn! deals 3 damage to target player.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(3)).addTarget(new TargetPlayer()));
    }

    private KillMaimBurn(final KillMaimBurn card) {
        super(card);
    }

    @Override
    public KillMaimBurn copy() {
        return new KillMaimBurn(this);
    }
}
