
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class GiantScorpion extends CardImpl {

    public GiantScorpion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SCORPION);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(DeathtouchAbility.getInstance());
    }

    private GiantScorpion(final GiantScorpion card) {
        super(card);
    }

    @Override
    public GiantScorpion copy() {
        return new GiantScorpion(this);
    }
}
