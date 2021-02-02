
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.PlainswalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RighteousAvengers extends CardImpl {

    public RighteousAvengers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Plainswalk
        this.addAbility(new PlainswalkAbility());
    }

    private RighteousAvengers(final RighteousAvengers card) {
        super(card);
    }

    @Override
    public RighteousAvengers copy() {
        return new RighteousAvengers(this);
    }
}
