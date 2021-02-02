

package mage.cards.e;

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
public final class EliteVanguard extends CardImpl {

    public EliteVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private EliteVanguard(final EliteVanguard card) {
        super(card);
    }

    @Override
    public EliteVanguard copy() {
        return new EliteVanguard(this);
    }

}
