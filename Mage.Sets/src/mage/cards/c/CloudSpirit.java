
package mage.cards.c;

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
 * @author michael.napoleon@gmail.com
 */
public final class CloudSpirit extends CardImpl {

    public CloudSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Cloud Spirit can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private CloudSpirit(final CloudSpirit card) {
        super(card);
    }

    @Override
    public CloudSpirit copy() {
        return new CloudSpirit(this);
    }
}
