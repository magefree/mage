
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LevelX2
 */
public final class BorealDruid extends CardImpl {

    public BorealDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.ELF, SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private BorealDruid(final BorealDruid card) {
        super(card);
    }

    @Override
    public BorealDruid copy() {
        return new BorealDruid(this);
    }
}
