
package mage.cards.b;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BurnAway extends CardImpl {

    public BurnAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}");


        // Burn Away deals 6 damage to target creature. When that creature dies this turn, exile all cards from its controller's graveyard.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new BurnAwayDelayedTriggeredAbility(), true));
    }

    private BurnAway(final BurnAway card) {
        super(card);
    }

    @Override
    public BurnAway copy() {
        return new BurnAway(this);
    }
}

class BurnAwayDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public BurnAwayDelayedTriggeredAbility() {
        super(new ExileGraveyardAllTargetPlayerEffect(), Duration.EndOfTurn, false);
    }

    public BurnAwayDelayedTriggeredAbility(BurnAwayDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent() && zEvent.getTarget() != null && zEvent.getTargetId().equals(getTargets().getFirstTarget())) {
            this.getTargets().clear(); // else spell fizzles because target creature died
            Target target = new TargetPlayer();
            target.add(zEvent.getTarget().getControllerId(), game);
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public BurnAwayDelayedTriggeredAbility copy() {
        return new BurnAwayDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, exile its controller's graveyard.";
    }
}