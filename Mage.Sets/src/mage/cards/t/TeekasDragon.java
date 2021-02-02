
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.RampageAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class TeekasDragon extends CardImpl {

    public TeekasDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{9}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // trample
        this.addAbility(TrampleAbility.getInstance());
        // rampage 4
        this.addAbility(new RampageAbility(4));
    }

    private TeekasDragon(final TeekasDragon card) {
        super(card);
    }

    @Override
    public TeekasDragon copy() {
        return new TeekasDragon(this);
    }
}
