package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class TangledFlorahedron extends ModalDoubleFacedCard {

    public TangledFlorahedron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL}, "{1}{G}",
                "Tangled Vale", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Tangled Florahedron
        // Creature — Elemental
        this.getLeftHalfCard().setPT(new MageInt(1), new MageInt(1));

        // {T}: Add {G}.
        this.getLeftHalfCard().addAbility(new GreenManaAbility());

        // 2.
        // Tangled Vale
        // Land

        // Tangled Vale enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());
    }

    private TangledFlorahedron(final TangledFlorahedron card) {
        super(card);
    }

    @Override
    public TangledFlorahedron copy() {
        return new TangledFlorahedron(this);
    }
}
