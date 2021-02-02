

package mage.cards.c;

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
public final class CanyonMinotaur extends CardImpl {

    public CanyonMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private CanyonMinotaur(final CanyonMinotaur card) {
        super(card);
    }

    @Override
    public CanyonMinotaur copy() {
        return new CanyonMinotaur(this);
    }

}
