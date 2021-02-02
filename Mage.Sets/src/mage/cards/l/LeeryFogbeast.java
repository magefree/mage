
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Plopman
 */
public final class LeeryFogbeast extends CardImpl {

    public LeeryFogbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever Leery Fogbeast becomes blocked, prevent all combat damage that would be dealt this turn.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true), false));
    }

    private LeeryFogbeast(final LeeryFogbeast card) {
        super(card);
    }

    @Override
    public LeeryFogbeast copy() {
        return new LeeryFogbeast(this);
    }
}
