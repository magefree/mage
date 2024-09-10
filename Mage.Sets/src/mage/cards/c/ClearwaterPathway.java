package mage.cards.c;

import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ClearwaterPathway extends ModalDoubleFacedCard {

    public ClearwaterPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Murkwater Pathway", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Clearwater Pathway
        // Land

        // {T}: Add {U}.
        this.getLeftHalfCard().addAbility(new BlueManaAbility());

        // 2.
        // Murkwater Pathway
        // Land

        // {T}: Add {B}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());
    }

    private ClearwaterPathway(final ClearwaterPathway card) {
        super(card);
    }

    @Override
    public ClearwaterPathway copy() {
        return new ClearwaterPathway(this);
    }
}
