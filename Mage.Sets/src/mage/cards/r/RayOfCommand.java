
package mage.cards.r;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RayOfCommand extends CardImpl {
    
    public RayOfCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Untap target creature an opponent controls and gain control of it until end of turn. That creature gains haste until end of turn. When you lose control of the creature, tap it.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new RayOfCommandDelayedTriggeredAbility(), true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
    }

    private RayOfCommand(final RayOfCommand card) {
        super(card);
    }

    @Override
    public RayOfCommand copy() {
        return new RayOfCommand(this);
    }
}

class RayOfCommandDelayedTriggeredAbility extends DelayedTriggeredAbility {

    RayOfCommandDelayedTriggeredAbility () {
        super(new TapTargetEffect(), Duration.EndOfGame, true); // effect can last over turns end, if you still control the target but only one time
    }

    RayOfCommandDelayedTriggeredAbility(RayOfCommandDelayedTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId);
    }
    
    @Override
    public RayOfCommandDelayedTriggeredAbility copy() {
        return new RayOfCommandDelayedTriggeredAbility(this);
    }
    
    @Override
    public String getRule() {
        return "When you lose control of the creature, tap it.";
    }
    
}
