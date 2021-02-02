
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class KessigRecluse extends CardImpl {

    public KessigRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(ReachAbility.getInstance());
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private KessigRecluse(final KessigRecluse card) {
        super(card);
    }

    @Override
    public KessigRecluse copy() {
        return new KessigRecluse(this);
    }
}
