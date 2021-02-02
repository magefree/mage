

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GoblinOutlander extends CardImpl {

    public GoblinOutlander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
    }

    private GoblinOutlander(final GoblinOutlander card) {
        super(card);
    }

    @Override
    public GoblinOutlander copy() {
        return new GoblinOutlander(this);
    }

}
