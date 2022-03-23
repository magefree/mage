
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author spjspj
 */
public final class ScalelordReckoner extends CardImpl {

    public ScalelordReckoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Dragon you control becomes the target of a spell or ability an opponent controls, destroy target nonland permanent that player controls.
        this.addAbility(new ScalelardReckonerTriggeredAbility(new DestroyTargetEffect()));
    }

    private ScalelordReckoner(final ScalelordReckoner card) {
        super(card);
    }

    @Override
    public ScalelordReckoner copy() {
        return new ScalelordReckoner(this);
    }
}

class ScalelardReckonerTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Dragon creature you control");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public ScalelardReckonerTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    public ScalelardReckonerTriggeredAbility(final ScalelardReckonerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScalelardReckonerTriggeredAbility copy() {
        return new ScalelardReckonerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            Permanent creature = game.getPermanent(event.getTargetId());
            if (creature != null && filter.match(creature, getControllerId(), this, game)) {
                FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent that player controls");
                filter.add(new ControllerIdPredicate(event.getPlayerId()));
                this.getTargets().clear();
                this.addTarget(new TargetPermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Dragon you control becomes the target of a spell or ability an opponent controls, destroy target nonland permanent that player controls.";
    }
}
