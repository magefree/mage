
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class OonasGatewarden extends CardImpl {

    public OonasGatewarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U/B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(WitherAbility.getInstance());
    }

    private OonasGatewarden(final OonasGatewarden card) {
        super(card);
    }

    @Override
    public OonasGatewarden copy() {
        return new OonasGatewarden(this);
    }
}
