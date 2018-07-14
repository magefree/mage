
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.SourceOnBattlefieldControlUnchangedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class Willbreaker extends CardImpl {

    public Willbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a creature an opponent controls becomes the target of a spell or ability you control, gain control of that creature for as long as you control Willbreaker.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new GainControlTargetEffect(Duration.Custom), new SourceOnBattlefieldControlUnchangedCondition(), null);
        effect.setText("gain control of that creature for as long as you control {this}");
        this.addAbility(new WillbreakerTriggeredAbility(effect));
    }

    public Willbreaker(final Willbreaker card) {
        super(card);
    }

    @Override
    public Willbreaker copy() {
        return new Willbreaker(this);
    }
}

class WillbreakerTriggeredAbility extends TriggeredAbilityImpl {

    public WillbreakerTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public WillbreakerTriggeredAbility(WillbreakerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getPlayerId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isCreature()) {
                Player controller = game.getPlayer(getControllerId());
                if (controller != null && controller.hasOpponent(permanent.getControllerId(), game)) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls becomes the target of a spell or ability you control, " + super.getRule();
    }

    @Override
    public WillbreakerTriggeredAbility copy() {
        return new WillbreakerTriggeredAbility(this);
    }
}
