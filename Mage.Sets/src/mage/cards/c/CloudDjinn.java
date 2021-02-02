
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
 * @author fireshoes
 */
public final class CloudDjinn extends CardImpl {

    public CloudDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Cloud Djinn can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private CloudDjinn(final CloudDjinn card) {
        super(card);
    }

    @Override
    public CloudDjinn copy() {
        return new CloudDjinn(this);
    }
}
