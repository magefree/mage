
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PromisedKannushi extends CardImpl {

    public PromisedKannushi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new SoulshiftAbility(7));
    }

    private PromisedKannushi(final PromisedKannushi card) {
        super(card);
    }

    @Override
    public PromisedKannushi copy() {
        return new PromisedKannushi(this);
    }
}
