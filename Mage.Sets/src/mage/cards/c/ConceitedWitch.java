package mage.cards.c;

import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class ConceitedWitch extends AdventureCard {

    public ConceitedWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARLOCK}, "{2}{B}",
                "Price of Beauty",
                new CardType[]{CardType.SORCERY}, "{B}");

        // Conceited Witch
        this.getLeftHalfCard().setPT(2, 3);

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility());

        // Price of Beauty
        // Create a Wicked Role token attached to target creature you control.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.WICKED));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        finalizeCard();
    }

    private ConceitedWitch(final ConceitedWitch card) {
        super(card);
    }

    @Override
    public ConceitedWitch copy() {
        return new ConceitedWitch(this);
    }
}
