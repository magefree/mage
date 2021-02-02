
package mage.cards.r;

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
public final class RootwaterCommando extends CardImpl {

    public RootwaterCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new IslandwalkAbility());
    }

    private RootwaterCommando(final RootwaterCommando card) {
        super(card);
    }

    @Override
    public RootwaterCommando copy() {
        return new RootwaterCommando(this);
    }
}
