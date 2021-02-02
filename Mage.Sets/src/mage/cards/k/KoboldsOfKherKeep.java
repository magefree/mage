
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class KoboldsOfKherKeep extends CardImpl {

    public KoboldsOfKherKeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{0}");
        this.color.setRed(true);
        this.subtype.add(SubType.KOBOLD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
    }

    private KoboldsOfKherKeep(final KoboldsOfKherKeep card) {
        super(card);
    }

    @Override
    public KoboldsOfKherKeep copy() {
        return new KoboldsOfKherKeep(this);
    }
}
