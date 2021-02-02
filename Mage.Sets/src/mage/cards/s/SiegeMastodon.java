

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
public final class SiegeMastodon extends CardImpl {

    public SiegeMastodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.ELEPHANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

    }

    private SiegeMastodon(final SiegeMastodon card) {
        super(card);
    }

    @Override
    public SiegeMastodon copy() {
        return new SiegeMastodon(this);
    }

}
