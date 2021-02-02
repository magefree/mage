
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class CanopyGorger extends CardImpl {

    public CanopyGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
    }

    private CanopyGorger(final CanopyGorger card) {
        super(card);
    }

    @Override
    public CanopyGorger copy() {
        return new CanopyGorger(this);
    }
}
