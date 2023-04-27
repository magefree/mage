
package mage.cards.d;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author jeffwadsworth
 */
public final class DragonAppeasement extends CardImpl {

    public DragonAppeasement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{R}{G}");

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));

        // Whenever you sacrifice a creature, you may draw a card.
        this.addAbility(new DragonAppeasementTriggeredAbility());

    }

    private DragonAppeasement(final DragonAppeasement card) {
        super(card);
    }

    @Override
    public DragonAppeasement copy() {
        return new DragonAppeasement(this);
    }
}

class DragonAppeasementTriggeredAbility extends TriggeredAbilityImpl {

    public DragonAppeasementTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
        setLeavesTheBattlefieldTrigger(true);
        setTriggerPhrase("Whenever you sacrifice a creature, ");
    }

    public DragonAppeasementTriggeredAbility(final DragonAppeasementTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DragonAppeasementTriggeredAbility copy() {
        return new DragonAppeasementTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).isCreature(game);
    }
}
