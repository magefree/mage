

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class WaveskimmerAven extends CardImpl {

    public WaveskimmerAven (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{W}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);


        this.power = new MageInt(2 );
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new ExaltedAbility());
    }

    private WaveskimmerAven(final WaveskimmerAven card) {
        super(card);
    }

    @Override
    public WaveskimmerAven copy() {
        return new WaveskimmerAven(this);
    }

}
