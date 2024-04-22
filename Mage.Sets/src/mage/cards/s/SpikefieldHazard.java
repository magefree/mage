package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class SpikefieldHazard extends ModalDoubleFacedCard {

    public SpikefieldHazard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{R}",
                "Spikefield Cave", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Spikefield Hazard
        // Instant

        // Spikefield Hazard deals 1 damage to any target. If a permanent dealt damage this way would die this turn, exile it instead.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getLeftHalfCard().getSpellAbility().addEffect(new ExileTargetIfDiesEffect()
                .setText("If a permanent dealt damage this way would die this turn, exile it instead."));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetAnyTarget());

        // 2.
        // Spikefield Cave
        // Land

        // Spikefield Cave enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());
    }

    private SpikefieldHazard(final SpikefieldHazard card) {
        super(card);
    }

    @Override
    public SpikefieldHazard copy() {
        return new SpikefieldHazard(this);
    }
}
