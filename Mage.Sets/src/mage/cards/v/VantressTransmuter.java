package mage.cards.v;

import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VantressTransmuter extends AdventureCard {

    public VantressTransmuter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{3}{U}",
                "Croaking Curse",
                new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Vantress Transmuter
        this.getLeftHalfCard().setPT(3, 4);

        // Croaking Curse
        // Tap target creature. Create a Cursed Role token attached to it.
        this.getRightHalfCard().getSpellAbility().addEffect(new TapTargetEffect());
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.CURSED)
                .setText("create a Cursed Role token attached to it"));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        finalizeCard();
    }

    private VantressTransmuter(final VantressTransmuter card) {
        super(card);
    }

    @Override
    public VantressTransmuter copy() {
        return new VantressTransmuter(this);
    }
}
