

package mage.cards.w;

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
 * @author North
 */
public final class WelkinTern extends CardImpl {

    public WelkinTern (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(2 );
        this.toughness = new MageInt( 1);

        this.addAbility(FlyingAbility.getInstance());
        // Welkin Tern can block only creatures with flying.
         this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private WelkinTern(final WelkinTern card) {
        super(card);
    }

    @Override
    public WelkinTern copy() {
        return new WelkinTern(this);
    }

}
