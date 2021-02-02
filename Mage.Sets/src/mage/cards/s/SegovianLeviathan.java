
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SegovianLeviathan extends CardImpl {

    public SegovianLeviathan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.LEVIATHAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new IslandwalkAbility());
    }

    private SegovianLeviathan(final SegovianLeviathan card) {
        super(card);
    }

    @Override
    public SegovianLeviathan copy() {
        return new SegovianLeviathan(this);
    }
}
