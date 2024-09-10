package mage.cards.h;

import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HengegatePathway extends ModalDoubleFacedCard {

    public HengegatePathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Mistgate Pathway", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Hengegate Pathway
        // Land

        // {T}: Add {W}.
        this.getLeftHalfCard().addAbility(new WhiteManaAbility());

        // 2.
        // Mistgate Pathway
        // Land

        // {T}: Add {U}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());
    }

    private HengegatePathway(final HengegatePathway card) {
        super(card);
    }

    @Override
    public HengegatePathway copy() {
        return new HengegatePathway(this);
    }
}
