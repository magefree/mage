
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SoulshiftAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class BodyOfJukai extends CardImpl {

    public BodyOfJukai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{G}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(8);
        this.toughness = new MageInt(5);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new SoulshiftAbility(8));
    }

    private BodyOfJukai(final BodyOfJukai card) {
        super(card);
    }

    @Override
    public BodyOfJukai copy() {
        return new BodyOfJukai(this);
    }
}
