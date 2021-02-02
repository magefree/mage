
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Styxo
 */
public final class OrtolanKeyboardist extends CardImpl {

    public OrtolanKeyboardist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ORTOLAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Draw a card then discard a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(), new TapSourceCost()));

    }

    private OrtolanKeyboardist(final OrtolanKeyboardist card) {
        super(card);
    }

    @Override
    public OrtolanKeyboardist copy() {
        return new OrtolanKeyboardist(this);
    }
}
