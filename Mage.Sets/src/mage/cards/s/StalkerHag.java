
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ForestwalkAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class StalkerHag extends CardImpl {

    public StalkerHag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B/G}{B/G}{B/G}");
        this.subtype.add(SubType.HAG);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(new SwampwalkAbility());
        this.addAbility(new ForestwalkAbility());
    }

    private StalkerHag(final StalkerHag card) {
        super(card);
    }

    @Override
    public StalkerHag copy() {
        return new StalkerHag(this);
    }
}
