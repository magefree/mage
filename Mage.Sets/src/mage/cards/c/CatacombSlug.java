
package mage.cards.c;

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
public final class CatacombSlug extends CardImpl {

    public CatacombSlug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.SLUG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(6);
    }

    private CatacombSlug(final CatacombSlug card) {
        super(card);
    }

    @Override
    public CatacombSlug copy() {
        return new CatacombSlug(this);
    }
}
