
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class VWing extends CardImpl {

    public VWing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());
    }

    private VWing(final VWing card) {
        super(card);
    }

    @Override
    public VWing copy() {
        return new VWing(this);
    }
}
