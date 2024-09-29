

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class GorillaWarrior extends CardImpl {

    public GorillaWarrior (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.APE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private GorillaWarrior(final GorillaWarrior card) {
        super(card);
    }

    @Override
    public GorillaWarrior copy() {
        return new GorillaWarrior(this);
    }
}
