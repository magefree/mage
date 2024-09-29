

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class HundredTalonKami extends CardImpl {

    public HundredTalonKami (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SoulshiftAbility(4));
    }

    private HundredTalonKami(final HundredTalonKami card) {
        super(card);
    }

    @Override
    public HundredTalonKami copy() {
        return new HundredTalonKami(this);
    }

}
