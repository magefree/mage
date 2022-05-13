package mage.cards.a;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class AmuletOfSafekeeping extends CardImpl {

    public AmuletOfSafekeeping(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever you become the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays {1}.
        this.addAbility(new AmuletOfSafekeepingTriggeredAbility());

        // Creature tokens get -1/-0.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostAllEffect(
                        -1, 0, Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CREATURE_TOKENS, false
                )
        ));
    }

    private AmuletOfSafekeeping(final AmuletOfSafekeeping card) {
        super(card);
    }

    @Override
    public AmuletOfSafekeeping copy() {
        return new AmuletOfSafekeeping(this);
    }
}

class AmuletOfSafekeepingTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterStackObject filter = new FilterStackObject();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public AmuletOfSafekeepingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new GenericManaCost(1)));
    }

    public AmuletOfSafekeepingTriggeredAbility(final AmuletOfSafekeepingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AmuletOfSafekeepingTriggeredAbility copy() {
        return new AmuletOfSafekeepingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (event.getTargetId().equals(getControllerId())
                && filter.match(stackObject, getControllerId(), this, game)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(stackObject.getId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you become the target of a spell or ability an opponent controls, "
                + "counter that spell or ability unless its controller pays {1}";
    }
}
