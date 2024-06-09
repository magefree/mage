

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class WingedCoatl extends CardImpl {

    public WingedCoatl (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{U}");
        this.subtype.add(SubType.SNAKE);


        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(FlashAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private WingedCoatl(final WingedCoatl card) {
        super(card);
    }

    @Override
    public WingedCoatl copy() {
        return new WingedCoatl(this);
    }

}
