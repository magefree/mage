
package mage.cards.f;

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
public final class FarbogExplorer extends CardImpl {

    public FarbogExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(new SwampwalkAbility());
    }

    private FarbogExplorer(final FarbogExplorer card) {
        super(card);
    }

    @Override
    public FarbogExplorer copy() {
        return new FarbogExplorer(this);
    }
}
