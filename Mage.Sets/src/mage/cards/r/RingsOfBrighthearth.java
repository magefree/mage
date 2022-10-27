package mage.cards.r;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RingsOfBrighthearth extends CardImpl {

    public RingsOfBrighthearth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever you activate an ability, if it isn't a mana ability, you may pay {2}. If you do, copy that ability. You may choose new targets for the copy.
        this.addAbility(new RingsOfBrighthearthTriggeredAbility());
    }

    private RingsOfBrighthearth(final RingsOfBrighthearth card) {
        super(card);
    }

    @Override
    public RingsOfBrighthearth copy() {
        return new RingsOfBrighthearth(this);
    }
}

class RingsOfBrighthearthTriggeredAbility extends TriggeredAbilityImpl {

    RingsOfBrighthearthTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CopyStackObjectEffect(), new GenericManaCost(2)));
    }

    private RingsOfBrighthearthTriggeredAbility(final RingsOfBrighthearthTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RingsOfBrighthearthTriggeredAbility copy() {
        return new RingsOfBrighthearthTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility == null || stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl) {
            return false;
        }
        this.getEffects().setValue("stackObject", stackAbility);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you activate an ability, if it isn't a mana ability, you may pay {2}. " +
                "If you do, copy that ability. You may choose new targets for the copy.";
    }
}
