package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CastIntoTheFire extends CardImpl {

    public CastIntoTheFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one --
        // * Cast into the Fire deals 1 damage to each of up to two target creatures.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // * Exile target artifact.
        this.getSpellAbility().addMode(new Mode(new ExileTargetEffect()).addTarget(new TargetArtifactPermanent()));
    }

    private CastIntoTheFire(final CastIntoTheFire card) {
        super(card);
    }

    @Override
    public CastIntoTheFire copy() {
        return new CastIntoTheFire(this);
    }
}
