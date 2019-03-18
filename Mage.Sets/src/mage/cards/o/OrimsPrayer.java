
package mage.cards.o;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author L_J
 */
public final class OrimsPrayer extends CardImpl {

    public OrimsPrayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // Whenever one or more creatures attack you, you gain 1 life for each attacking creature.
        this.addAbility(new OrimsPrayerTriggeredAbility());
    }

    public OrimsPrayer(final OrimsPrayer card) {
        super(card);
    }

    @Override
    public OrimsPrayer copy() {
        return new OrimsPrayer(this);
    }
}

class OrimsPrayerTriggeredAbility extends TriggeredAbilityImpl {

    public OrimsPrayerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(new AttackingCreatureCount()));
    }

    public OrimsPrayerTriggeredAbility(final OrimsPrayerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OrimsPrayerTriggeredAbility copy() {
        return new OrimsPrayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getDefenders().contains(getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures attack you, " + super.getRule();
    }
}
