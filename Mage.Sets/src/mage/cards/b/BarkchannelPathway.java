package mage.cards.b;

import java.util.UUID;

import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class BarkchannelPathway extends ModalDoubleFacedCard {

    public BarkchannelPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Tidechannel Pathway", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Barkchannel Pathway
        // Land

        // {T}: Add {G}.
        this.getLeftHalfCard().addAbility(new GreenManaAbility());

        // 2.
        // Tidechannel Pathway
        // Land

        // {T}: Add {U}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());
    }

    private BarkchannelPathway(final BarkchannelPathway card) {
        super(card);
    }

    @Override
    public BarkchannelPathway copy() {
        return new BarkchannelPathway(this);
    }
}
