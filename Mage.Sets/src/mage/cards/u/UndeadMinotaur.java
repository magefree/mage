
package mage.cards.u;

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
public final class UndeadMinotaur extends CardImpl {

    public UndeadMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINOTAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private UndeadMinotaur(final UndeadMinotaur card) {
        super(card);
    }

    @Override
    public UndeadMinotaur copy() {
        return new UndeadMinotaur(this);
    }
}
