package mage.cards.f;

import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class FerociousWerefox extends AdventureCard {

    public FerociousWerefox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELF, SubType.FOX, SubType.WARRIOR}, "{3}{G}",
                "Guard Change",
                new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Ferocious Werefox
        this.getLeftHalfCard().setPT(4, 3);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // Guard Change
        // Create a Monster Role token attached to target creature you control.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.MONSTER));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        finalizeCard();
    }

    private FerociousWerefox(final FerociousWerefox card) {
        super(card);
    }

    @Override
    public FerociousWerefox copy() {
        return new FerociousWerefox(this);
    }
}
