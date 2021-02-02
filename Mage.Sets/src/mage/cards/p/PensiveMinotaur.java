
package mage.cards.p;

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
public final class PensiveMinotaur extends CardImpl {

    public PensiveMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private PensiveMinotaur(final PensiveMinotaur card) {
        super(card);
    }

    @Override
    public PensiveMinotaur copy() {
        return new PensiveMinotaur(this);
    }
}
