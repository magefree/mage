package mage.cards.d;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author LevelX2
 */
public final class DeathFrenzy extends CardImpl {

    public DeathFrenzy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{G}");

        // All creatures get -2/-2 until end of turn. Whenever a creature dies this turn, you gain 1 life.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new DeathFrenzyDelayedTriggeredAbility()));
    }

    private DeathFrenzy(final DeathFrenzy card) {
        super(card);
    }

    @Override
    public DeathFrenzy copy() {
        return new DeathFrenzy(this);
    }
}

class DeathFrenzyDelayedTriggeredAbility extends DelayedTriggeredAbility {


    public DeathFrenzyDelayedTriggeredAbility() {
        super(new GainLifeEffect(1), Duration.EndOfTurn, false);
    }

    public DeathFrenzyDelayedTriggeredAbility(DeathFrenzyDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent() && zEvent.getTarget() != null && StaticFilters.FILTER_PERMANENT_CREATURES.match(zEvent.getTarget(), controllerId, this, game)) {
            return true;
        }
        return false;
    }

    @Override
    public DeathFrenzyDelayedTriggeredAbility copy() {
        return new DeathFrenzyDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature dies this turn, you gain 1 life.";
    }
}
