
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WardenOfGeometries extends CardImpl {

    public WardenOfGeometries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private WardenOfGeometries(final WardenOfGeometries card) {
        super(card);
    }

    @Override
    public WardenOfGeometries copy() {
        return new WardenOfGeometries(this);
    }
}
