
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class WaspLancer extends CardImpl {

    public WaspLancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U/B}{U/B}{U/B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
    }

    private WaspLancer(final WaspLancer card) {
        super(card);
    }

    @Override
    public WaspLancer copy() {
        return new WaspLancer(this);
    }
}
