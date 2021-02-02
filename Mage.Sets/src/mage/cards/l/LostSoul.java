
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class LostSoul extends CardImpl {

    public LostSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.MINION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new SwampwalkAbility());
    }

    private LostSoul(final LostSoul card) {
        super(card);
    }

    @Override
    public LostSoul copy() {
        return new LostSoul(this);
    }
}
