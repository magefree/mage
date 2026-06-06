package mage.cards.b;

import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BesottedKnight extends AdventureCard {

    public BesottedKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "{3}{W}",
                "Betroth the Beast",
                new CardType[]{CardType.SORCERY}, "{W}");

        // Besotted Knight
        this.getLeftHalfCard().setPT(3, 3);

        // Betroth the Beast
        // Create a Royal Role token attached to target creature you control.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.ROYAL));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        finalizeCard();
    }

    private BesottedKnight(final BesottedKnight card) {
        super(card);
    }

    @Override
    public BesottedKnight copy() {
        return new BesottedKnight(this);
    }
}
