package mage.cards.n;

import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class NeedlevergePathway extends ModalDoubleFacedCard {

    public NeedlevergePathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Pillarverge Pathway", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Needleverge Pathway
        // Land

        // {T}: Add {R}.
        this.getLeftHalfCard().addAbility(new RedManaAbility());

        // 2.
        // Pillarverge Pathway
        // Land

        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
    }

    private NeedlevergePathway(final NeedlevergePathway card) {
        super(card);
    }

    @Override
    public NeedlevergePathway copy() {
        return new NeedlevergePathway(this);
    }
}
