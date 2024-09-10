package mage.cards.r;

import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class RiverglidePathway extends ModalDoubleFacedCard {

    public RiverglidePathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Lavaglide Pathway", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Riverglide Pathway
        // Land

        // {T}: Add {U}.
        this.getLeftHalfCard().addAbility(new BlueManaAbility());

        // 2.
        // Lavaglide Pathway
        // Land

        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());
    }

    private RiverglidePathway(final RiverglidePathway card) {
        super(card);
    }

    @Override
    public RiverglidePathway copy() {
        return new RiverglidePathway(this);
    }
}
