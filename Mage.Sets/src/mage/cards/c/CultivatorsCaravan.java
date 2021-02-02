
package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class CultivatorsCaravan extends CardImpl {

    public CultivatorsCaravan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private CultivatorsCaravan(final CultivatorsCaravan card) {
        super(card);
    }

    @Override
    public CultivatorsCaravan copy() {
        return new CultivatorsCaravan(this);
    }
}
