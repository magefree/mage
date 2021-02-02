
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class Breezekeeper extends CardImpl {

    public Breezekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.DJINN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Phasing
        this.addAbility(PhasingAbility.getInstance());
    }

    private Breezekeeper(final Breezekeeper card) {
        super(card);
    }

    @Override
    public Breezekeeper copy() {
        return new Breezekeeper(this);
    }
}
