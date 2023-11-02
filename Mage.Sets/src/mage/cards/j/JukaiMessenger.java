

package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class JukaiMessenger extends CardImpl {

    public JukaiMessenger (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new ForestwalkAbility());
    }

    private JukaiMessenger(final JukaiMessenger card) {
        super(card);
    }

    @Override
    public JukaiMessenger copy() {
        return new JukaiMessenger(this);
    }

}
