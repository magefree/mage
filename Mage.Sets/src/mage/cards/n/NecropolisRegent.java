package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class NecropolisRegent extends CardImpl {

    public NecropolisRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature you control deals combat damage to a player, put that many +1/+1 counters on it.
        this.addAbility(new NecropolisRegentTriggeredAbility());
    }

    private NecropolisRegent(final NecropolisRegent card) {
        super(card);
    }

    @Override
    public NecropolisRegent copy() {
        return new NecropolisRegent(this);
    }
}

class NecropolisRegentTriggeredAbility extends TriggeredAbilityImpl {

    public NecropolisRegentTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), false);
    }

    public NecropolisRegentTriggeredAbility(final NecropolisRegentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NecropolisRegentTriggeredAbility copy() {
        return new NecropolisRegentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.isControlledBy(controllerId)) {
                this.getEffects().clear();
                Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(event.getAmount()));
                effect.setTargetPointer(new FixedTarget(creature.getId(), game));
                this.addEffect(effect);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control deals combat damage to a player, put that many +1/+1 counters on it.";
    }
}
