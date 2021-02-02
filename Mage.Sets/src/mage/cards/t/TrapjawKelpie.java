
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class TrapjawKelpie extends CardImpl {

    public TrapjawKelpie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G/U}{G/U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Persist
        this.addAbility(new PersistAbility());
    }

    private TrapjawKelpie(final TrapjawKelpie card) {
        super(card);
    }

    @Override
    public TrapjawKelpie copy() {
        return new TrapjawKelpie(this);
    }
}
