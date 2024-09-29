

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class Acridian extends CardImpl {

    public Acridian (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.addAbility(new EchoAbility("{1}{G}"));
    }

    private Acridian(final Acridian card) {
        super(card);
    }

    @Override
    public Acridian copy() {
        return new Acridian(this);
    }
}