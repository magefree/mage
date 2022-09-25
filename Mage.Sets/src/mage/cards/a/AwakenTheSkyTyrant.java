
package mage.cards.a;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.DragonToken2;

/**
 *
 * @author LevelX2
 */
public final class AwakenTheSkyTyrant extends CardImpl {

    public AwakenTheSkyTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");

        // When a source an opponent controls deals damage to you, sacrifice Awaken the Sky Tyrant. If you do, create a 5/5 red Dragon creature token with flying.
        this.addAbility(new AwakenTheSkyTyrantTriggeredAbility());
    }

    private AwakenTheSkyTyrant(final AwakenTheSkyTyrant card) {
        super(card);
    }

    @Override
    public AwakenTheSkyTyrant copy() {
        return new AwakenTheSkyTyrant(this);
    }
}

class AwakenTheSkyTyrantTriggeredAbility extends TriggeredAbilityImpl {

    public AwakenTheSkyTyrantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CreateTokenEffect(new DragonToken2(), 1), new SacrificeSourceCost(), null, false), false);
        setTriggerPhrase("When a source an opponent controls deals damage to you, ");
    }

    public AwakenTheSkyTyrantTriggeredAbility(final AwakenTheSkyTyrantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AwakenTheSkyTyrantTriggeredAbility copy() {
        return new AwakenTheSkyTyrantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(controllerId)) {
            UUID sourceControllerId = game.getControllerId(event.getSourceId());
            if (sourceControllerId != null && game.getOpponents(getControllerId()).contains(sourceControllerId)) {
                return true;
            }
        }
        return false;
    }
}
