

package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AjanisMantra extends CardImpl {

    public AjanisMantra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new GainLifeEffect(1), true));
    }

    private AjanisMantra(final AjanisMantra card) {
        super(card);
    }

    @Override
    public AjanisMantra copy() {
        return new AjanisMantra(this);
    }

}
