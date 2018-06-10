
package mage.cards.h;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class HuntersInsight extends CardImpl {

    public HuntersInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");


        // Choose target creature you control. Whenever that creature deals combat damage to a player or planeswalker this turn, draw that many cards.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new HuntersInsightTriggeredAbility(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    public HuntersInsight(final HuntersInsight card) {
        super(card);
    }

    @Override
    public HuntersInsight copy() {
        return new HuntersInsight(this);
    }
}

class HuntersInsightTriggeredAbility extends TriggeredAbilityImpl {

    public HuntersInsightTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public HuntersInsightTriggeredAbility(final HuntersInsightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HuntersInsightTriggeredAbility copy() {
        return new HuntersInsightTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER || event.getType() == EventType.DAMAGED_PLANESWALKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId) && ((DamagedEvent) event).isCombatDamage()) {
            this.getEffects().clear();
            this.addEffect(new DrawCardSourceControllerEffect(event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature deals combat damage to a player or planeswalker, draw that many cards.";
    }
}
