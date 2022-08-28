
package mage.cards.v;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2 & L_J
 */
public final class VigorousCharge extends CardImpl {
    
    private static final String staticText = "Whenever that creature deals combat damage this turn, if this spell was kicked, you gain life equal to that damage";

    public VigorousCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Kicker {W} (You may pay an additional {W} as you cast this spell.)
        this.addAbility(new KickerAbility("{W}"));
        // Target creature gains trample until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        // Whenever that creature deals combat damage this turn, if this spell was kicked, you gain life equal to that damage.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new GainAbilityTargetEffect(new VigorousChargeTriggeredAbility(), Duration.EndOfTurn),
        new LockedInCondition(KickedCondition.ONCE), staticText));

    }

    private VigorousCharge(final VigorousCharge card) {
        super(card);
    }

    @Override
    public VigorousCharge copy() {
        return new VigorousCharge(this);
    }
}

class VigorousChargeTriggeredAbility extends TriggeredAbilityImpl {

    public VigorousChargeTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public VigorousChargeTriggeredAbility(final VigorousChargeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VigorousChargeTriggeredAbility copy() {
        return new VigorousChargeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent damageEvent = (DamagedEvent) event;
        if (damageEvent.isCombatDamage()) {
            if (event.getSourceId().equals(this.sourceId)) {
                this.getEffects().clear();
                this.getEffects().add(new GainLifeEffect(damageEvent.getAmount()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage, you gain that much life.";
    }
}
