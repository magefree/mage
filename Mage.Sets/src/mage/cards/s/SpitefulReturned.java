
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Quercitron
 */
public final class SpitefulReturned extends CardImpl {

    public SpitefulReturned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bestow {3}{B}
        this.addAbility(new BestowAbility(this, "{3}{B}"));
        // Whenever Spiteful Returned or enchanted creature attacks, defending player loses 2 life.
        Effect effect = new LoseLifeTargetEffect(2);
        effect.setText("defending player loses 2 life");
        this.addAbility(new SpitefulReturnedTriggeredAbility(effect));
        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield)));
    }

    private SpitefulReturned(final SpitefulReturned card) {
        super(card);
    }

    @Override
    public SpitefulReturned copy() {
        return new SpitefulReturned(this);
    }
}

class SpitefulReturnedTriggeredAbility extends TriggeredAbilityImpl {

    public SpitefulReturnedTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever {this} or enchanted creature attacks, ");
    }

    public SpitefulReturnedTriggeredAbility(final SpitefulReturnedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpitefulReturnedTriggeredAbility copy() {
        return new SpitefulReturnedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(this.getSourceId());
        if (sourcePermanent == null) {
            return false;
        }

        if (sourcePermanent.isCreature(game)) {
            if (event.getSourceId() != null
                    && event.getSourceId().equals(this.getSourceId())) {
                UUID defender = game.getCombat().getDefendingPlayerId(this.getSourceId(), game);
                this.getEffects().get(0).setTargetPointer(new FixedTarget(defender));
                return true;
            }
        } else {
            if (sourcePermanent.isAttachedTo(event.getSourceId())) {
                UUID defender = game.getCombat().getDefendingPlayerId(sourcePermanent.getAttachedTo(), game);
                this.getEffects().get(0).setTargetPointer(new FixedTarget(defender));
                return true;
            }
        }
        return false;
    }
}
