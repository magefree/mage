package mage.cards.r;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.filter.predicate.other.NotManaAbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RingsOfBrighthearth extends CardImpl {
    private static final FilterStackObject filter = new FilterActivatedOrTriggeredAbility("an ability that isn't a mana ability");

    static {
        filter.add(NotManaAbilityPredicate.instance);
    }

    public RingsOfBrighthearth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever you activate an ability, if it isn't a mana ability, you may pay {2}. If you do, copy that ability. You may choose new targets for the copy.
        this.addAbility(new ActivateAbilityTriggeredAbility(new DoIfCostPaid(new CopyStackObjectEffect(), new ManaCostsImpl<>("{2}")), filter, SetTargetPointer.SPELL));
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
        setTriggerPhrase("Whenever you activate an ability, if it isn't a mana ability, ");
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
        if (stackAbility == null || stackAbility.getStackAbility().isManaActivatedAbility()) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }
}
