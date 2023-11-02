

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class PlatedSeastrider extends CardImpl {

    public PlatedSeastrider (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);
    }

    private PlatedSeastrider(final PlatedSeastrider card) {
        super(card);
    }

    @Override
    public PlatedSeastrider copy() {
        return new PlatedSeastrider(this);
    }

}
