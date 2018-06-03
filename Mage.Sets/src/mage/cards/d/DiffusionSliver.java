
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetStackObject;

/**
 *
 * @author LevelX2
 */
public final class DiffusionSliver extends CardImpl {

    public DiffusionSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a Sliver creature you control becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays {2}.
        this.addAbility(new DiffusionSliverTriggeredAbility(new CounterUnlessPaysEffect(new GenericManaCost(2))));
    }

    public DiffusionSliver(final DiffusionSliver card) {
        super(card);
    }

    @Override
    public DiffusionSliver copy() {
        return new DiffusionSliver(this);
    }
}

class DiffusionSliverTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Sliver creature you control");

    static {
        filter.add(new SubtypePredicate(SubType.SLIVER));
    }

    public DiffusionSliverTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public DiffusionSliverTriggeredAbility(final DiffusionSliverTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiffusionSliverTriggeredAbility copy() {
        return new DiffusionSliverTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            Permanent creature = game.getPermanent(event.getTargetId());
            if (creature != null && filter.match(creature, getSourceId(), getControllerId(), game)) {
                this.getTargets().clear();
                TargetStackObject target = new TargetStackObject();
                target.add(event.getSourceId(), game);
                this.addTarget(target);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Sliver creature you control becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays {2}.";
    }
}