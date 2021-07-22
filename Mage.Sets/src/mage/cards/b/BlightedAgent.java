
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class BlightedAgent extends CardImpl {

    public BlightedAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.PHYREXIAN, SubType.HUMAN, SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private BlightedAgent(final BlightedAgent card) {
        super(card);
    }

    @Override
    public BlightedAgent copy() {
        return new BlightedAgent(this);
    }
}
