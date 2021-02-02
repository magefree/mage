
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class RishadanAirship extends CardImpl {

    public RishadanAirship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Rishadan Airship can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private RishadanAirship(final RishadanAirship card) {
        super(card);
    }

    @Override
    public RishadanAirship copy() {
        return new RishadanAirship(this);
    }
}
