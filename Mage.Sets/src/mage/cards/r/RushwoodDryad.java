
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RushwoodDryad extends CardImpl {

    public RushwoodDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new ForestwalkAbility());
    }

    private RushwoodDryad(final RushwoodDryad card) {
        super(card);
    }

    @Override
    public RushwoodDryad copy() {
        return new RushwoodDryad(this);
    }
}
