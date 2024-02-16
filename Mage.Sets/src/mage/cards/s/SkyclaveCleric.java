package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class SkyclaveCleric extends ModalDoubleFacedCard {

    public SkyclaveCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.KOR, SubType.CLERIC}, "{1}{W}",
                "Skyclave Basilica", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Skyclave Cleric
        // Creature — Kor Cleric
        this.getLeftHalfCard().setPT(new MageInt(1), new MageInt(3));

        // When Skyclave Cleric enters the battlefield, you gain 2 life.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));

        // 2.
        // Skyclave Basilica
        // Land

        // Skyclave Basilica enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
    }

    private SkyclaveCleric(final SkyclaveCleric card) {
        super(card);
    }

    @Override
    public SkyclaveCleric copy() {
        return new SkyclaveCleric(this);
    }
}
