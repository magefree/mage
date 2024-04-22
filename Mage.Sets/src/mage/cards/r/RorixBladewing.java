
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author North
 */
public final class RorixBladewing extends CardImpl {

    public RorixBladewing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private RorixBladewing(final RorixBladewing card) {
        super(card);
    }

    @Override
    public RorixBladewing copy() {
        return new RorixBladewing(this);
    }
}
