
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class MoorFiend extends CardImpl {

    public MoorFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());
    }

    private MoorFiend(final MoorFiend card) {
        super(card);
    }

    @Override
    public MoorFiend copy() {
        return new MoorFiend(this);
    }
}
