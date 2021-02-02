
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
public final class MarshGoblins extends CardImpl {

    public MarshGoblins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());
    }

    private MarshGoblins(final MarshGoblins card) {
        super(card);
    }

    @Override
    public MarshGoblins copy() {
        return new MarshGoblins(this);
    }
}
