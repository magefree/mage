
package mage.cards.c;

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
public final class CowlProwler extends CardImpl {

    public CowlProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
    }

    private CowlProwler(final CowlProwler card) {
        super(card);
    }

    @Override
    public CowlProwler copy() {
        return new CowlProwler(this);
    }
}
