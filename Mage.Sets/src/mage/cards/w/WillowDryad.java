
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class WillowDryad extends CardImpl {

    public WillowDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());
    }

    private WillowDryad(final WillowDryad card) {
        super(card);
    }

    @Override
    public WillowDryad copy() {
        return new WillowDryad(this);
    }
}
