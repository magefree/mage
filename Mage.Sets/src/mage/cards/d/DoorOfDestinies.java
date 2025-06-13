
package mage.cards.d;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class DoorOfDestinies extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }
    public DoorOfDestinies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // As Door of Destinies enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Whenever you cast a spell of the chosen type, put a charge counter on Door of Destinies.
        this.addAbility(new AddCounterAbility());

        // Creatures you control of the chosen type get +1/+1 for each charge counter on Door of Destinies.
        this.addAbility(new SimpleStaticAbility(new BoostAllOfChosenSubtypeEffect(1, 1,
                Duration.WhileOnBattlefield, filter, false)));
    }

    private DoorOfDestinies(final DoorOfDestinies card) {
        super(card);
    }

    @Override
    public DoorOfDestinies copy() {
        return new DoorOfDestinies(this);
    }
}

class AddCounterAbility extends TriggeredAbilityImpl {

    public AddCounterAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)), false);
    }

    private AddCounterAbility(final AddCounterAbility ability) {
        super(ability);
    }

    @Override
    public AddCounterAbility copy() {
        return new AddCounterAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(getSourceId(), game);
        if (subType != null) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null
                    && spell.isControlledBy(getControllerId())
                    && spell.hasSubtype(subType, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell of the chosen type, put a charge counter on {this}.";
    }
}
