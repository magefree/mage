package mage.cards.c;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CorruptionOfTowashi extends CardImpl {

    public CorruptionOfTowashi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        // When Corruption of Towashi enters the battlefield, incubate 4.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IncubateEffect(4)));

        // Whenever a permanent you control transforms or a permanent enters the battlefield under your control transformed, you may draw a card. Do this only once each turn.
        this.addAbility(new CorruptionOfTowashiTriggeredAbility());
    }

    private CorruptionOfTowashi(final CorruptionOfTowashi card) {
        super(card);
    }

    @Override
    public CorruptionOfTowashi copy() {
        return new CorruptionOfTowashi(this);
    }
}

class CorruptionOfTowashiTriggeredAbility extends TriggeredAbilityImpl {

    CorruptionOfTowashiTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
        this.setTriggerPhrase("Whenever a permanent you control transforms " +
                "or a permanent enters the battlefield under your control transformed, ");
        this.setDoOnlyOnceEachTurn(true);
    }

    private CorruptionOfTowashiTriggeredAbility(final CorruptionOfTowashiTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CorruptionOfTowashiTriggeredAbility copy() {
        return new CorruptionOfTowashiTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(game.getControllerId(event.getTargetId()))) {
            return false;
        }
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                return ((EntersTheBattlefieldEvent) event).getTarget().isTransformed();
            case TRANSFORMED:
                return true;
        }
        return false;
    }
}
