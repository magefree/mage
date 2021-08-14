package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
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
 * @author L_J
 */
public final class DaruSpiritualist extends CardImpl {

    public DaruSpiritualist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a Cleric creature you control becomes the target of a spell or ability, it gets +0/+2 until end of turn.
        this.addAbility(new DaruSpiritualistTriggeredAbility(new BoostTargetEffect(0, 2, Duration.EndOfTurn)));
    }

    private DaruSpiritualist(final DaruSpiritualist card) {
        super(card);
    }

    @Override
    public DaruSpiritualist copy() {
        return new DaruSpiritualist(this);
    }
}

class DaruSpiritualistTriggeredAbility extends TriggeredAbilityImpl {

    public DaruSpiritualistTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public DaruSpiritualistTriggeredAbility(final DaruSpiritualistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DaruSpiritualistTriggeredAbility copy() {
        return new DaruSpiritualistTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanent(event.getTargetId());
        if (creature != null && creature.hasSubtype(SubType.CLERIC, game) && creature.getControllerId().equals(getControllerId()) && creature.isCreature(game)) {
            this.getEffects().setTargetPointer(new FixedTarget(creature, game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Cleric creature you control becomes the target of a spell or ability, it gets +0/+2 until end of turn.";
    }
}
