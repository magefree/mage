package mage.cards.b;

import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class BrightclimbPathway extends ModalDoubleFacedCard {

    public BrightclimbPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Grimclimb Pathway", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Brightclimb Pathway
        // Land

        // {T}: Add {W}.
        this.getLeftHalfCard().addAbility(new WhiteManaAbility());

        // 2.
        // Grimclimb Pathway
        // Land

        // {T}: Add {B}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());
    }

    private BrightclimbPathway(final BrightclimbPathway card) {
        super(card);
    }

    @Override
    public BrightclimbPathway copy() {
        return new BrightclimbPathway(this);
    }
}
