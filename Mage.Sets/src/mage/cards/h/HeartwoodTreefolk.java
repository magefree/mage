
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class HeartwoodTreefolk extends CardImpl {

    public HeartwoodTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(new ForestwalkAbility());
    }

    private HeartwoodTreefolk(final HeartwoodTreefolk card) {
        super(card);
    }

    @Override
    public HeartwoodTreefolk copy() {
        return new HeartwoodTreefolk(this);
    }
}
