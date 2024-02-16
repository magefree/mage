package mage.cards.d;

import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarkborePathway extends ModalDoubleFacedCard {

    public DarkborePathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Slitherbore Pathway", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Darkbore Pathway
        // Land

        // {T}: Add {B}.
        this.getLeftHalfCard().addAbility(new BlackManaAbility());

        // 2.
        // Slitherbore Pathway
        // Land

        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());
    }

    private DarkborePathway(final DarkborePathway card) {
        super(card);
    }

    @Override
    public DarkborePathway copy() {
        return new DarkborePathway(this);
    }
}
