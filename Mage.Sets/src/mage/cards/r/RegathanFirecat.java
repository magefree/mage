
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class RegathanFirecat extends CardImpl {

    public RegathanFirecat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
    }

    private RegathanFirecat(final RegathanFirecat card) {
        super(card);
    }

    @Override
    public RegathanFirecat copy() {
        return new RegathanFirecat(this);
    }
}
