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
import mage.game.stack.StackAbility;

/**
 *
 * @author LevelX2
 */
public final class RunicArmasaur extends CardImpl {

    public RunicArmasaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever an opponent activates an ability of a creature or a land that is not a mana ability, you may draw a card.
        this.addAbility(new RunicArmasaurTriggeredAbility());
    }

    private RunicArmasaur(final RunicArmasaur card) {
        super(card);
    }

    @Override
    public RunicArmasaur copy() {
        return new RunicArmasaur(this);
    }
}

class RunicArmasaurTriggeredAbility extends TriggeredAbilityImpl {

    RunicArmasaurTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
        setTriggerPhrase("Whenever an opponent activates an ability of a creature or land that isn't a mana ability, ");
    }

    private RunicArmasaurTriggeredAbility(final RunicArmasaurTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RunicArmasaurTriggeredAbility copy() {
        return new RunicArmasaurTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility != null
                && stackAbility.getAbilityType() == AbilityType.ACTIVATED
                && game.getOpponents(this.getControllerId()).contains(stackAbility.getControllerId())
                && stackAbility.getSourcePermanentOrLKI(game) != null) { // must be a permanent
            MageObject abilitySourceObject = stackAbility.getSourceObject(game);
            return abilitySourceObject != null
                    && (abilitySourceObject.isLand(game)
                    || abilitySourceObject.isCreature(game));
        }
        return false;
    }

}
