
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GiantSolifuge extends CardImpl {

    public GiantSolifuge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R/G}{R/G}");
        this.subtype.add(SubType.INSECT);


        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(ShroudAbility.getInstance());
    }

    private GiantSolifuge(final GiantSolifuge card) {
        super(card);
    }

    @Override
    public GiantSolifuge copy() {
        return new GiantSolifuge(this);
    }
}
