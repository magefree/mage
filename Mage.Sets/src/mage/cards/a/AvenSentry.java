
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author tcontis
 */
public final class AvenSentry extends CardImpl {

    public AvenSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        //Flying
        this.addAbility(FlyingAbility.getInstance());

    }

    private AvenSentry(final AvenSentry card) {
        super(card);
    }

    @Override
    public AvenSentry copy() {
        return new AvenSentry(this);
    }
}