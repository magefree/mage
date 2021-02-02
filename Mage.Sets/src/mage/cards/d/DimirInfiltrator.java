
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DimirInfiltrator extends CardImpl {

    public DimirInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Dimir Infiltrator can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
        // Transmute {1}{U}{B}
        this.addAbility(new TransmuteAbility("{1}{U}{B}"));
    }

    private DimirInfiltrator(final DimirInfiltrator card) {
        super(card);
    }

    @Override
    public DimirInfiltrator copy() {
        return new DimirInfiltrator(this);
    }
}
