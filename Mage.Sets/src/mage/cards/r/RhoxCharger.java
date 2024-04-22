

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class RhoxCharger extends CardImpl {

    public RhoxCharger (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new ExaltedAbility());
    }

    private RhoxCharger(final RhoxCharger card) {
        super(card);
    }

    @Override
    public RhoxCharger copy() {
        return new RhoxCharger(this);
    }

}
