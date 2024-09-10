package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class AkoumWarrior extends ModalDoubleFacedCard {

    public AkoumWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.MINOTAUR, SubType.WARRIOR}, "{5}{R}",
                "Akoum Teeth", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Akoum Warrior
        // Creature — Minotaur Warrior
        this.getLeftHalfCard().setPT(new MageInt(4), new MageInt(5));

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // 2.
        // Akoum Teeth
        // Land

        // Akoum Teeth enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());
    }

    private AkoumWarrior(final AkoumWarrior card) {
        super(card);
    }

    @Override
    public AkoumWarrior copy() {
        return new AkoumWarrior(this);
    }
}
