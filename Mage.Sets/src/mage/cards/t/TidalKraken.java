
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class TidalKraken extends CardImpl {

    public TidalKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}{U}");
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Tidal Kraken can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private TidalKraken(final TidalKraken card) {
        super(card);
    }

    @Override
    public TidalKraken copy() {
        return new TidalKraken(this);
    }
}
