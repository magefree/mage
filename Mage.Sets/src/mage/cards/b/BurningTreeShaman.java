package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class BurningTreeShaman extends CardImpl {

    public BurningTreeShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");
        this.subtype.add(SubType.CENTAUR, SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever a player activates an ability that isn't a mana ability, Burning-Tree Shaman deals 1 damage to that player.
        this.addAbility(new BurningTreeShamanTriggeredAbility());
    }

    private BurningTreeShaman(final BurningTreeShaman card) {
        super(card);
    }

    @Override
    public BurningTreeShaman copy() {
        return new BurningTreeShaman(this);
    }
}

class BurningTreeShamanTriggeredAbility extends TriggeredAbilityImpl {

    BurningTreeShamanTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(StaticValue.get(1), true, "that player", true));
    }

    private BurningTreeShamanTriggeredAbility(final BurningTreeShamanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BurningTreeShamanTriggeredAbility copy() {
        return new BurningTreeShamanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility != null && stackAbility.getAbilityType() == AbilityType.ACTIVATED) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player activates an ability that isn't a mana ability, {this} deals 1 damage to that player.";
    }
}
