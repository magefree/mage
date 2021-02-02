

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ArmoredCancrix extends CardImpl {

    public ArmoredCancrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");

        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);
    }

    private ArmoredCancrix(final ArmoredCancrix card) {
        super(card);
    }

    @Override
    public ArmoredCancrix copy() {
        return new ArmoredCancrix(this);
    }

}
