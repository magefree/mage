

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class Hovermyr extends CardImpl {

    public Hovermyr (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(1 );
        this.toughness = new MageInt( 2);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
    }

    private Hovermyr(final Hovermyr card) {
        super(card);
    }

    @Override
    public Hovermyr copy() {
        return new Hovermyr(this);
    }

}
