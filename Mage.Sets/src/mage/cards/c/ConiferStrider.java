
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ConiferStrider extends CardImpl {

    public ConiferStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
    }

    private ConiferStrider(final ConiferStrider card) {
        super(card);
    }

    @Override
    public ConiferStrider copy() {
        return new ConiferStrider(this);
    }
}
