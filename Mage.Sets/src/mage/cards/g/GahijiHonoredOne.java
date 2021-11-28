package mage.cards.g;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class GahijiHonoredOne extends CardImpl {

    public GahijiHonoredOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a creature attacks one of your opponents or a planeswalker an opponent controls, that creature gets +2/+0 until end of turn.
        this.addAbility(new GahijiHonoredOneTriggeredAbility());

    }

    private GahijiHonoredOne(final GahijiHonoredOne card) {
        super(card);
    }

    @Override
    public GahijiHonoredOne copy() {
        return new GahijiHonoredOne(this);
    }
}

class GahijiHonoredOneTriggeredAbility extends TriggeredAbilityImpl {

    public GahijiHonoredOneTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(2, 0, Duration.EndOfTurn), false);
    }

    public GahijiHonoredOneTriggeredAbility(Effect effect, boolean optional, String text) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public GahijiHonoredOneTriggeredAbility(final GahijiHonoredOneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player defender = game.getPlayer(event.getTargetId());
        if (defender == null) {
            Permanent planeswalker = game.getPermanent(event.getTargetId());
            if (planeswalker != null) {
                defender = game.getPlayer(planeswalker.getControllerId());
            }
        }
        if (defender != null) {
            Set<UUID> opponents = game.getOpponents(this.getControllerId());
            if (opponents != null && opponents.contains(defender.getId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks one of your opponents or a planeswalker an opponent controls, that creature gets +2/+0 until end of turn.";
    }

    @Override
    public GahijiHonoredOneTriggeredAbility copy() {
        return new GahijiHonoredOneTriggeredAbility(this);
    }

}
