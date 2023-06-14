package mage.cards.c;

import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class CragcrownPathway extends ModalDoubleFacedCard {

    public CragcrownPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Timbercrown Pathway", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Cragcrown Pathway
        // Land

        // {T}: Add {R}.
        this.getLeftHalfCard().addAbility(new RedManaAbility());
        this.getLeftHalfCard().addAbility(new TransformAbility());

        // 2.
        // Timbercrown Pathway
        // Land

        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());
    }

    private CragcrownPathway(final CragcrownPathway card) {
        super(card);
    }

    @Override
    public CragcrownPathway copy() {
        return new CragcrownPathway(this);
    }
}
