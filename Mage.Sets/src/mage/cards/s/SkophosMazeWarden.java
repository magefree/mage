package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkophosMazeWarden extends CardImpl {

    public SkophosMazeWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {1}: Skophos Maze-Warden gets +1/-1 until the end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, -1, Duration.EndOfTurn), new GenericManaCost(1)
        ));

        // Whenever another creature becomes the target of an ability of a land you control named Labyrinth of Skophos, you may have Skophos Maze-Warden fight that creature.
        this.addAbility(new SkophosMazeWardenTriggeredAbility());
    }

    private SkophosMazeWarden(final SkophosMazeWarden card) {
        super(card);
    }

    @Override
    public SkophosMazeWarden copy() {
        return new SkophosMazeWarden(this);
    }
}

class SkophosMazeWardenTriggeredAbility extends TriggeredAbilityImpl {

    SkophosMazeWardenTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, true);
    }

    private SkophosMazeWardenTriggeredAbility(final SkophosMazeWardenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SkophosMazeWardenTriggeredAbility copy() {
        return new SkophosMazeWardenTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(stackObject.getSourceId());
        if (permanent == null
                || !permanent.isControlledBy(getControllerId())
                || !permanent.isLand(game)
                || !permanent.getName().equals("Labyrinth of Skophos")) {
            return false;
        }
        Permanent creature = game.getPermanent(event.getTargetId());
        if (creature == null
                || !creature.isCreature(game)
                || creature.getId().equals(getSourceId())) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new FightTargetSourceEffect().setTargetPointer(new FixedTarget(creature, game)));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever another creature becomes the target of an ability of a land you control " +
                "named Labyrinth of Skophos, you may have {this} fight that creature. " +
                "<i>(Each deals damage equal to its power to the other.)</i>";
    }
}
