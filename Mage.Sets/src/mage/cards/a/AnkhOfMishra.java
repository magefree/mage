
package mage.cards.a;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author KholdFuzion
 *
 */
public final class AnkhOfMishra extends CardImpl {

    public AnkhOfMishra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        //  Whenever a land enters the battlefield, Ankh of Mishra deals 2 damage to that land's controller.
        this.addAbility(new AnkhOfMishraAbility());

    }

    private AnkhOfMishra(final AnkhOfMishra card) {
        super(card);
    }

    @Override
    public AnkhOfMishra copy() {
        return new AnkhOfMishra(this);
    }
}

class AnkhOfMishraAbility extends TriggeredAbilityImpl {

    public AnkhOfMishraAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2));
    }

    private AnkhOfMishraAbility(final AnkhOfMishraAbility ability) {
        super(ability);
    }

    @Override
    public AnkhOfMishraAbility copy() {
        return new AnkhOfMishraAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isLand(game)) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(player.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a land enters the battlefield, {this} deals 2 damage to that land's controller.";
    }
}
