

package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class ZephyrSprite extends CardImpl {

    public ZephyrSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.FAERIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    private ZephyrSprite(final ZephyrSprite card) {
        super(card);
    }

    @Override
    public ZephyrSprite copy() {
        return new ZephyrSprite(this);
    }

}
