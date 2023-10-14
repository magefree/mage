package mage.cards.b;

import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class BranchloftPathway extends ModalDoubleFacedCard {

    public BranchloftPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Boulderloft Pathway", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Branchloft Pathway
        // Land

        // {T}: Add {G}.
        this.getLeftHalfCard().addAbility(new GreenManaAbility());

        // 2.
        // Boulderloft Pathway
        // Land

        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
    }

    private BranchloftPathway(final BranchloftPathway card) {
        super(card);
    }

    @Override
    public BranchloftPathway copy() {
        return new BranchloftPathway(this);
    }
}
