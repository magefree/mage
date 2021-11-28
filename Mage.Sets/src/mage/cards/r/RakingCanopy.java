package mage.cards.r;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class RakingCanopy extends CardImpl {

    public RakingCanopy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // Whenever a creature with flying attacks you, Raking Canopy deals 4 damage to it.
        this.addAbility(new RakingCanopyTriggeredAbility());
    }

    private RakingCanopy(final RakingCanopy card) {
        super(card);
    }

    @Override
    public RakingCanopy copy() {
        return new RakingCanopy(this);
    }
}

class RakingCanopyTriggeredAbility extends TriggeredAbilityImpl {

    public RakingCanopyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(4));
    }

    public RakingCanopyTriggeredAbility(final RakingCanopyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RakingCanopyTriggeredAbility copy() {
        return new RakingCanopyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attacker = game.getPermanent(event.getSourceId());
        if (attacker == null
                || !attacker.getAbilities().contains(FlyingAbility.getInstance())) {
            return false;
        }
        if (event.getTargetId().equals(this.getControllerId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(attacker.getId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature with flying attacks you, {this} deals 4 damage to it.";
    }
}
