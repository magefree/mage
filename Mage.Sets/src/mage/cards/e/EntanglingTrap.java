
package mage.cards.e;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class EntanglingTrap extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public EntanglingTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // Whenever you clash, tap target creature an opponent controls. If you won, that creature doesn't untap during its controller's next untap step.
        Ability ability = new EntanglingTrapTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public EntanglingTrap(final EntanglingTrap card) {
        super(card);
    }

    @Override
    public EntanglingTrap copy() {
        return new EntanglingTrap(this);
    }
}

class EntanglingTrapTriggeredAbility extends TriggeredAbilityImpl {

    public EntanglingTrapTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TapTargetEffect());
    }

    public EntanglingTrapTriggeredAbility(final EntanglingTrapTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EntanglingTrapTriggeredAbility copy() {
        return new EntanglingTrapTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.CLASHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        //remove effects from previous triggers
        List<Effect> effects = getEffects();
        List<Effect> effectsToRemove = new ArrayList<>();
        for (Effect effect : effects) {
            if (effect instanceof DontUntapInControllersNextUntapStepTargetEffect) {
                effectsToRemove.add(effect);
            }
        }
        for (Effect effect : effectsToRemove) {
            effects.remove(effect);
        }

        if (event.getData().equals("controller") && event.getPlayerId().equals(getControllerId())
                || event.getData().equals("opponent") && event.getTargetId().equals(getControllerId())) {
            addEffect(new DontUntapInControllersNextUntapStepTargetEffect());
        }

        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you clash, tap target creature an opponent controls. If you won, that creature doesn't untap during its controller's next untap step.";
    }
}
