
package mage.cards.b;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
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
 * @author LevelX2
 */
public final class BattleStrain extends CardImpl {

    public BattleStrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");


        // Whenever a creature blocks, Battle Strain deals 1 damage to that creature's controller.
        this.addAbility(new BattleStrainTriggeredAbility());
    }

    private BattleStrain(final BattleStrain card) {
        super(card);
    }

    @Override
    public BattleStrain copy() {
        return new BattleStrain(this);
    }
}

class BattleStrainTriggeredAbility extends TriggeredAbilityImpl {

    public BattleStrainTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
    }

    public BattleStrainTriggeredAbility(final BattleStrainTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BattleStrainTriggeredAbility copy() {
        return new BattleStrainTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent blocker = game.getPermanent(event.getTargetId());
        if (blocker != null) {
            getEffects().get(0).setTargetPointer(new FixedTarget(blocker.getControllerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature blocks, {this} deals 1 damage to that creature's controller.";
    }
}
