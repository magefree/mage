package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class SigilCaptain extends CardImpl {

    public SigilCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{W}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature enters the battlefield under your control, if that creature is 1/1, put two +1/+1 counters on it.
        this.addAbility(new SigilCaptainTriggeredAbility());
    }

    private SigilCaptain(final SigilCaptain card) {
        super(card);
    }

    @Override
    public SigilCaptain copy() {
        return new SigilCaptain(this);
    }
}

class SigilCaptainTriggeredAbility extends TriggeredAbilityImpl {

    public SigilCaptainTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
    }

    public SigilCaptainTriggeredAbility(final SigilCaptainTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            return false;
        }
        if (permanent.isControlledBy(controllerId)
                && permanent.getPower().getValue() == 1
                && permanent.getToughness().getValue() == 1) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public SigilCaptainTriggeredAbility copy() {
        return new SigilCaptainTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield under your control, if that creature is 1/1, put two +1/+1 counters on it.";
    }
}
