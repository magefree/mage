

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DaggerclawImp extends CardImpl {

    public DaggerclawImp (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.IMP);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new CantBlockAbility());
    }

    private DaggerclawImp(final DaggerclawImp card) {
        super(card);
    }

    @Override
    public DaggerclawImp copy() {
        return new DaggerclawImp(this);
    }

}
