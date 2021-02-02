
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RampageAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class CrawGiant extends CardImpl {

    public CrawGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}{G}{G}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Rampage 2
        this.addAbility(new RampageAbility(2));
    }

    private CrawGiant(final CrawGiant card) {
        super(card);
    }

    @Override
    public CrawGiant copy() {
        return new CrawGiant(this);
    }
}
