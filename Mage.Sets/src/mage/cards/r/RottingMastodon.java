
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
public final class RottingMastodon extends CardImpl {

    public RottingMastodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELEPHANT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(8);
    }

    private RottingMastodon(final RottingMastodon card) {
        super(card);
    }

    @Override
    public RottingMastodon copy() {
        return new RottingMastodon(this);
    }
}
