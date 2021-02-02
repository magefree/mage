
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class LifespringDruid extends CardImpl {

    public LifespringDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private LifespringDruid(final LifespringDruid card) {
        super(card);
    }

    @Override
    public LifespringDruid copy() {
        return new LifespringDruid(this);
    }
}
