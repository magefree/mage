package mage.cards.w;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
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

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Willbreaker extends CardImpl {

    public Willbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a creature an opponent controls becomes the target of a spell or ability you control, gain control of that creature for as long as you control Willbreaker.
        this.addAbility(new WillbreakerTriggeredAbility());
    }

    private Willbreaker(final Willbreaker card) {
        super(card);
    }

    @Override
    public Willbreaker copy() {
        return new Willbreaker(this);
    }
}

class WillbreakerTriggeredAbility extends TriggeredAbilityImpl {

    WillbreakerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainControlTargetEffect(Duration.WhileControlled));
    }

    private WillbreakerTriggeredAbility(final WillbreakerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game)
                || !game.getOpponents(getControllerId()).contains(permanent.getControllerId())) {
            return false;
        }
        // always call this method for FixedTargets in case it is blinked
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls becomes the target of a spell or ability you control, " +
                "gain control of that creature for as long as you control {this}";
    }

    @Override
    public WillbreakerTriggeredAbility copy() {
        return new WillbreakerTriggeredAbility(this);
    }
}
