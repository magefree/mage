
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class StandingTroops extends CardImpl {

    public StandingTroops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        this.addAbility(VigilanceAbility.getInstance());
    }

    private StandingTroops(final StandingTroops card) {
        super(card);
    }

    @Override
    public StandingTroops copy() {
        return new StandingTroops(this);
    }
}
