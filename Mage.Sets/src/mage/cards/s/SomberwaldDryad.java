
package mage.cards.s;

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
public final class SomberwaldDryad extends CardImpl {

    public SomberwaldDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new ForestwalkAbility());
    }

    private SomberwaldDryad(final SomberwaldDryad card) {
        super(card);
    }

    @Override
    public SomberwaldDryad copy() {
        return new SomberwaldDryad(this);
    }
}
