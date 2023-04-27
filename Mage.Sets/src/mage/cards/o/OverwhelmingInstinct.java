
package mage.cards.o;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Wehk
 */
public final class OverwhelmingInstinct extends CardImpl {

    public OverwhelmingInstinct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever you attack with three or more creatures, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DrawCardSourceControllerEffect(1), 3));
    }

    private OverwhelmingInstinct(final OverwhelmingInstinct card) {
        super(card);
    }

    @Override
    public OverwhelmingInstinct copy() {
        return new OverwhelmingInstinct(this);
    }
}

class OverwhelmingInstinctTriggeredAbility extends TriggeredAbilityImpl {

    public OverwhelmingInstinctTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever you attack with three or more creatures, ");
    }

    public OverwhelmingInstinctTriggeredAbility(final OverwhelmingInstinctTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OverwhelmingInstinctTriggeredAbility copy() {
        return new OverwhelmingInstinctTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 3 && game.getCombat().getAttackingPlayerId().equals(getControllerId());
    }
}
