package mage.cards.b;

import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlightstepPathway extends ModalDoubleFacedCard {

    public BlightstepPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Searstep Pathway", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Blightstep Pathway
        // Land

        // {T}: Add {B}.
        this.getLeftHalfCard().addAbility(new BlackManaAbility());

        // 2.
        // Searstep Pathway
        // Land

        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());
    }

    private BlightstepPathway(final BlightstepPathway card) {
        super(card);
    }

    @Override
    public BlightstepPathway copy() {
        return new BlightstepPathway(this);
    }
}
