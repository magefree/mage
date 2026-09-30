package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 * @author riajones
 */
public final class LokiGodOfMischief extends CardImpl {

    public LokiGodOfMischief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.SORCERER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a player or permanent becomes the target of an ability you control, draw a card.
        // This ability triggers only once each turn.
        this.addAbility(new LokiGodOfMischiefTriggeredAbility());
    }

    private LokiGodOfMischief(final LokiGodOfMischief card) {
        super(card);
    }

    @Override
    public LokiGodOfMischief copy() {
        return new LokiGodOfMischief(this);
    }
}

class LokiGodOfMischiefTriggeredAbility extends TriggeredAbilityImpl {

    LokiGodOfMischiefTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
        this.setTriggerPhrase("Whenever a player or permanent becomes the target of an ability you control, ");
        this.setTriggersLimitEachTurn(1);
    }

    private LokiGodOfMischiefTriggeredAbility(final LokiGodOfMischiefTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LokiGodOfMischiefTriggeredAbility copy() {
        return new LokiGodOfMischiefTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject == null || stackObject.getStackAbility() == null || stackObject instanceof Spell) {
            return false;
        }
        if (!stackObject.isControlledBy(this.getControllerId())) {
            return false;
        }

        UUID targetId = event.getTargetId();

        // Check if target is a player
        Player player = game.getPlayer(targetId);
        if (player != null) {
            return true;
        }

        // Check if target is a permanent
        Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
        return permanent != null;
    }
}
