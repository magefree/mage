
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class BayouDragonfly extends CardImpl {

    public BayouDragonfly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SwampwalkAbility());
    }

    private BayouDragonfly(final BayouDragonfly card) {
        super(card);
    }

    @Override
    public BayouDragonfly copy() {
        return new BayouDragonfly(this);
    }
}
