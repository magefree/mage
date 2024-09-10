

package mage.cards.l;

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
public final class LeoninSkyhunter extends CardImpl {

    public LeoninSkyhunter (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    private LeoninSkyhunter(final LeoninSkyhunter card) {
        super(card);
    }

    @Override
    public LeoninSkyhunter copy() {
        return new LeoninSkyhunter(this);
    }

}
