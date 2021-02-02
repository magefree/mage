
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class RepublicFrigate extends CardImpl {

    public RepublicFrigate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());
    }

    private RepublicFrigate(final RepublicFrigate card) {
        super(card);
    }

    @Override
    public RepublicFrigate copy() {
        return new RepublicFrigate(this);
    }
}
