
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class AmbushViper extends CardImpl {

    public AmbushViper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private AmbushViper(final AmbushViper card) {
        super(card);
    }

    @Override
    public AmbushViper copy() {
        return new AmbushViper(this);
    }
}
