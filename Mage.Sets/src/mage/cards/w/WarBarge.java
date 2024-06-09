
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class WarBarge extends CardImpl {

    public WarBarge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        
        // {3}: Target creature gains islandwalk until end of turn. When War Barge leaves the battlefield this turn, destroy that creature. A creature destroyed this way can't be regenerated.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(new IslandwalkAbility(false), Duration.EndOfTurn), new GenericManaCost(3));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new WarBargeDelayedTriggeredAbility()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private WarBarge(final WarBarge card) {
        super(card);
    }

    @Override
    public WarBarge copy() {
        return new WarBarge(this);
    }
}

class WarBargeDelayedTriggeredAbility extends DelayedTriggeredAbility {

    WarBargeDelayedTriggeredAbility() {
        super(new DestroyTargetEffect(true), Duration.EndOfTurn, false);
    }

    private WarBargeDelayedTriggeredAbility(final WarBargeDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WarBargeDelayedTriggeredAbility copy() {
        return new WarBargeDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                Effect effect = this.getEffects().get(0);
                effect.setTargetPointer(new FixedTarget(this.getFirstTarget()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When War Barge leaves the battlefield this turn, destroy that creature. A creature destroyed this way can't be regenerated.";
    }
}
