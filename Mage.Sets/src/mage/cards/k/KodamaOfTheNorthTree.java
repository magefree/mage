

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author Loki
 */
public final class KodamaOfTheNorthTree extends CardImpl {

    public KodamaOfTheNorthTree (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(ShroudAbility.getInstance());
    }

    public KodamaOfTheNorthTree (final KodamaOfTheNorthTree card) {
        super(card);
    }

    @Override
    public KodamaOfTheNorthTree copy() {
        return new KodamaOfTheNorthTree(this);
    }

}
