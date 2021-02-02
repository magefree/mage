
package mage.cards.s;

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
public final class SummitProwler extends CardImpl {

    public SummitProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.YETI);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private SummitProwler(final SummitProwler card) {
        super(card);
    }

    @Override
    public SummitProwler copy() {
        return new SummitProwler(this);
    }
}
