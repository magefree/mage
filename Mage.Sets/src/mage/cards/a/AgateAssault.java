package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgateAssault extends CardImpl {

    public AgateAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Choose one --
        // * Agate Assault deals 4 damage to target creature. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Exile target artifact.
        this.getSpellAbility().addMode(new Mode(new ExileTargetEffect()).addTarget(new TargetArtifactPermanent()));
    }

    private AgateAssault(final AgateAssault card) {
        super(card);
    }

    @Override
    public AgateAssault copy() {
        return new AgateAssault(this);
    }
}
