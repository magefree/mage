

package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BattleCryAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LoxodonPartisan extends CardImpl {

    public LoxodonPartisan (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.addAbility(new BattleCryAbility());
    }

    private LoxodonPartisan(final LoxodonPartisan card) {
        super(card);
    }

    @Override
    public LoxodonPartisan copy() {
        return new LoxodonPartisan(this);
    }

}
