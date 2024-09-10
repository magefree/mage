

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class TalonTrooper extends CardImpl {

    public TalonTrooper (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SCOUT);


        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
    }

    private TalonTrooper(final TalonTrooper card) {
        super(card);
    }

    @Override
    public TalonTrooper copy() {
        return new TalonTrooper(this);
    }

}
