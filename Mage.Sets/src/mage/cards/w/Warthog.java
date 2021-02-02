
package mage.cards.w;

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
public final class Warthog extends CardImpl {

    public Warthog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());
    }

    private Warthog(final Warthog card) {
        super(card);
    }

    @Override
    public Warthog copy() {
        return new Warthog(this);
    }
}
