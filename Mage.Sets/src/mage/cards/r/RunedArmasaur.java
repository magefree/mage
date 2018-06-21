package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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

/**
 *
 * @author LevelX2
 */
public final class RunedArmasaur extends CardImpl {

    public RunedArmasaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever an opponent activates an ability of a creature or a land that is not a mana ability, you may draw a card.
        this.addAbility(new RunedArmasaurTriggeredAbility());
    }

    public RunedArmasaur(final RunedArmasaur card) {
        super(card);
    }

    @Override
    public RunedArmasaur copy() {
        return new RunedArmasaur(this);
    }
}

class RunedArmasaurTriggeredAbility extends TriggeredAbilityImpl {

    RunedArmasaurTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    RunedArmasaurTriggeredAbility(final RunedArmasaurTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RunedArmasaurTriggeredAbility copy() {
        return new RunedArmasaurTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility != null && stackAbility.getAbilityType() == AbilityType.ACTIVATED) {
            MageObject abilitySourceObject = stackAbility.getSourceObject(game);
            return abilitySourceObject != null && (abilitySourceObject.isLand() || abilitySourceObject.isCreature());
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent activates an ability of a creature or a land that is not a mana ability, you may draw a card.";
    }
}
