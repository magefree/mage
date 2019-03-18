
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class AmbuscadeShaman extends CardImpl {

    public AmbuscadeShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Ambuscade Shaman or another creature enters the battlefield under your control, that creature gets +2/+2 until end of turn.
        Effect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("that creature gets +2/+2 until end of turn");
        this.addAbility(new AmbuscadeShamanTriggeredAbility(effect));

        // Dash {3}{B} <i>(You may cast this spell for its dash cost. If you do, it gains haste, and it's returned from the battlefield to its owner's hand at the beginning of the next end step.)</i>);
        this.addAbility(new DashAbility(this, "{3}{B}"));

    }

    public AmbuscadeShaman(final AmbuscadeShaman card) {
        super(card);
    }

    @Override
    public AmbuscadeShaman copy() {
        return new AmbuscadeShaman(this);
    }
}

class AmbuscadeShamanTriggeredAbility extends TriggeredAbilityImpl {

    AmbuscadeShamanTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    AmbuscadeShamanTriggeredAbility(final AmbuscadeShamanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AmbuscadeShamanTriggeredAbility copy() {
        return new AmbuscadeShamanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        if (permanent.isControlledBy(this.controllerId)
                && permanent.isCreature()) {
            this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another creature enters the battlefield under your control, that creature gets +2/+2 until end of turn.";
    }
}
