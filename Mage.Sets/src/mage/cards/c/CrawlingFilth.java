
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class CrawlingFilth extends CardImpl {

    public CrawlingFilth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FearAbility.getInstance());
        this.addAbility(new SoulshiftAbility(5));
    }

    private CrawlingFilth(final CrawlingFilth card) {
        super(card);
    }

    @Override
    public CrawlingFilth copy() {
        return new CrawlingFilth(this);
    }
}
