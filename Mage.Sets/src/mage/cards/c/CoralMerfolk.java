

package mage.cards.c;

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
public final class CoralMerfolk extends CardImpl {

    public CoralMerfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(2);
    this.toughness = new MageInt(1);
    }

    private CoralMerfolk(final CoralMerfolk card) {
        super(card);
    }

    @Override
    public CoralMerfolk copy() {
        return new CoralMerfolk(this);
    }

}
