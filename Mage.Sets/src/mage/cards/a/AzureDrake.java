

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AzureDrake extends CardImpl {

    public AzureDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
    }

    private AzureDrake(final AzureDrake card) {
        super(card);
    }

    @Override
    public AzureDrake copy() {
        return new AzureDrake(this);
    }

}
