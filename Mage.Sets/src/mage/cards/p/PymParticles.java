package mage.cards.p;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class PymParticles extends CardImpl {

    public PymParticles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Target creature gains vigilance until end of turn and can't be blocked this turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance()));
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect().setText("and can't be blocked this turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private PymParticles(final PymParticles card) {
        super(card);
    }

    @Override
    public PymParticles copy() {
        return new PymParticles(this);
    }
}
