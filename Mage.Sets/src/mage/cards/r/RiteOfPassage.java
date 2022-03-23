
package mage.cards.r;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public final class RiteOfPassage extends CardImpl {

    public RiteOfPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Whenever a creature you control is dealt damage, put a +1/+1 counter on it.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("put a +1/+1 counter on it");
        this.addAbility(new RiteOfPassageTriggeredAbility(effect));

    }

    private RiteOfPassage(final RiteOfPassage card) {
        super(card);
    }

    @Override
    public RiteOfPassage copy() {
        return new RiteOfPassage(this);
    }
}

class RiteOfPassageTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    public RiteOfPassageTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public RiteOfPassageTriggeredAbility(final RiteOfPassageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RiteOfPassageTriggeredAbility copy() {
        return new RiteOfPassageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null && filter.match(permanent, getControllerId(), this, game)) {
            getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a creature you control is dealt damage, " ;
    }
}
