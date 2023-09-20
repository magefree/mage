

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
public final class CylianElf extends CardImpl {

    public CylianElf (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private CylianElf(final CylianElf card) {
        super(card);
    }

    @Override
    public CylianElf copy() {
        return new CylianElf(this);
    }
}
