package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class SejiriShelter extends ModalDoubleFacedCard {

    public SejiriShelter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{1}{W}",
                "Sejiri Glacier", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Sejiri Shelter
        // Instant

        // Target creature you control gains protection from the color of your choice until end of turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // 2.
        // Sejiri Glacier
        // Land

        // Sejiri Glacier enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
    }

    private SejiriShelter(final SejiriShelter card) {
        super(card);
    }

    @Override
    public SejiriShelter copy() {
        return new SejiriShelter(this);
    }
}
