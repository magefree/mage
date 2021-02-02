
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author nantuko
 */
public final class InvisibleStalker extends CardImpl {

    public InvisibleStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(HexproofAbility.getInstance());
        // Invisible Stalker can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private InvisibleStalker(final InvisibleStalker card) {
        super(card);
    }

    @Override
    public InvisibleStalker copy() {
        return new InvisibleStalker(this);
    }
}
