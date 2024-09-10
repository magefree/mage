
package mage.cards.h;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class HauntingWind extends CardImpl {

    public HauntingWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Whenever an artifact becomes tapped or a player activates an artifact's ability without {tap} in its activation cost, Haunting Wind deals 1 damage to that artifact's controller.
        this.addAbility(new HauntingWindTriggeredAbility());
    }

    private HauntingWind(final HauntingWind card) {
        super(card);
    }

    @Override
    public HauntingWind copy() {
        return new HauntingWind(this);
    }
}

class HauntingWindTriggeredAbility extends TriggeredAbilityImpl {

    HauntingWindTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
    }

    private HauntingWindTriggeredAbility(final HauntingWindTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HauntingWindTriggeredAbility copy() {
        return new HauntingWindTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY
                || event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATED_ABILITY) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (permanent == null || !permanent.isArtifact(game)) {
                return false;
            }
            StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
            if (stackAbility == null) {
                return false;
            }
            String abilityText = stackAbility.getRule(true);
            if (abilityText.contains("{T}:") || abilityText.contains("{T},") || abilityText.contains("{T} or")) {
                return false;
            }
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(permanent.getControllerId(), game));
            }
            return true;
        }
        if (event.getType() == GameEvent.EventType.TAPPED) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null || !permanent.isArtifact(game)) {
                return false;
            }
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(permanent.getControllerId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an artifact becomes tapped or a player activates an artifact's ability without {T} in its activation cost, {this} deals 1 damage to that artifact's controller.";
    }
}
