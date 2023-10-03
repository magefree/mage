
package mage.cards.k;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author emerald000
 */
public final class KarmicJustice extends CardImpl {

    public KarmicJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");


        // Whenever a spell or ability an opponent controls destroys a noncreature permanent you control, you may destroy target permanent that opponent controls.
        this.addAbility(new KarmicJusticeTriggeredAbility());
    }

    private KarmicJustice(final KarmicJustice card) {
        super(card);
    }

    @Override
    public KarmicJustice copy() {
        return new KarmicJustice(this);
    }
}

class KarmicJusticeTriggeredAbility extends TriggeredAbilityImpl {
    
    KarmicJusticeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
    }
    
    private KarmicJusticeTriggeredAbility(final KarmicJusticeTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public KarmicJusticeTriggeredAbility copy() {
        return new KarmicJusticeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROYED_PERMANENT;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(controllerId) 
                && game.getOpponents(this.getControllerId()).contains(game.getControllerId(event.getSourceId()))) {
            MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (!mageObject.isCreature(game)) {
                this.getTargets().clear();
                FilterPermanent filter = new FilterPermanent("target permanent that opponent controls");
                filter.add(new ControllerIdPredicate(game.getControllerId(event.getSourceId())));
                Target target = new TargetPermanent(filter);
                this.getTargets().add(target);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever a spell or ability an opponent controls destroys a noncreature permanent you control, you may destroy target permanent that opponent controls.";
    }
}
