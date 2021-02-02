

package mage.cards.s;

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
public final class SilvercoatLion extends CardImpl {

    public SilvercoatLion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

    }

    private SilvercoatLion(final SilvercoatLion card) {
        super(card);
    }

    @Override
    public SilvercoatLion copy() {
        return new SilvercoatLion(this);
    }

}
