
package mage.cards.s;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class SpitefulVisions extends CardImpl {

    public SpitefulVisions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B/R}{B/R}");


        // At the beginning of each player's draw step, that player draws an additional card.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new DrawCardTargetEffect(1).setText("that player draws an additional card"), TargetController.ANY, false));

        // Whenever a player draws a card, Spiteful Visions deals 1 damage to that player.
        TriggeredAbility triggeredAbility = new SpitefulVisionsTriggeredAbility(new DamageTargetEffect(1), false);
        this.addAbility(triggeredAbility);
    }

    private SpitefulVisions(final SpitefulVisions card) {
        super(card);
    }

    @Override
    public SpitefulVisions copy() {
        return new SpitefulVisions(this);
    }
}

class SpitefulVisionsTriggeredAbility  extends TriggeredAbilityImpl {

    public SpitefulVisionsTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public SpitefulVisionsTriggeredAbility(final SpitefulVisionsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId() != null) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player draws a card, {this} deals 1 damage to that player.";
    }

    @Override
    public SpitefulVisionsTriggeredAbility copy() {
        return new SpitefulVisionsTriggeredAbility(this);
    }
}