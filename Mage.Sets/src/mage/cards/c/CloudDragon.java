
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
 * @author ilcartographer
 */
public final class CloudDragon extends CardImpl {

    public CloudDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Cloud Dragon can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private CloudDragon(final CloudDragon card) {
        super(card);
    }

    @Override
    public CloudDragon copy() {
        return new CloudDragon(this);
    }
}
