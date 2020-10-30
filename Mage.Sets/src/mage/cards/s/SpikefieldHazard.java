package mage.cards.s;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpikefieldHazard extends CardImpl {

    public SpikefieldHazard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.s.SpikefieldCave.class;

        // Spikefield Hazard deals 1 damage to any target. If a permanent dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect()
                .setText("If a permanent dealt damage this way would die this turn, exile it instead."));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private SpikefieldHazard(final SpikefieldHazard card) {
        super(card);
    }

    @Override
    public SpikefieldHazard copy() {
        return new SpikefieldHazard(this);
    }
}
